package com.zoho.hawking.datetimeparser.constants;

public enum Tense {

    PAST(1),

    RECENT_PAST(2),

    PRESENT(3),

    IMMEDIATE_FUTURE(4),

    FUTURE(5);

    private final int value;

    Tense(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
