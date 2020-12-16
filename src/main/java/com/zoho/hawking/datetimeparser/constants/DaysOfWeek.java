package com.zoho.hawking.datetimeparser.constants;

public enum DaysOfWeek {

    DAY_ONE(1),

    DAY_TWO(2),

    DAY_THREE(3),

    DAY_FOUR(4),

    DAY_FIVE(5),

    DAY_SIX(6),

    DAY_SEVEN(7);

    private final int value;

    DaysOfWeek(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
