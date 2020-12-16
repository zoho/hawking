package com.zoho.hawking.language.english.model;

public class DateInfo {
  private String dateText;
  private int startIndex;
  private int endIndex;

  public DateInfo(String dateText, int startIndex, int endIndex) {
    this.dateText = dateText;
    this.startIndex = startIndex;
    this.endIndex = endIndex;
  }

  public DateInfo() {
  }

  public String getDateText() {
    return dateText;
  }

  public void setDateText(String dateText) {
    this.dateText = dateText;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public void setStartIndex(int startIndex) {
    this.startIndex = startIndex;
  }

  public int getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(int endIndex) {
    this.endIndex = endIndex;
  }
}
