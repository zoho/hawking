package com.zoho.hawking.language.english;

import com.zoho.hawking.datetimeparser.WordProperty;
import com.zoho.hawking.datetimeparser.constants.*;
import edu.stanford.nlp.ling.Word;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class DateTimeWordProperties {

    /*
     *
     * Prefix used define the property of the word
     * 	start - 0
     * 	end - 1
     * 	secondary - 3
     * nothing - -1
     *
     * TenseIndicator used define property of the word
     * 	past - 0
     * 	present - 1
     * 	future - 2
     * 	immediate_future - 3
     * 	Immediate - 4
     * 	nothing - -1
     * */

    /*Primary Prefix Start Range*/
    public static final WordProperty BEGINNING = new WordProperty("beginning", WordImplication.START_RANGE); //No I18N
    public static final WordProperty STARTING = new WordProperty("starting", WordImplication.START_RANGE); //No I18N
    public static final WordProperty AFTER = new WordProperty("after", WordImplication.START_RANGE, true); //No I18N
    public static final WordProperty FROM = new WordProperty("from", WordImplication.START_RANGE); //No I18N
    /*Primary Prefix Start Range And Tense*/
    public static final WordProperty SINCE = new WordProperty("since", WordImplication.START_RANGE, Tense.PAST); //No I18N
    public static final WordProperty BACK = new WordProperty("back",  WordImplication.START_RANGE, Tense.PAST); //No I18N
    public static final WordProperty AGO = new WordProperty("ago",  WordImplication.START_RANGE, Tense.PAST); //No I18N
    /*Primary Prefix End Range*/
    public static final WordProperty TILL = new WordProperty("till", WordImplication.END_RANGE); //No I18N
    public static final WordProperty UNTIL = new WordProperty("until", WordImplication.END_RANGE); //No I18N
    public static final WordProperty WITHIN = new WordProperty("within", WordImplication.END_RANGE); //No I18N
    public static final WordProperty ENDING = new WordProperty("ending", WordImplication.END_RANGE); //No I18N
    public static final WordProperty BEFORE = new WordProperty("before", WordImplication.END_RANGE, true); //No I18N
    /*Tense Indicator PAST*/
    public static final WordProperty LAST = new WordProperty("last", Tense.PAST); //No I18N
    public static final WordProperty PREVIOUS = new WordProperty("previous", Tense.PAST); //No I18N
    /*Tense Indicator FUTURE*/
    public static final WordProperty NEXT = new WordProperty("next", Tense.FUTURE); //No I18N
    public static final WordProperty LATER = new WordProperty("later", Tense.FUTURE); //No I18N
    /*Tense Indicator PRESENT*/
//    public static final WordProperty NOW = new WordProperty("now", Tense.PRESENT); //No I18N
    /*Tense Indicator IMMEDIATE FUTURE*/
    public static final WordProperty UPCOMING = new WordProperty("upcoming", Tense.IMMEDIATE_FUTURE); //No I18N
    public static final WordProperty COMING = new WordProperty("coming", Tense.IMMEDIATE_FUTURE); //No I18N
    /*Tense Indicator IMMEDIATE*/
    public static final WordProperty THIS = new WordProperty("this", Tense.IMMEDIATE); //No I18N
    public static final WordProperty THESE = new WordProperty("these", Tense.IMMEDIATE); //No I18N
    public static final WordProperty CURRENT = new WordProperty("current", Tense.IMMEDIATE); //No I18N
    /*Tense Indicator REMAINDER*/
    public static final WordProperty REST = new WordProperty("rest", WordImplication.REMINDER); //No I18N
    public static final WordProperty REMAIN = new WordProperty("remain", WordImplication.REMINDER); //Stem of the word can also be used (Remainder,Remaining) //No I18N
    /*Tense Indicator Immediate PAST*/
    public static final WordProperty PAST = new WordProperty("past", Tense.RECENT_PAST); //No I18N

    /*Secondary Prefix Start Range*/
//    public static final WordProperty AT = new WordProperty("at", 2, -1); //No I18N
//    public static final WordProperty ON = new WordProperty("on", 2, -1); //No I18N
//    public static final WordProperty IN_AN = new WordProperty("in an", 2, -1); //No I18N
//    public static final WordProperty IN_THE = new WordProperty("in the", 2, -1); //No I18N
//    public static final WordProperty IN_A = new WordProperty("in a", 2, -1); //No I18N
//    public static final WordProperty IN = new WordProperty("in", 2, -1); //No I18N
//    public static final WordProperty FOR_AN = new WordProperty("for an", 2, -1); //No I18N
//    public static final WordProperty FOR = new WordProperty("for", 2, -1); //No I18N
//    public static final WordProperty OF_THE = new WordProperty("of the", 2, -1); //No I18N
//    public static final WordProperty OF = new WordProperty("of", 2, -1); //No I18N
    public static final WordProperty FEW = new WordProperty("few", 2, -1); //No I18N


    /*Set Prefix */
    public static final WordProperty EVERY = new WordProperty("every", WordImplication.SET); //No I18N
    public static final WordProperty EACH = new WordProperty("each", WordImplication.SET); //No I18N
    public static final WordProperty REGULARLY = new WordProperty("regularly", WordImplication.SET); //No I18N

    /*Relationship prefix
     *
     * 1) range relation (from to)
     * 2) conditional (30 days from today)*/
//
//    public static final WordProperty TO = new WordProperty("to", 10); //No I18N
//    public static final WordProperty BETWEEN = new WordProperty("between", 10); //No I18N
//    public static final WordProperty INBETWEEN = new WordProperty("inbetween", 10); //No I18N
//    public static final WordProperty DASH = new WordProperty("-", 10); //No I18N
//
//    public static final WordProperty OR = new WordProperty("or", 11); //No I18N
//    public static final WordProperty ASWELLAS = new WordProperty("aswellas", 11); //No I18N
//    public static final WordProperty AND = new WordProperty("and", 11); //No I18N
//    public static final WordProperty COMMA = new WordProperty(",", 11);


    //Year Span components
    public static final WordProperty YEAR = new WordProperty("year", DateTimeSpan.YEAR); //No I18N
    public static final WordProperty YEARS = new WordProperty("years", DateTimeSpan.YEARS); //No I18N
    //Month Span components
    public static final WordProperty MONTH = new WordProperty("month", DateTimeSpan.MONTH); //No I18N
    public static final WordProperty MONTHS = new WordProperty("months", DateTimeSpan.MONTHS); //No I18N
    //Week(sun - sat)(7 days)  Span components
    public static final WordProperty WEEK = new WordProperty("week", DateTimeSpan.WEEK); //No I18N
    public static final WordProperty WEEKS = new WordProperty("weeks", DateTimeSpan.WEEKS); //No I18N
    //Week(mon - fri)(5/6 days)  Span components
    public static final WordProperty WEEKDAY = new WordProperty("weekday", DateTimeSpan.WEEKDAY); //No I18N
    public static final WordProperty WEEKDAYS = new WordProperty("weekdays", DateTimeSpan.WEEKDAYS); //No I18N
    //Week(sat - sun)(1/2 days)  Span components
    public static final WordProperty WEEKEND = new WordProperty("weekend", DateTimeSpan.WEEKEND); //No I18N
    public static final WordProperty WEEKENDS = new WordProperty("weekends", DateTimeSpan.WEEKENDS); //No I18N
    //Day Span components
    public static final WordProperty DAY = new WordProperty("day", DateTimeSpan.DAY); //No I18N
    public static final WordProperty DAYS = new WordProperty("days", DateTimeSpan.DAYS); //No I18N
    //CurrentDay Span components
    public static final WordProperty TODAY = new WordProperty("today", DateTimeSpan.CURRENT_DAY); //No I18N
    public static final WordProperty NOW = new WordProperty("now", DateTimeSpan.CURRENT_DAY, Tense.PRESENT); //No I18N
    public static final WordProperty TOMORROW = new WordProperty("tomorrow", DateTimeSpan.CURRENT_DAY); //No I18N
    public static final WordProperty YESTERDAY = new WordProperty("yesterday", DateTimeSpan.CURRENT_DAY); //No I18N
    //Hour Span components
    public static final WordProperty HOUR = new WordProperty("hour", DateTimeSpan.HOUR); //No I18N
    public static final WordProperty HOURS = new WordProperty("hours", DateTimeSpan.HOURS); //No I18N
    public static final WordProperty HR = new WordProperty("hr", DateTimeSpan.HOUR); //No I18N
    public static final WordProperty HRS = new WordProperty("hrs", DateTimeSpan.HOURS); //No I18N
    //Minute Span components
    public static final WordProperty MINUTE = new WordProperty("minute", DateTimeSpan.MINUTE); //No I18N
    public static final WordProperty MINUTES = new WordProperty("minutes", DateTimeSpan.MINUTES); //No I18N
    public static final WordProperty MIN = new WordProperty("min", DateTimeSpan.MINUTE); //No I18N
    public static final WordProperty MINS = new WordProperty("mins", DateTimeSpan.MINUTES); //No I18N
    //Second Span components
    public static final WordProperty SECOND = new WordProperty("second", DateTimeSpan.SECOND); //No I18N
    public static final WordProperty SECONDS = new WordProperty("seconds", DateTimeSpan.SECONDS); //No I18N
    public static final WordProperty SEC = new WordProperty("sec", DateTimeSpan.SECOND); //No I18N
    public static final WordProperty SECS = new WordProperty("secs", DateTimeSpan.SECONDS); //No I18N

    public static final WordProperty QUARTER_YEARLY = new WordProperty("quarterly", DateTimeSpan.CUSTOM_DATE); //No I18N
    public static final WordProperty QUARTER = new WordProperty("quarter", DateTimeSpan.CUSTOM_DATE); //No I18N
    public static final WordProperty QUARTERS = new WordProperty("quarters", DateTimeSpan.CUSTOM_DATES); //No I18N
    public static final WordProperty HALF_YEARLY = new WordProperty("halfyearly", DateTimeSpan.CUSTOM_DATE); //No I18N
    public static final WordProperty HALF = new WordProperty("half", DateTimeSpan.CUSTOM_DATE); //No I18N
    public static final WordProperty HALFS = new WordProperty("halfs", DateTimeSpan.CUSTOM_DATES); //No I18N
    public static final WordProperty ANNUAL_YEAR = new WordProperty("annualyear", DateTimeSpan.CUSTOM_DATE); //No I18N
    public static final WordProperty FISCAL_YEAR = new WordProperty("fiscalyear", DateTimeSpan.CUSTOM_DATE); //No I18N



    //SET
    public static final WordProperty YEARWISE = new WordProperty("yearwise", DateTimeSpan.YEAR); //No I18N
    public static final WordProperty YEARLY = new WordProperty("yearly", DateTimeSpan.YEAR); //No I18N
    public static final WordProperty ANNUALLY = new WordProperty("annually", DateTimeSpan.YEAR); //No I18N

    public static final WordProperty MONTHWISE = new WordProperty("monthwise", DateTimeSpan.MONTH); //No I18N
    public static final WordProperty MONTHLY = new WordProperty("monthly", DateTimeSpan.MONTH); //No I18N

    public static final WordProperty WEEKWISE = new WordProperty("weekwise", DateTimeSpan.WEEK); //No I18N
    public static final WordProperty WEEKLY = new WordProperty("weekly", DateTimeSpan.WEEK); //No I18N

    public static final WordProperty DAYWISE = new WordProperty("daywise", DateTimeSpan.DAY); //No I18N
    public static final WordProperty DAILY = new WordProperty("daily", DateTimeSpan.DAY); //No I18N

    public static final WordProperty HOURWISE = new WordProperty("hourwise", DateTimeSpan.HOUR); //No I18N
    public static final WordProperty HOURLY = new WordProperty("hourly", DateTimeSpan.HOUR); //No I18N

    public static final WordProperty SUNDAY = new WordProperty("sunday", DaysOfWeek.DAY_SEVEN, new String[]{"sun"});

    public static final WordProperty MONDAY = new WordProperty("monday", DaysOfWeek.DAY_ONE, new String[]{"mon"});

    public static final WordProperty TUESDAY = new WordProperty("tuesday", DaysOfWeek.DAY_TWO, new String[]{"tue"});

    public static final WordProperty WEDNESDAY = new WordProperty("wednesday", DaysOfWeek.DAY_THREE, new String[]{"wed"});

    public static final WordProperty THURSDAY = new WordProperty("thursday", DaysOfWeek.DAY_FOUR, new String[]{"thu", "thurs"});

    public static final WordProperty FRIDAY = new WordProperty("friday", DaysOfWeek.DAY_FIVE, new String[]{"fri"});

    public static final WordProperty SATURDAY = new WordProperty("saturday", DaysOfWeek.DAY_SIX, new String[]{"sat"});


    public static final WordProperty JANUARY = new WordProperty("january", MonthsOfYear.MONTH_ONE, new String[]{"jan"});

    public static final WordProperty FEBRUARY = new WordProperty("february", MonthsOfYear.MONTH_TWO, new String[]{"feb"});

    public static final WordProperty MARCH = new WordProperty("march", MonthsOfYear.MONTH_THREE, new String[]{"mar"});

    public static final WordProperty APRIL = new WordProperty("april", MonthsOfYear.MONTH_FOUR, new String[]{"apr"});

    public static final WordProperty MAY = new WordProperty("may", MonthsOfYear.MONTH_FIVE, new String[]{});

    public static final WordProperty JUNE = new WordProperty("june", MonthsOfYear.MONTH_SIX, new String[]{"jun"});

    public static final WordProperty JULY = new WordProperty("july", MonthsOfYear.MONTH_SEVEN, new String[]{"jul"});

    public static final WordProperty AUGUST = new WordProperty("august", MonthsOfYear.MONTH_EIGHT, new String[]{"aug"});

    public static final WordProperty SEPTEMBER = new WordProperty("september", MonthsOfYear.MONTH_NINE, new String[]{"sep","sept"});

    public static final WordProperty OCTOBER = new WordProperty("october", MonthsOfYear.MONTH_TEN, new String[]{"oct"});

    public static final WordProperty NOVEMBER = new WordProperty("november", MonthsOfYear.MONTH_ELEVEN, new String[]{"nov"});

    public static final WordProperty DECEMBER = new WordProperty("december", MonthsOfYear.MONTH_TWELE, new String[]{"dec"});



    public static final WordProperty DAWN = new WordProperty("dawn", Pair.of(HoursOfDay.THIRD_HOUR, HoursOfDay.FIFTH_HOUR), new String[]{});

    public static final WordProperty EARLY_MORNING = new WordProperty("early morning", Pair.of(HoursOfDay.FIFTH_HOUR, HoursOfDay.SIXTH_HOUR), new String[]{});

    public static final WordProperty MORNING = new WordProperty("morning", Pair.of(HoursOfDay.SIXTH_HOUR, HoursOfDay.NINTH_HOUR), new String[]{});

    public static final WordProperty MID_MORNING = new WordProperty("mid morning", Pair.of(HoursOfDay.NINTH_HOUR, HoursOfDay.ELEVENTH_HOUR), new String[]{});

    public static final WordProperty NOON = new WordProperty("noon", Pair.of(HoursOfDay.TWELFTH_HOUR, HoursOfDay.TWELFTH_HOUR), new String[]{});

    public static final WordProperty AFTERNOON = new WordProperty("afternoon", Pair.of(HoursOfDay.TWELFTH_HOUR, HoursOfDay.SEVENTEENTH_HOUR), new String[]{});

    public static final WordProperty EVENING = new WordProperty("evening", Pair.of(HoursOfDay.SEVENTEENTH_HOUR, HoursOfDay.TWENTY_FIRST_HOUR), new String[]{"eve"});

    public static final WordProperty NIGHT = new WordProperty("night", Pair.of(HoursOfDay.TWENTY_FIRST_HOUR, HoursOfDay.TWENTY_THIRD_HOUR), new String[]{});

    public static final WordProperty MIDNIGHT = new WordProperty("midnight", Pair.of(HoursOfDay.ZEROTH_HOURS, HoursOfDay.THIRD_HOUR), new String[]{"mid night"});

    public static final WordProperty TONIGHT = new WordProperty("tonight", Pair.of(HoursOfDay.TWENTY_FIRST_HOUR, HoursOfDay.TWENTY_THIRD_HOUR), new String[]{""});



    static final WordProperty[] ALL_WORDS = {
            BEGINNING, STARTING, AFTER, FROM, SINCE, BACK, AGO, TILL, UNTIL, WITHIN, ENDING, BEFORE, PAST,
            LAST, PREVIOUS, NEXT, LATER, CURRENT, NOW, REST, UPCOMING, COMING, THIS, THESE,  FEW, EVERY, EACH, REGULARLY,
            YEAR, YEARS, MONTH, MONTHS, WEEK, WEEKS, WEEKDAY, WEEKDAYS, WEEKEND, WEEKENDS, DAY, DAYS,
            TODAY, NOW, TOMORROW, YESTERDAY, HOUR, HOURS, HR, HRS, MINUTE, MINUTES, MIN, MINS,
            SECOND, SECONDS, SEC, SECS, QUARTER_YEARLY, QUARTER, QUARTERS, HALF_YEARLY, HALF, HALFS, ANNUAL_YEAR, FISCAL_YEAR,
            YEARWISE, YEARLY, ANNUALLY, MONTHWISE, MONTHLY, WEEKWISE, WEEKLY, DAYWISE, DAILY, HOURWISE, HOURLY,
            SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY,
            JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER,
            DAWN, EARLY_MORNING, MORNING, MID_MORNING, NOON, AFTERNOON, EVENING, NIGHT, MIDNIGHT, TONIGHT
    };



    public static final String[] TIME_FORMATS = {
            "hh:mm:ssa", "h:mm:ssa", "hh:mm:ss:SSSa", "h:mm:ss:SSSa", "hh:mm:ssSSSa", "h:mm:ssSSSa", "HH:mm:ss", //NO I18n
            "HH:mm:ss:SSS", "HH:mm:ssSSS", "HH.mm.ss", "HH.mm", "HH:mm", "hh:mma", "h:mma", "ha", "hmma", //NO I18n
            "hhmma", "hh:mm a", "h:mm a", "hh.mma", "h.mma", "h:mm", "HHmm", "HH" //NO I18n
    };

    public static final String TIME_NORMALIZATION_REGEX = "[^\\dA-Za-z0-9:.&]"; //No I18N


}
