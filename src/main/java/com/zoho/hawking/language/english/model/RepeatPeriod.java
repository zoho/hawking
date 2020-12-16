//$Id$
package com.zoho.hawking.language.english.model;

public class RepeatPeriod {

    private long years;
    private long months;
    private long weeks;
    private long days;
    private long hour;
    private long customDate;

    public RepeatPeriod(long years, long months, long weeks, long days, long hour, long customDate) {
        this.years = years;
        this.months = months;
        this.weeks = weeks;
        this.days = days;
        this.hour = hour;
        this.customDate = customDate;
    }

    public long getYears() {
        return years;
    }

    public long getMonths() {
        return months;
    }

    public long getWeeks() {
        return weeks;
    }

    public long getDays() {
        return days;
    }

    public long getHour() {
        return hour;
    }

    public long getCustomDate() {
        return customDate;
    }

}	
