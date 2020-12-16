package com.zoho.hawking.datetimeparser.constants;

public enum HoursOfDay {

    ZEROTH_HOURS(0),

    FIRST_HOUR(1),

    SECOND_HOUR(2),

    THIRD_HOUR(3),

    FOURTH_HOUR(4),

    FIFTH_HOUR(5),

    SIXTH_HOUR(6),

    SEVEN_HOUR(7),

    EIGHT_HOUR(8),

    NINTH_HOUR(9),

    TENTH_HOUR(10),

    ELEVENTH_HOUR(11),

    TWELFTH_HOUR(12),

    THIRTEENTH_HOUR(13),

    FOURTEENTH_HOUR(14),

    FIFTEENTH_HOUR(15),

    SIXTEENTH_HOUR(16),

    SEVENTEENTH_HOUR(17),

    EIGHTEENTH_HOUR(18),

    NINETEENTH_HOUR(19),

    TWENTIETH_HOUR(20),

    TWENTY_FIRST_HOUR(21),

    TWENTY_SECOND_HOUR(22),

    TWENTY_THIRD_HOUR(23),

    TWENTY_FOURTH_HOUR(24);



    private final int value;

    HoursOfDay(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
