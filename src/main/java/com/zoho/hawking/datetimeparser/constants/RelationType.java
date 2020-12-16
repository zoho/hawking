package com.zoho.hawking.datetimeparser.constants;

public enum RelationType {

    RELATION_RANGE(1),

    RELATION_DATESET(2),

    RELATION_CONDITIONAL(3);

    private final int value;

    RelationType(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }


}
