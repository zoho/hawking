//$Id$
package com.zoho.hawking.language.english.model;

public class RecognizerOutput {

    private String recognizerLabel;
    private Integer recognizerStartIndex;
    private Integer recognizerEndIndex;
    private String text;

    public RecognizerOutput() {

    }

    public RecognizerOutput(String recognizerLabel, Integer recognizerStartIndex, Integer recognizerEndIndex, String text) {
        this.recognizerLabel = recognizerLabel;
        this.recognizerStartIndex = recognizerStartIndex;
        this.recognizerEndIndex = recognizerEndIndex;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRecognizerLabel() {
        return recognizerLabel;
    }

    public void setRecognizerLabel(String recognizerLabel) {
        this.recognizerLabel = recognizerLabel;
    }

    public Integer getRecognizerStartIndex() {
        return recognizerStartIndex;
    }

    public void setRecognizerStartIndex(Integer recognizerStartIndex) {
        this.recognizerStartIndex = recognizerStartIndex;
    }

    public Integer getRecognizerEndIndex() {
        return recognizerEndIndex;
    }

    public void setRecognizerEndIndex(Integer recognizerEndIndex) {
        this.recognizerEndIndex = recognizerEndIndex;
    }

}
