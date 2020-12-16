//$Id$
package com.zoho.hawking.datetimeparser.configuration;

public class SpanDefault {
    /*
     * Configuration for startAndEndOfTimeSpan or CurrentTime
     *
     * 	startAndEndOfTimeSpan - 0
     * 	currentTime - 1
     *
     * */
    private int yearSpan;
    private int monthSpan;
    private int weekSpan;
    private int daySpan;
    private int hourSpan;
    private int minuteSpan;
    private int secondSpan;

    SpanDefault() {

    }

    public int getYearSpan() {
        return yearSpan;
    }

    public void setYearSpan(int yearSpan) {
        this.yearSpan = yearSpan;
    }

    public int getMonthSpan() {
        return monthSpan;
    }

    public void setMonthSpan(int monthSpan) {
        this.monthSpan = monthSpan;
    }

    public int getWeekSpan() {
        return weekSpan;
    }

    public void setWeekSpan(int weekSpan) {
        this.weekSpan = weekSpan;
    }

    public int getDaySpan() {
        return daySpan;
    }

    public void setDaySpan(int daySpan) {
        this.daySpan = daySpan;
    }

    public int getHourSpan() {
        return hourSpan;
    }

    public void setHourSpan(int hourSpan) {
        this.hourSpan = hourSpan;
    }

    public int getMinuteSpan() {
        return minuteSpan;
    }

    public void setMinuteSpan(int minuteSpan) {
        this.minuteSpan = minuteSpan;
    }

    public int getSecondSpan() {
        return secondSpan;
    }

    public void setSecondSpan(int secondSpan) {
        this.secondSpan = secondSpan;
    }


}
