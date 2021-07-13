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


    public DatesFound parse(String inputSentence, Date referenceDate, HawkingConfiguration config, String lang) {
        Configuration configuration = new Configuration(config);
        ConfigurationConstants.setConfiguration(configuration);
        List<ParserOutput> parserOutputs = new ArrayList<>();
        List<DateGroup> dateGroups = new ArrayList<>();
        DatesFound datesFound = new DatesFound();

        AbstractLanguage abstractLanguage = LanguageFactory.getLanguageImpl(lang);
        assert abstractLanguage != null;
        List<DateTimeProperties> dateList = abstractLanguage.predict(inputSentence, referenceDate, config);
        for (DateTimeProperties date : dateList) {
            parserOutputs.add(date.getParserOutput());
            dateGroups.add(date.getDateGroup());
        }
        parserOutputs = DateTimeProperties.addDefaultTime(parserOutputs, config.getDayhourStart(), config.getDayhourEnd());
        datesFound.setParserOutputs(parserOutputs);
        datesFound.setDateGroups(dateGroups);
        LOGGER.info(datesFound.toString());
        return DateTimeProperties.emptyDatesRemover(datesFound);
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
