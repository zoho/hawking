package com.zoho.hawking.language.english;

import com.zoho.hawking.datetimeparser.utils.NumberParser;
import com.zoho.hawking.utils.DateTimeProperties;
import edu.stanford.nlp.util.Triple;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeRecognitionAndParsing {

    private static List<String> cardinalNumberList = Arrays.asList("First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh", "Eighth", "Ninth", "Tenth",//No I18N
            "Eleventh", "Twelfth", "Thirteenth", "Fourteenth", "Fifteenth", "Sixteenth", "Seventeenth", "Eighteenth", "Nineteenth", "Twentieth", "Twenty-first",//No I18N
            "Twenty-second", "Twenty-third", "Twenty-fourth", "Twenty-fifth", "Twenty-sixth", "Twenty-seventh", "Twenty-eighth", "Twenty-ninth", "Thirtieth", "Thirty-first",//No I18N
            "Twenty first", "Twenty second", "Twenty third", "Twenty fourth", "Twenty fifth", "Twenty sixth", "Twenty seventh", "Twenty eighth", "Twenty ninth", "Thirtieth", "Thirty first");//No I18N

    public static List<Pair<Boolean, List<Triple<String, Integer, Integer>>>> getSeparateDates(List<Triple<String, Integer, Integer>> allDates) {
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


    private static boolean isContain(String dateText, String timezone) {
        String pattern = "\\b" + timezone + "\\b"; //No I18N
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(dateText);
        return m.find();
    }

    public static void extract(String text){
        List<Pair<Boolean, List<Triple<String, Integer, Integer>>>> singleDatesList = getSeparateDates(Parser.parse(text));


        List<DateTimeProperties> dateList = new ArrayList<>();
        for (Pair<Boolean, List<Triple<String, Integer, Integer>>> relAndDate : singleDatesList) {
            boolean relationExist = relAndDate.getLeft();
            List<Triple<String, Integer, Integer>> triples = relAndDate.getRight();
            if (!triples.isEmpty()) {
                Triple<String, Integer, Integer> triple = triples.get(0);
                int startIndex = triple.second;
                int endIndex = triple.third;
                String parsedText = text.substring(startIndex, endIndex);
                parsedText = cardinalNumberFinder(parsedText);
                Map<String, String> componentsMap = Recognizer.tagPredictor(parsedText, triples);
//                DateTimeOffsetReturn dateTimeOffsetReturn = TimeZoneExtractor.referenceDateExtractor(referenceDate, config, parsedText);
//                if(!TimeZoneExtractor.isTimeZonePresent){
//                    dateTimeOffsetReturn = TimeZoneExtractor.referenceDateExtractor(referenceDate, config, inputSentence);
//                }
//                dateTimeEssentials.setReferenceTime(dateTimeOffsetReturn.getReferenceDate());
//                dateTimeEssentials.setTimeZoneOffSet(dateTimeOffsetReturn.getTimeOffset());
            }


        }
    }

    public static String cardinalNumberFinder(String dateText) {
        String cardinal = null;
//        if (dateText.contains("start day")) {
//            dateText = dateText.replaceAll("\\b(?i)" + "start day" + "\\b", "");//No I18N
//            dateText = "first day of " + dateText;//No I18N
//        } else if (dateText.contains("end day")) {
//            dateText = dateText.replaceAll("\\b(?i)" + "end day" + "\\b", "");//No I18N
//            dateText = "last day of " + dateText;//No I18N
//        } else if (dateText.contains("to go")) {
//            dateText = dateText.replaceAll("\\b(?i)" + "to go" + "\\b", "");//No I18N
//            dateText = "until " + dateText;//No I18N
//        }
        for (String cardinalNumber : cardinalNumberList) {
            if (isContain(dateText, cardinalNumber)) {
                cardinal = cardinalNumber;
                dateText = dateText.replaceAll("\\b(?i)" + cardinal + "\\b", NumberParser.CARDINAL_NUMBERS.get(cardinal)); //No I18N
            }
        }
        return dateText;

    }
}
