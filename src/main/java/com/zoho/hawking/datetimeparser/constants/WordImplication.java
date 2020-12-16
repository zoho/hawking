package com.zoho.hawking.datetimeparser.constants;

public enum WordImplication {

    START_RANGE(1), //since 1990

    END_RANGE(2), //util 1990

    REMINDER(3),

    SET(3); //Every friday



    private final int value;

    WordImplication(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
