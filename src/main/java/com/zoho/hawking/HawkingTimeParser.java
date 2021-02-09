//$Id$
package com.zoho.hawking;


import com.zoho.hawking.datetimeparser.DateAndTime;
import com.zoho.hawking.datetimeparser.DateTimeParser;
import com.zoho.hawking.datetimeparser.configuration.Configuration;
import com.zoho.hawking.datetimeparser.configuration.HawkingConfiguration;
import com.zoho.hawking.datetimeparser.constants.ConfigurationConstants;
import com.zoho.hawking.language.AbstractLanguage;
import com.zoho.hawking.language.LanguageFactory;
import com.zoho.hawking.language.english.Parser;
import com.zoho.hawking.language.english.model.*;
import com.zoho.hawking.utils.Constants;
import com.zoho.hawking.utils.DateTimeProperties;
import com.zoho.hawking.utils.TimeZoneExtractor;
import edu.stanford.nlp.util.Triple;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class HawkingTimeParser {

    private static final Logger LOGGER = Logger.getLogger(HawkingTimeParser.class.getName());

    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        HawkingTimeParser parser = new HawkingTimeParser();
        while(true) {
            LOGGER.info("Give input: ");
            String inputText = s.nextLine();
            HawkingConfiguration hawkingConfiguration = new HawkingConfiguration();
            Date referenceDate = new Date();
            LOGGER.info("referenceDate ::::: "+referenceDate);
            hawkingConfiguration.setFiscalYearStart(2);
            hawkingConfiguration.setFiscalYearEnd(1);
            LOGGER.info("Give Timezone: ");
            String inputText2 = s.nextLine();
            hawkingConfiguration.setTimeZone(inputText2);
            DatesFound datesFound = parser.parse(inputText, referenceDate, hawkingConfiguration, "eng"); //No I18N
            LOGGER.info("DATES FOUND ::  "+ datesFound.toString());
        }
    }



    public DatesFound parse(String inputSentence, Date referenceDate, HawkingConfiguration config, String lang) {
        Configuration configuration = new Configuration(config);
        ConfigurationConstants.setConfiguration(configuration);
        List<ParserOutput> parserOutputs = new ArrayList<>();
        List<DateGroup> dateGroups = new ArrayList<>();
        DatesFound datesFound = new DatesFound();

        AbstractLanguage abstractLanguage = LanguageFactory.getLanguageImpl(lang);

        List<DateTimeProperties> dateList = abstractLanguage.predict(inputSentence, referenceDate, config);
        for (DateTimeProperties date : dateList) {
            parserOutputs.add(date.getParserOutput());
            dateGroups.add(date.getDateGroup());
        }
        datesFound.setParserOutputs(parserOutputs);
        datesFound.setDateGroups(dateGroups);
        LOGGER.info(datesFound.toString());
        return DateTimeProperties.emptyDatesRemover(datesFound);
    }


    public static List<DateInfo> getDateText(String inputSentence, String lang) {
        List<DateInfo> dateList = new ArrayList<>();
        List<Triple<String, Integer, Integer>> parseout = Parser.parse(inputSentence);
        LOGGER.info(parseout.toString());
        List<Pair<Boolean, List<Triple<String, Integer, Integer>>>> singleDatesList = LanguageFactory.getLanguageImpl(lang).getSeparateDates(parseout);
        DateTimeEssentials dateTimeEssentials = new DateTimeEssentials();
        dateTimeEssentials.setSentence(inputSentence);
        for (Pair<Boolean, List<Triple<String, Integer, Integer>>> date : singleDatesList) {
            dateTimeEssentials.setTriples(date);
            List<Triple<String, Integer, Integer>> tripleList = dateTimeEssentials.getTriples();
            if (date.getLeft()) {
                StringBuilder singleDateText = new StringBuilder();
                for (Triple<String, Integer, Integer> triple : tripleList) {
                    int startIndex = triple.second;
                    int endIndex = triple.third;
                    String dateText = dateTimeEssentials.getSentence().substring(startIndex, endIndex);
                    singleDateText.append(" ").append(dateText); // no i18n
                }
                if (DateTimeProperties.isDateContain(singleDateText.toString().toLowerCase())) {
                    dateList.add(new DateInfo(singleDateText.toString().trim(), tripleList.get(0).second, tripleList.get(tripleList.size() - 1).third));
                }
            } else {
                for (Triple<String, Integer, Integer> triple : tripleList) {
                    int startIndex = triple.second;
                    int endIndex = triple.third;
                    String dateText = dateTimeEssentials.getSentence().substring(startIndex, endIndex);
                    if (DateTimeProperties.isDateContain(dateText.toLowerCase())) {
                        dateList.add(new DateInfo(dateText.trim(), startIndex, endIndex));
                    }
                }
            }
        }
        return dateList;
    }


    public static Pair<ParserOutput, DateGroup> setDateAndTime(DateTimeProperties dateTimeProperties, AbstractLanguage abstractLanguage) {
        ParserOutput parserOutput = dateTimeProperties.getParserOutput();
        DateTimeEssentials dateTimeEssentials = dateTimeProperties.getDateTimeEssentials();
        DateAndTime dateAndTime = DateTimeParser.timeParser(
            dateTimeProperties.getReferenceTime() != null ? dateTimeProperties.getReferenceTime() : dateTimeEssentials.getReferenceTime(),
            dateTimeProperties.getDateTimeEssentials().getTense(),
            dateTimeProperties.getComponentsMap(),
            abstractLanguage);
        DateTime start = dateAndTime.getStart() != null ? new DateTime(TimeZoneExtractor.offsetDateConverter(dateAndTime.getStart().getMillis(), dateTimeEssentials.getTimeZoneOffSet())) : null;
        DateTime end = dateAndTime.getEnd() != null ? new DateTime(TimeZoneExtractor.offsetDateConverter(dateAndTime.getEnd().getMillis(), dateTimeEssentials.getTimeZoneOffSet())) : null;
        String startFormat = dateAndTime.getStart() != null ? TimeZoneExtractor.dateFormatter(dateAndTime.getStart().getMillis()) : null;
        String endFormat = dateAndTime.getEnd() != null ? TimeZoneExtractor.dateFormatter(dateAndTime.getEnd().getMillis()) : null;
        DateRange dateRange = new DateRange("", start, end, startFormat, endFormat); //No I18N
        parserOutput.setTimezoneOffset(dateTimeEssentials.getTimeZoneOffSet());
        parserOutput.setDateRange(dateRange);
        parserOutput.setIsTimeZonePresent(TimeZoneExtractor.isTimeZonePresent);
        DateGroup dateGroup = dateAndTime.getDateGroup();
        dateGroup.setExpression(Constants.OPEN_PARENTHESIS + parserOutput.getId() + Constants.CLOSE_PARENTHESIS);
        return Pair.of(parserOutput, dateGroup);
    }
}
