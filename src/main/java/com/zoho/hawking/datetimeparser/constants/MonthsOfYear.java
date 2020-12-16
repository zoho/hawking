package com.zoho.hawking.datetimeparser.constants;

public enum MonthsOfYear {

    MONTH_ONE(1),

    MONTH_TWO(2),

    MONTH_THREE(3),

    MONTH_FOUR(4),

    MONTH_FIVE(5),

    MONTH_SIX(6),

    MONTH_SEVEN(7),

    MONTH_EIGHT(8),

    MONTH_NINE(9),

    MONTH_TEN(10),

    MONTH_ELEVEN(11),

    MONTH_TWELE(12),

    MONTH_THIRTEEN(13);

    private final int value;

    MonthsOfYear(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
