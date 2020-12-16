package com.zoho.hawking.datetimeparser.constants;

public enum DateTimeSpan {
    YEAR(1),
    YEARS(2),

    MONTH(3),
    MONTHS(4),

    WEEK(5),
    WEEKS(6),

    DAY(7),
    DAYS(8),

    WEEKEND(9),
    WEEKENDS(10),

    WEEKDAY(11),
    WEEKDAYS(12),

    HOUR(13),
    HOURS(14),

    MINUTE(15),
    MINUTES(16),

    SECOND(17),
    SECONDS(18),

    CUSTOM_DATE(19),
    CUSTOM_DATES(20),

    CURRENT_DAY(21);


    private final int value;

    DateTimeSpan(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
