package com.zoho.hawking.language.english;

import com.zoho.hawking.datetimeparser.configuration.HawkingConfiguration;
import com.zoho.hawking.language.AbstractLanguage;
import com.zoho.hawking.language.english.model.DateTimeEssentials;
import com.zoho.hawking.language.english.model.DateTimeOffsetReturn;
import com.zoho.hawking.utils.CoreNlpUtils;
import com.zoho.hawking.utils.DateTimeProperties;
import com.zoho.hawking.utils.TimeZoneExtractor;
import edu.stanford.nlp.util.Triple;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class EnglishLanguage extends AbstractLanguage {
    public EnglishLanguage() {
        super(DateTimeWordProperties.ALL_WORDS);
    }

    private static final Logger LOGGER = Logger.getLogger(EnglishLanguage.class.getName());

    public  List<Pair<Boolean, List<Triple<String, Integer, Integer>>>> getSeparateDates(List<Triple<String, Integer, Integer>> allDates) {
        List<Pair<Boolean, List<Triple<String, Integer, Integer>>>> separateDates = new ArrayList<>();
        int startIndex = 0;
        int endIndex = 0;
        int prevEnd = -1;
        boolean isRelation = false;

        for (Triple<String, Integer, Integer> date : allDates) {
            if (isRelation || date.first().equals("R")) {
                isRelation = true;
            }
            if (prevEnd != -1 && date.second() - prevEnd != 1) {
                List<Triple<String, Integer, Integer>> singleDate = allDates.subList(startIndex, endIndex);
                separateDates.add(Pair.of(isRelation, singleDate));
                startIndex = endIndex;
            }
            prevEnd = date.third();
            endIndex++;
        }
        separateDates.add(Pair.of(isRelation, allDates.subList(startIndex, endIndex)));
        return separateDates;
    }

    @Override
    public List<DateTimeProperties> predict(String inputSentence, Date referenceDate, HawkingConfiguration config) {
        List<DateTimeProperties> dateList = new ArrayList<>();
        inputSentence = inputSentence.replaceAll("\n",". "); //NO I18n
        List<String> inputSentences = CoreNlpUtils.sentenceTokenize(inputSentence);
        for(String sent: inputSentences){
            sent = sent.replaceAll("\\s{2,}", " ").trim();
            List<Pair<Boolean, List<Triple<String, Integer, Integer>>>> singleDatesList = getSeparateDates(Parser.parse(sent));
            for (Pair<Boolean, List<Triple<String, Integer, Integer>>> relAndDate : singleDatesList) {
                List<Triple<String, Integer, Integer>> triples = relAndDate.getRight();
                DateTimeEssentials dateTimeEssentials = new DateTimeEssentials();
                dateTimeEssentials.setParagraph(inputSentence);
                dateTimeEssentials.addId();
                dateTimeEssentials.setSentence(sent);
                dateTimeEssentials.setTriples(relAndDate);
                dateTimeEssentials.setTense(getTense(sent));
                if (!triples.isEmpty()) {
                    Triple<String, Integer, Integer> triple = triples.get(0);

                    int startIndex = triple.second;
                    int endIndex = triple.third;
                    String parsedText = sent.substring(startIndex, endIndex);
                    DateTimeOffsetReturn dateTimeOffsetReturn = TimeZoneExtractor.referenceDateExtractor(referenceDate, config, parsedText);
                    if(!TimeZoneExtractor.isTimeZonePresent){
                        dateTimeOffsetReturn = TimeZoneExtractor.referenceDateExtractor(referenceDate, config, sent);
                    }
                    dateTimeEssentials.setReferenceTime(dateTimeOffsetReturn.getReferenceDate());
                    dateTimeEssentials.setTimeZoneOffSet(dateTimeOffsetReturn.getTimeOffset());
                    try {
                        dateList.addAll(DateTimeGateWay.getDateAndTime(dateTimeEssentials));
                    } catch (Exception e) {
                        LOGGER.info("HawkingTimeParser :: Exception in Hawking :: Unparsed date component Present");
                    }
                }


            }
        }
        return dateList;

    }

    @Override
    public String getTense(String inputText) {
        return CoreNlpUtils.getParsedDependency(inputText).getTenseClass();
    }
}
