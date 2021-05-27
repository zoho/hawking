//$Id$
package com.zoho.hawking.utils;

import com.zoho.hawking.HawkingTimeParser;
import com.zoho.hawking.language.english.Recognizer;
import com.zoho.hawking.datetimeparser.DateAndTime;
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

    public void setParserOutput(ParserOutput parserOutput) {
        this.parserOutput = parserOutput;
    }
    public void setDateGroup(DateGroup dateGroup) {
        this.dateGroup = dateGroup;
    }

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



    public void setParsedDate() {
        parsedText = removeTimeZone(parsedText);
        parsedText = cardinalNumberFinder(parsedText);
        ParsedDate parserDate = Recognizer.recognize(parsedText);
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
                returnText = returnText.replaceAll("([,“”\"~()@])", " "); //No I18N
                returnText = returnText.replaceAll("(hrs|hr|Hr|Hrs)", " hrs"); //No I18N
                returnText = returnText.replaceAll("date", "day"); //No I18N
                returnText = returnText.replaceAll("nextweek", "next week"); //No I18N
                returnText = returnText.replaceAll("Year", "year"); //No I18N
                returnText = returnText.replaceAll("\\b(?i)" + "final", "last"); //No I18N
                returnText = returnText.replaceAll("\\.$", " "); //No I18N
                returnText = returnText.replaceAll("^- ", " "); //No I18N
                returnText = returnText.replaceAll("post", "after"); //No I18N
                returnText = returnText.replaceAll("breakfast", "8 AM"); //No I18N
                returnText = returnText.replaceAll("lunch", "1 PM"); //No I18N
                returnText = returnText.replaceAll("dinner", "8 PM"); //No I18N
                returnText = returnText.replaceAll("null",""); //No I18N
                returnText = returnText.replaceAll("\\s{2,}", " ").trim();//No I18N
                return returnText;
            }
        }
        returnText = parsedText.replaceAll("([,“”\"~()@])", " "); //No I18N
        returnText = returnText.replaceAll("(hrs|Hrs|Hr|hr)", " hrs"); //No I18N
        returnText = returnText.replaceAll("date", "day"); //No I18N
        returnText = returnText.replaceAll("nextweek", "next week"); //No I18N
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
        returnText = returnText.replaceAll("\\s{2,}", " ").trim();//No I18N
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
        this.parsedText = parsedText.replaceFirst("^"+wordToRemove, "");
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
}
