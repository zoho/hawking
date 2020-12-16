//$Id$
package com.zoho.hawking.datetimeparser.constants;

import com.zoho.hawking.datetimeparser.WordProperty;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DateTimeParserConstants {
    /*TimeSpan used to identify the span property of the word
     * 	year_span - 0
     * 	month_span - 1
     * 	week_span - 2
     * 	weekday_span - 3
     * 	weekend_span - 4
     * 	day_span - 5
     * 	currentday_span - 6
     * 	hour_span - 7
     * 	minute_span - 8
     * 	second_span - 9
     * 	custom_date - 10*/

    //Year Span components
    public static final WordProperty YEAR = new WordProperty("year", 0); //No I18N
    public static final WordProperty YEARS = new WordProperty("years", 0); //No I18N
    //Month Span components
    public static final WordProperty MONTH = new WordProperty("month", 1); //No I18N
    public static final WordProperty MONTHS = new WordProperty("months", 1); //No I18N
    //Week(sun - sat)(7 days)  Span components
    public static final WordProperty WEEK = new WordProperty("week", 2); //No I18N
    public static final WordProperty WEEKS = new WordProperty("weeks", 2); //No I18N
    //Week(mon - fri)(5/6 days)  Span components
    public static final WordProperty WEEKDAY = new WordProperty("weekday", 3); //No I18N
    public static final WordProperty WEEKDAYS = new WordProperty("weekdays", 3); //No I18N
    //Week(sat - sun)(1/2 days)  Span components
    public static final WordProperty WEEKEND = new WordProperty("weekend", 4); //No I18N
    public static final WordProperty WEEKENDS = new WordProperty("weekends", 4); //No I18N
    //Day Span components
    public static final WordProperty DAY = new WordProperty("day", 5); //No I18N
    public static final WordProperty DAYS = new WordProperty("days", 5); //No I18N
    //CurrentDay Span components
    public static final WordProperty TODAY = new WordProperty("today", 6); //No I18N
    public static final WordProperty NOW = new WordProperty("now", 6); //No I18N
    public static final WordProperty TOMORROW = new WordProperty("tomorrow", 6); //No I18N
    public static final WordProperty YESTERDAY = new WordProperty("yesterday", 6); //No I18N
    //Hour Span components
    public static final WordProperty HOUR = new WordProperty("hour", 7); //No I18N
    public static final WordProperty HOURS = new WordProperty("hours", 7); //No I18N
    public static final WordProperty HR = new WordProperty("hr", 7); //No I18N
    public static final WordProperty HRS = new WordProperty("hrs", 7); //No I18N
    //Minute Span components
    public static final WordProperty MINUTE = new WordProperty("minute", 8); //No I18N
    public static final WordProperty MINUTES = new WordProperty("minutes", 8); //No I18N
    public static final WordProperty MIN = new WordProperty("min", 8); //No I18N
    public static final WordProperty MINS = new WordProperty("mins", 8); //No I18N
    //Second Span components
    public static final WordProperty SECOND = new WordProperty("second", 9); //No I18N
    public static final WordProperty SECONDS = new WordProperty("seconds", 9); //No I18N
    public static final WordProperty SEC = new WordProperty("sec", 9); //No I18N
    public static final WordProperty SECS = new WordProperty("secs", 9); //No I18N

    public static final WordProperty QUARTER_YEARLY = new WordProperty("quarterly", 10); //No I18N
    public static final WordProperty QUARTER = new WordProperty("quarter", 10); //No I18N
    public static final WordProperty QUARTERS = new WordProperty("quarters", 10); //No I18N
    public static final WordProperty HALF_YEARLY = new WordProperty("halfyearly", 10); //No I18N
    public static final WordProperty HALF = new WordProperty("half", 10); //No I18N
    public static final WordProperty HALFS = new WordProperty("halfs", 10); //No I18N
    public static final WordProperty ANNUAL_YEAR = new WordProperty("annualyear", 10); //No I18N
    public static final WordProperty FISCAL_YEAR = new WordProperty("fiscalyear", 10); //No I18N

    public static final Map<String, Integer> DAYS_OF_WEEK = createDayOfWeek();
    public static final Map<String, Integer> MONTHS_OF_YEAR = createMonthOfYear();
    public static final Map<String, Pair<Integer, Integer>> PARTS_OF_DAY = createPartofDay();

    public static final String[] TIME_FORMATS = {
            "hh:mm:ssa", "h:mm:ssa", "hh:mm:ss:SSSa", "h:mm:ss:SSSa", "hh:mm:ssSSSa", "h:mm:ssSSSa", "HH:mm:ss", //NO I18n
            "HH:mm:ss:SSS", "HH:mm:ssSSS", "HH.mm.ss", "HH.mm", "HH:mm", "hh:mma", "h:mma", "ha", "hmma", //NO I18n
            "hhmma", "hh:mm a", "h:mm a", "hh.mma", "h.mma", "h:mm", "HHmm", "HH" //NO I18n
    };

    public static final String TIME_NORMALIZATION_REGEX = "[^\\dA-Za-z0-9:.&]"; //No I18N

    private static Map<String, Integer> createDayOfWeek() {
        HashMap<String, Integer> dayOfWeek = new HashMap<String, Integer>();
        dayOfWeek.put("sunday", 7);
        dayOfWeek.put("monday", 1);
        dayOfWeek.put("tuesday", 2);
        dayOfWeek.put("wednesday", 3);
        dayOfWeek.put("thursday", 4);
        dayOfWeek.put("friday", 5);
        dayOfWeek.put("saturday", 6);
        dayOfWeek.put("sun", 7);
        dayOfWeek.put("mon", 1);
        dayOfWeek.put("tue", 2);
        dayOfWeek.put("wed", 3);
        dayOfWeek.put("thu", 4);
        dayOfWeek.put("thurs", 4);
        dayOfWeek.put("fri", 5);
        dayOfWeek.put("sat", 6);
        return Collections.unmodifiableMap(dayOfWeek);
    }


    private static Map<String, Pair<Integer, Integer>> createPartofDay() {
        HashMap<String, Pair<Integer, Integer>> partOfDay = new HashMap<>();
        partOfDay.put("dawn", Pair.of(3, 5));
        partOfDay.put("early morning", Pair.of(5, 6));
        partOfDay.put("morning", Pair.of(6, 9));
        partOfDay.put("mid morning", Pair.of(9, 11));
        partOfDay.put("noon", Pair.of(12, 12));
        partOfDay.put("afternoon", Pair.of(12, 17));
        partOfDay.put("evening", Pair.of(17, 21));
        partOfDay.put("eve", Pair.of(17, 21));
        partOfDay.put("night", Pair.of(21, 23));
        partOfDay.put("midnight", Pair.of(0, 3));
        partOfDay.put("tonight", Pair.of(21, 23));
        return Collections.unmodifiableMap(partOfDay);
    }

    private static Map<String, Integer> createMonthOfYear() {
        HashMap<String, Integer> monthOfYear = new HashMap<>();
        monthOfYear.put("january", 1);
        monthOfYear.put("february", 2);
        monthOfYear.put("march", 3);
        monthOfYear.put("april", 4);
        monthOfYear.put("may", 5);
        monthOfYear.put("june", 6);
        monthOfYear.put("july", 7);
        monthOfYear.put("august", 8);
        monthOfYear.put("september", 9);
        monthOfYear.put("october", 10);
        monthOfYear.put("november", 11);
        monthOfYear.put("december", 12);
        monthOfYear.put("jan", 1);
        monthOfYear.put("feb", 2);
        monthOfYear.put("mar", 3);
        monthOfYear.put("apr", 4);
        monthOfYear.put("jun", 6);
        monthOfYear.put("jul", 7);
        monthOfYear.put("aug", 8);
        monthOfYear.put("sep", 9);
        monthOfYear.put("oct", 10);
        monthOfYear.put("nov", 11);
        monthOfYear.put("dec", 12);
        return Collections.unmodifiableMap(monthOfYear);
    }

}
