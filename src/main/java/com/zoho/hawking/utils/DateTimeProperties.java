//$Id$
package com.zoho.hawking.utils;

import com.zoho.hawking.HawkingTimeParser;
import com.zoho.hawking.language.english.Recognizer;
import com.zoho.hawking.datetimeparser.DateAndTime;
import com.zoho.hawking.datetimeparser.DateTimeParser;
import com.zoho.hawking.datetimeparser.utils.NumberParser;
import com.zoho.hawking.language.english.model.*;
import edu.stanford.nlp.util.Triple;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeProperties {
    //private final static String ROOT = System.getProperty("user.dir");
    private final static Logger LOGGER = Logger.getLogger(HawkingTimeParser.class.getName());
    private final static Pattern DATEFORMATREGEX = Pattern.compile("(?:\\d{4}|\\d{1,2})[-./]\\d{1,2}[-./](?:\\d{4}|\\d{1,2})$");
    private final static Pattern DATEMONTHFORMATREGEX = Pattern.compile("^(\\d{1,2})[-/]\\d{1,2}$");
    private final static Pattern TIMEFORMATREGEX = Pattern.compile("^(((0[0-9]|1[0-9]|2[0-3]|[0-9])([:.][0-5][0-9])?)[\\s]?([AaPp][Mm])?)$");
    private final static Pattern TIMEFORMATREGEXHMS = Pattern.compile("^(((0[0-9]|1[0-9]|2[0-3]|[0-9])([:.][0-5][0-9])([:.][0-5][0-9])?))$");
    private final static Pattern MONTHOFYEAR = Pattern.compile("^(jan|feb|mar|apr|may|jun|jul|aug|sept|sep|oct|nov|dec|Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sept|Sep|Oct|Nov|Dec)$");
    private final static Pattern NUMBERFORMATREGEX = Pattern.compile("^[0-9]*$");
    private final static Pattern NUMBERFORMAT_23DIGIT = Pattern.compile("^\\d{2,3}$");
    private final static Pattern NUMBERFORMAT_4DIGIT = Pattern.compile("^\\d{4}$");
    private final static Pattern NUMBERFORMAT_1DIGIT = Pattern.compile("^\\d$");
    private static List<String> cardinalNumberList = Arrays.asList("First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh", "Eighth", "Ninth", "Tenth",//No I18N
        "Eleventh", "Twelfth", "Thirteenth", "Fourteenth", "Fifteenth", "Sixteenth", "Seventeenth", "Eighteenth", "Nineteenth", "Twentieth", "Twenty-first",//No I18N
        "Twenty-second", "Twenty-third", "Twenty-fourth", "Twenty-fifth", "Twenty-sixth", "Twenty-seventh", "Twenty-eighth", "Twenty-ninth", "Thirtieth", "Thirty-first",//No I18N
        "Twenty first", "Twenty second", "Twenty third", "Twenty fourth", "Twenty fifth", "Twenty sixth", "Twenty seventh", "Twenty eighth", "Twenty ninth", "Thirtieth", "Thirty first");//No I18N
    private final static Pattern DATETIMEWORDS = Pattern.compile("(afternoon|apr|april|aug|august|dawn|day|days|dec|december|eve|evening|feb|february|fri|friday|hour|hours|jan|january|jul|july|jun|june|mar|march|may|midnight|min|mins|minute|minutes|mon|monday|month|months|morning|night|noon|nov|november|now|oct|october|sat|saturday|sec|second|seconds|secs|sep|sept|september|sun|sunday|thu|thurs|thursday|today|tomorrow|tue|tuesday|wed|wednesday|week|weeks|year|years|yesterday)");
    private final static Pattern NUMBERR_REGEX = Pattern.compile("([0-9])");

    public Map<String, String> getComponentsMap() {
        return componentsMap;
    }

    private ParserOutput parserOutput = new ParserOutput();
    private DateGroup dateGroup = new DateGroup();
    private ParsedDate parsedDate;
    private DateTimeEssentials dateTimeEssentials;

    public DateTimeEssentials getDateTimeEssentials() {
        return dateTimeEssentials;
    }

    public DateTime getReferenceTime() {
        return referenceTime;
    }


    private DateTime referenceTime = null;
    private Triple<String, Integer, Integer> triple;
    private Map<String, String> componentsMap;
    private String parsedText;

    public DateTimeProperties(DateTimeEssentials dateTimeEssentials, Triple<String, Integer, Integer> triple) {
        this.dateTimeEssentials = dateTimeEssentials;
        this.triple = triple;
        setParserOutput();
    }

    public DateTimeProperties(DateTimeEssentials dateTimeEssentials, DateTime referenceTime, Triple<String, Integer, Integer> triple) {
        this.dateTimeEssentials = dateTimeEssentials;
        this.referenceTime = referenceTime;
        this.triple = triple;
        setParserOutput();
    }

    private static boolean isContain(String dateText, String timezone) {
        String pattern = "\\b" + timezone + "\\b"; //No I18N
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(dateText);
        return m.find();
    }

    public static boolean isDateContain(String dateText) {
        return (DATETIMEWORDS.matcher(dateText).find() || NUMBERR_REGEX.matcher(dateText).find());
    }

    public static String cardinalNumberFinder(String dateText) {
        String cardinal = null;
        if (dateText.contains("start day")) {
            dateText = dateText.replaceAll("\\b(?i)" + "start day" + "\\b", "");//No I18N
            dateText = "first day of " + dateText;//No I18N
        } else if (dateText.contains("end day")) {
            dateText = dateText.replaceAll("\\b(?i)" + "end day" + "\\b", "");//No I18N
            dateText = "last day of " + dateText;//No I18N
        } else if (dateText.contains("to go")) {
            dateText = dateText.replaceAll("\\b(?i)" + "to go" + "\\b", "");//No I18N
            dateText = "until " + dateText;//No I18N
        }
        for (String cardinalNumber : cardinalNumberList) {
            if (isContain(dateText, cardinalNumber)) {
                cardinal = cardinalNumber;
            }
        }
        if (cardinal != null) {
            dateText = dateText.replaceAll("\\b(?i)" + cardinal + "\\b", NumberParser.CARDINAL_NUMBERS.get(cardinal)); //No I18N

        }


        return dateText;

    }

    public ParserOutput getParserOutput() {
        return parserOutput;
    }

    private void setParserOutput(ParsedDate parserDate) {
        List<RecognizerOutput> recognizerOutputs = new ArrayList<>();
        for (Triple<String, Integer, Integer> recognizeOutput : parserDate.getOutputWithOffsets()) {
            String recognizedText = parsedText.substring(recognizeOutput.second, recognizeOutput.third);
            RecognizerOutput recognizerOutput = new RecognizerOutput(recognizeOutput.first, recognizeOutput.second, recognizeOutput.third, recognizedText);
            recognizerOutputs.add(recognizerOutput);
        }
        parserOutput.setRecognizerOutputs(recognizerOutputs);
        this.parsedDate = parserDate;
    }

    public void setParserOutput() {
        parserOutput.setId(dateTimeEssentials.getId());
        int startIndex = triple.second;
        int endIndex = triple.third;
        parsedText = dateTimeEssentials.getSentence().substring(startIndex, endIndex);
        parserOutput.setParserLabel(triple.first);
        parserOutput.setText(parsedText);
        int parsedTextIndexInPara = findParagraphIndex(dateTimeEssentials.getParagraph(), dateTimeEssentials.getSentence(), startIndex);
        parserOutput.setParserStartIndex(parsedTextIndexInPara);
        parserOutput.setParserEndIndex(parsedTextIndexInPara + parsedText.length());
    }

//    public void setDateAndTime() {
//        DateAndTime dateAndTime = DateTimeParser.timeParser(
//            referenceTime != null ? referenceTime : dateTimeEssentials.getReferenceTime(),
//            dateTimeEssentials.getTense(),
//            componentsMap);
//        DateTime start = dateAndTime.getStart() != null ? new DateTime(TimeZoneExtractor.offsetDateConverter(dateAndTime.getStart().getMillis(), dateTimeEssentials.getTimeZoneOffSet())) : null;
//        DateTime end = dateAndTime.getEnd() != null ? new DateTime(TimeZoneExtractor.offsetDateConverter(dateAndTime.getEnd().getMillis(), dateTimeEssentials.getTimeZoneOffSet())) : null;
//        String startFormat = dateAndTime.getStart() != null ? TimeZoneExtractor.dateFormatter(dateAndTime.getStart().getMillis()) : null;
//        String endFormat = dateAndTime.getEnd() != null ? TimeZoneExtractor.dateFormatter(dateAndTime.getEnd().getMillis()) : null;
//        DateRange dateRange = new DateRange("", start, end, startFormat, endFormat); //No I18N
//        parserOutput.setTimezoneOffset(dateTimeEssentials.getTimeZoneOffSet());
//        parserOutput.setDateRange(dateRange);
//        parserOutput.setIsTimeZonePresent(TimeZoneExtractor.isTimeZonePresent);
//        setDateGroup(dateAndTime);
//    }

    public void setParsedDate() {
        parsedText = removeTimeZone(parsedText);
        parsedText = cardinalNumberFinder(parsedText);
        ParsedDate parserDate = Recognizer.recognize(parsedText);
        tagAlternator(parsedText, parserDate);
        tagShrinker(parsedText, parserDate);
        LOGGER.info("Give input: "+ parserDate.getTaggedWithXML());
        componentsMap = Recognizer.tagPredictor(parsedText, parserDate.getOutputWithOffsets());
        setParserOutput(parserDate);
        parserOutput.setIsExactTimePresent(
                parserDate.getTaggedWithXML().contains("exact_time") || //No I18N
                        parserDate.getTaggedWithXML().contains("hour_span") || //No I18N
                        parserDate.getTaggedWithXML().contains("minute_span") || //No I18N
                        parserDate.getTaggedWithXML().contains("second_span")); //No I18N
    }

    private String removeTimeZone(String parsedText) {
        String returnText;
        for (String timezone : TimeZoneExtractor.timeZoneList) {
            if (isContain(parsedText, timezone)) {
                returnText = parsedText.replaceAll("\\b(?i)" + timezone + "\\b", ""); //No I18N
                returnText = returnText.replaceAll(",", " "); //No I18N
                returnText = returnText.replaceAll("date", "day"); //No I18N
                returnText = returnText.replaceAll("Year", "year"); //No I18N
                returnText = returnText.replaceAll("\\b(?i)" + "final", "last"); //No I18N
                returnText = returnText.replaceAll("\\.$", " "); //No I18N
                returnText = returnText.replaceAll("^- ", " "); //No I18N
                returnText = returnText.replaceAll("post", "after"); //No I18N
                returnText = returnText.replaceAll("breakfast", "8 AM"); //No I18N
                returnText = returnText.replaceAll("lunch", "1 PM"); //No I18N
                returnText = returnText.replaceAll("dinner", "8 PM"); //No I18N
                returnText = returnText.replaceAll("null",""); //No I18N
                return returnText;
            }
        }
        returnText = parsedText.replaceAll(",", " "); //No I18N
        returnText = returnText.replaceAll("date", "day"); //No I18N
        returnText = returnText.replaceAll("Year", "year"); //No I18N
        returnText = returnText.replaceAll("\\b(?i)" + "final", "last"); //No I18N
        returnText = returnText.replaceAll("\\.$", " ").trim(); //No I18N
        String test = returnText.replaceAll("^(and |or |to )?\\d*$","");  //No I18N
        returnText = returnText.replaceAll("^- ", " "); //No I18N
        returnText = returnText.replaceAll("post", "after"); //No I18N
        returnText = returnText.replaceAll("breakfast", "8 AM"); //No I18N
        returnText = returnText.replaceAll("lunch", "1 PM"); //No I18N
        returnText = returnText.replaceAll("dinner", "8 PM"); //No I18N
        returnText = returnText.replaceAll("null",""); //No I18N
        returnText = test.length() > 0 ? returnText : test;
        return returnText;

    }

    public ParsedDate getParsedDate() {
        return parsedDate;
    }

    public DateGroup getDateGroup() {
        return dateGroup;
    }

    public void setDateGroup(DateAndTime dateAndTime) {
        DateGroup dateGroup = dateAndTime.getDateGroup();
        dateGroup.setExpression(Constants.OPEN_PARENTHESIS + parserOutput.getId() + Constants.CLOSE_PARENTHESIS);
        this.dateGroup = dateGroup;
    }

    public void setParsedText(String parsedText) {
        this.parsedText = parsedText;
    }

    private int findParagraphIndex(String paragraph, String sentence, int startIndexOfWordInSentence) {
        int sentenceIndexInPara = paragraph.indexOf(sentence);
        return sentenceIndexInPara + startIndexOfWordInSentence;
    }

    public Map<String, String> getComponentMap() {
        return componentsMap;
    }

    public void setReferenceTime(DateTime referenceTime) {
        this.referenceTime = referenceTime;
    }

    public void cleanParsedText(String wordToRemove) {
        this.parsedText = parsedText.replace(wordToRemove, "");
    }
    private static Long getStartTimeInLong(List<ParserOutput> datesFound, int index) {
        DateTime startDate = datesFound.get(index).getDateRange().getStart();
        return (startDate != null) ? startDate.getMillis() : null;
    }

    private static Long getEndTimeInLong(List<ParserOutput> datesFound, int index) {
        DateTime endDate = datesFound.get(index).getDateRange().getEnd();
        return (endDate != null) ? endDate.getMillis() : null;
    }
    public static DatesFound emptyDatesRemover(DatesFound dates) {
        List<DateGroup> dateGroups = dates.getDateGroups();
        List<ParserOutput> parserOutputs = dates.getParserOutputs();
        List<DateGroup> dateGroup = new ArrayList<>();
        List<ParserOutput> parserOutput = new ArrayList<>();
        DatesFound date = new DatesFound();
        for (int i = 0; i < dateGroups.size(); i++) {
            Long startDateInBody = getStartTimeInLong(parserOutputs, i);
            Long endDateInBody = getEndTimeInLong(parserOutputs, i);
            if (startDateInBody != null || endDateInBody != null) {
                dateGroup.add(dateGroups.get(i));
                parserOutput.add(parserOutputs.get(i));
            } else {
                LOGGER.info("DateTimeExtractor :: Unparsed date is present :: "+ parserOutputs.get(i).getText());
            }
        }
        date.setDateGroups(dateGroup);
        date.setParserOutputs(parserOutput);
        return date;
    }

    private void tagShrinker(String parseText, ParsedDate parserDateCurrent) {
        List<Triple<String, Integer, Integer>> triples = parserDateCurrent.getOutputWithOffsets();
        for (int i = 0; i < triples.size(); i++) {
            Triple<String, Integer, Integer> triple = triples.get(i);
            String tag = triple.first();
            String textOne = parseText.substring(triple.second(), triple.third());

            Triple<String, Integer, Integer> triplee = i != (triples.size() - 1) ? triples.get(i + 1) : null;
            String textTwo = triplee != null ? parseText.substring(triplee.second(), triplee.third()) : null;
            String tagg = triplee != null ? triplee.first() : " "; //NO I18n

            if (tag.equals("exact_time") && tagg.equals("exact_time")) {
                Triple<String, Integer, Integer> tripleLocal = new Triple<>("exact_time", triple.second(), triplee.third());  //NO I18n
                triples.remove(i + 1);
                triples.set(i, tripleLocal);
                parserDateCurrent.setOutputWithoffsets(triples);
                parserDateCurrent.setTaggedWithXML(parserDateCurrent.getTaggedWithXML().replace("<exact_time>" + textOne + "</exact_time> " + "<exact_time> " + textTwo + "</exact_time>", "<exact_time>" + textOne + textTwo + "</exact_time>"));  //NO I18n
            }
            Triple<String, Integer, Integer> triplePrev = i > 0 ? triples.get(i - 1) : null;
            String tagPrev = triplePrev != null ? triplePrev.first() : null;

            if (tag.equals("exact_number") && tagg.equals("exact_time") && !((tagPrev.equals("month_of_year")) && (TIMEFORMATREGEX.matcher(textTwo).find() || TIMEFORMATREGEXHMS.matcher(textTwo).find()))) {
                Triple<String, Integer, Integer> tripleLocal = new Triple<>("exact_time", triple.second(), triplee.third());  //NO I18n
                triples.remove(i + 1);
                triples.set(i, tripleLocal);
                parserDateCurrent.setOutputWithoffsets(triples);
                parserDateCurrent.setTaggedWithXML(parserDateCurrent.getTaggedWithXML().replace("<exact_number>" + textOne + "</exact_number> " + "<exact_time>" + textTwo + "</exact_time>", "<exact_time>" + textOne + ":" + textTwo + "</exact_time>"));  //NO I18n
                parsedText = parsedText.replace(textOne + " " + textTwo, textOne + ":" + textTwo);  //NO I18n
                parsedText = parsedText.replace("to:", "to ");//NO I18n
            }
        }

    }

    private void tagAlternator(String parseText, ParsedDate parserDateCurrent) {
        String inputSentence = dateTimeEssentials.getSentence();
        List<Triple<String, Integer, Integer>> triples = parserDateCurrent.getOutputWithOffsets();
        String tag_xml = parserDateCurrent.getTaggedWithXML();
        for (int i = 0; i < triples.size(); i++) {
            Triple<String, Integer, Integer> triple = triples.get(i);
            String tag = triple.first();
            String text = parseText.substring(triple.second(), triple.third());
            Triple<String, Integer, Integer> triplee = i != (triples.size() - 1) ? triples.get(i + 1) : null;
            String tagg = triplee != null ? triplee.first() : null;
            if (tag.equals("exact_number") || tag.equals("exact_year")) {
                if (DATEFORMATREGEX.matcher(text).find()) {
                    Triple<String, Integer, Integer> tr = new Triple<>("exact_date", triple.second(), triple.third()); //No I18N
                    triples.set(i, tr);
                    parserDateCurrent.setOutputWithoffsets(triples);
                    parserDateCurrent.setTaggedWithXML(parserDateCurrent.getTaggedWithXML().replace("<exact_number>" + text + "</exact_number>", "<exact_date>" + text + "</exact_date>")); //No I18N
                } else if (!(NUMBERFORMAT_23DIGIT.matcher(text).find()) && (TIMEFORMATREGEX.matcher(text).find()) || ((tagg != null) && ((NUMBERFORMAT_4DIGIT.matcher(text).find()) && (tagg.equals("hour_span"))))) {
                    Triple<String, Integer, Integer> triplePrev = i > 0 ? triples.get(i - 1) : null;
                    String tagPrev = triplePrev != null ? triplePrev.first() : null;
                    if (tagg != null && (NUMBERFORMAT_1DIGIT.matcher(text).find() || NUMBERFORMAT_23DIGIT.matcher(text).find()) && (tagg.equals("exact_time") || tagg.equals("hour_span") || tagg.equals("day_span") || tagg.equals("week_span") || tagg.equals("month_span") || tagg.equals("year_span") || tagg.equals("exact_year") || tagg.equals("month_of_year") || tagg.equals("minute_span") || tagg.equals("second_span"))) {
                        LOGGER.info(" Date is  an  hr span format");
                    } else if (!((triples.size() == 2 || triples.size() == 3) && (tagPrev != null && tagPrev.equals("month_of_year")))) {
                        Triple<String, Integer, Integer> tr = new Triple<>("exact_time", triple.second(), triple.third()); //No I18N
                        triples.set(i, tr);
                        parserDateCurrent.setOutputWithoffsets(triples);
                        if (tag.equals("exact_number")) {
                            parserDateCurrent.setTaggedWithXML(parserDateCurrent.getTaggedWithXML().replace("<exact_number>" + text + "</exact_number>", "<exact_time>" + text + "</exact_time>"));//No I18N
                        } else {
                            parserDateCurrent.setTaggedWithXML(parserDateCurrent.getTaggedWithXML().replace("<exact_year>" + text + "</exact_year>", "<exact_time>" + text + "</exact_time>")); //No I18N
                        }
                    }
                }
            } else if (tag.equals("exact_date") && (!(inputSentence.matches((".*(equals |equal to |= |=)" + text))))) { //No I18N
                if (TIMEFORMATREGEX.matcher(text).find() || TIMEFORMATREGEXHMS.matcher(text).find()) {
                    Triple<String, Integer, Integer> tr = new Triple<>("exact_time", triple.second(), triple.third()); //No I18N
                    triples.set(i, tr);
                    parserDateCurrent.setOutputWithoffsets(triples);
                    parserDateCurrent.setTaggedWithXML(parserDateCurrent.getTaggedWithXML().replace("<exact_date>" + text + "</exact_date>", "<exact_time>" + text + "</exact_time>")); //No I18N
                } else if (NUMBERFORMATREGEX.matcher(text).find()) {
                    Triple<String, Integer, Integer> tr = new Triple<>("exact_number", triple.second(), triple.third()); //No I18N
                    triples.set(i, tr);
                    parserDateCurrent.setOutputWithoffsets(triples);
                    parserDateCurrent.setTaggedWithXML(parserDateCurrent.getTaggedWithXML().replace("<exact_date>" + text + "</exact_date>", "<exact_number>" + text + "</exact_number>")); //No I18N
                } else if(MONTHOFYEAR.matcher(text).find()){
                    Triple<String, Integer, Integer> tr = new Triple<>("month_of_year", triple.second(), triple.third()); //No I18N
                    triples.set(i, tr);
                    parserDateCurrent.setOutputWithoffsets(triples);
                    parserDateCurrent.setTaggedWithXML(parserDateCurrent.getTaggedWithXML().replace("<exact_date>" + text + "</exact_date>", "<month_of_year>" + text + "</month_of_year>")); //No I18N

                }
            } else if (tag.equals("exact_time")) {
                if (DATEMONTHFORMATREGEX.matcher(text).find()) {
                    Triple<String, Integer, Integer> tr = new Triple<>("exact_date", triple.second(), triple.third()); //No I18N
                    triples.set(i, tr);
                    parserDateCurrent.setOutputWithoffsets(triples);
                    parserDateCurrent.setTaggedWithXML(parserDateCurrent.getTaggedWithXML().replace("<exact_time>" + text + "</exact_time>", "<exact_date>" + text + "</exact_date>")); //No I18N
                }

            } else if (tag.equals("implict_prefix") && triples.size() == 1) {
                Triple<String, Integer, Integer> tr = new Triple<>("exact_date", triple.second(), triple.third()); //No I18N
                triples.set(i, tr);
                parserDateCurrent.setOutputWithoffsets(triples);
                parserDateCurrent.setTaggedWithXML(parserDateCurrent.getTaggedWithXML().replace("<implict_prefix>" + text + "</implict_prefix>", "<exact_date>" + text + "</exact_date>")); //No I18N
            }
        }

        if (tag_xml.contains("day_of_week") && tag_xml.contains("month_of_year") && tag_xml.contains("exact_number")) {
            List<Triple<String, Integer, Integer>> triple = parserDateCurrent
                .getOutputWithOffsets();
            String date_xml = parserDateCurrent.getTaggedWithXML();
            for (int i = 0; i < triple.size(); i++) {
                Triple<String, Integer, Integer> triplet = triples.get(i);
                String tag = triplet.first();
                if (tag.equals("day_of_week")) {
                    String text = parseText.substring(triplet.second(), triplet.third());
                    triple.remove(i);
                    parserDateCurrent.setOutputWithoffsets(triple);
                    parserDateCurrent.setTaggedWithXML(date_xml.replace("<day_of_week>" + text + "</day_of_week>", "")); //No I18N//No I18N
                }
            }

        }
    }
}
