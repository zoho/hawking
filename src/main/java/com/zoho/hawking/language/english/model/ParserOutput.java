//$Id$
package com.zoho.hawking.language.english.model;

import java.util.List;

public class ParserOutput {

    private Integer id;
    private DateRange dateRange;
    private String parserLabel;
    private Integer parserStartIndex;
    private String text;
    private Boolean isTimeZonePresent;
    private Boolean isExactTimePresent;
    private String timezoneOffset;
    private Integer parserEndIndex;
    private List<RecognizerOutput> recognizerOutputs;

    public ParserOutput() {
    }

    public ParserOutput(Integer id, DateRange dateRange, String parserLabel, Integer parserStartIndex, String text, Boolean isTimeZonePresent, Boolean isExactTimePresent, String timezoneOffset, Integer parserEndIndex, List<RecognizerOutput> recognizerOutputs) {
        this.id = id;
        this.dateRange = dateRange;
        this.parserLabel = parserLabel;
        this.parserStartIndex = parserStartIndex;
        this.text = text;
        this.isTimeZonePresent = isTimeZonePresent;
        this.isExactTimePresent = isExactTimePresent;
        this.timezoneOffset = timezoneOffset;
        this.parserEndIndex = parserEndIndex;
        this.recognizerOutputs = recognizerOutputs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public String getParserLabel() {
        return parserLabel;
    }

    public void setParserLabel(String parserLabel) {
        this.parserLabel = parserLabel;
    }

    public Integer getParserStartIndex() {
        return parserStartIndex;
    }

    public void setParserStartIndex(Integer parserStartIndex) {
        this.parserStartIndex = parserStartIndex;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getIsTimeZonePresent() {
        return isTimeZonePresent;
    }

    public void setIsTimeZonePresent(Boolean timeZonePresent) {
        isTimeZonePresent = timeZonePresent;
    }

    public Boolean getIsExactTimePresent() {
        return isExactTimePresent;
    }

    public void setIsExactTimePresent(Boolean exactTimePresent) {
        isExactTimePresent = exactTimePresent;
    }

    public String getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(String timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public Integer getParserEndIndex() {
        return parserEndIndex;
    }

    public void setParserEndIndex(Integer parserEndIndex) {
        this.parserEndIndex = parserEndIndex;
    }

    public List<RecognizerOutput> getRecognizerOutputs() {
        return recognizerOutputs;
    }

    public void setRecognizerOutputs(
        List<RecognizerOutput> recognizerOutputs) {
        this.recognizerOutputs = recognizerOutputs;
    }
}
