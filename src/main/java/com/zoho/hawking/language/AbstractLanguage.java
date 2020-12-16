package com.zoho.hawking.language;

import com.zoho.hawking.datetimeparser.WordProperty;
import com.zoho.hawking.datetimeparser.configuration.HawkingConfiguration;
import com.zoho.hawking.datetimeparser.constants.DateTimeSpan;
import com.zoho.hawking.datetimeparser.constants.Tense;
import com.zoho.hawking.datetimeparser.constants.WordImplication;
import com.zoho.hawking.utils.DateTimeProperties;
import edu.stanford.nlp.util.Triple;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class AbstractLanguage {

    public List<String> primaryPrefix;

    //    public final List<String> SECONDARY_PREFIX = createPrefixArray(2, 2);
    public List<String> wordsWithoutStartRange;
    public List<String> wordsWithoutEndRange;
    public List<String> startRange;

    public List<String> endRange;

    public List<String> tenseIndicators;

    // words that directly express date and time
    public List<String> pastWords;

    public List<String> futureWords;

    public List<String> presentWords;

    // expressing the time frame
    public List<String> immediatePast;

    public List<String> immediateFuture;

//    public final List<String> IMMEDIATE = createTenseArray(4, 4);

    public List<String> remainder;

    public List<String> yearWords;

    public List<String> yearsWords;

    public List<String> monthWords;

    public List<String> monthsWords;

    public List<String> weekWords;

    public List<String> weeksWords;

    public List<String> weekdayWords;

    public List<String> weekdaysWords;

    public List<String> weekendWords;

    public List<String> weekendsWords;

    public List<String> dayWords;

    public List<String> daysWords;

    public List<String> currentDayWords;

    public List<String> hourWords;

    public List<String> hoursWords;

    public List<String> minuteWords;

    public List<String> minutesWords;

    public List<String> secondWords;

    public List<String> secondsWords;

    public List<String> customDate;

    public List<String> customDates;

    public Map<String, Integer> daysOfWeek;
    public Map<String, Integer> monthsOfYear;
    public Map<String, Pair<Integer, Integer>> partsOfDay;

    public AbstractLanguage(WordProperty[] allWords){

        this.primaryPrefix = Utils.createPrefixArray(WordImplication.START_RANGE, WordImplication.END_RANGE, allWords);

        this.wordsWithoutStartRange = Utils.createDateWithoutAnyOneOfTheRange(WordImplication.END_RANGE, allWords);

        this.wordsWithoutEndRange = Utils.createDateWithoutAnyOneOfTheRange(WordImplication.START_RANGE, allWords);

        this.startRange = Utils.createPrefixArray(WordImplication.START_RANGE, WordImplication.START_RANGE, allWords);

        this.endRange = Utils.createPrefixArray(WordImplication.END_RANGE, WordImplication.END_RANGE, allWords);

        this.tenseIndicators = Utils.createTenseArray(Tense.PAST, Tense.FUTURE, allWords);

        this.pastWords = Utils.createTenseArray(Tense.PAST, Tense.PAST, allWords);

        this.immediatePast = Utils.createTenseArray(Tense.RECENT_PAST, Tense.RECENT_PAST, allWords);

        this.presentWords = Utils.createTenseArray(Tense.PRESENT, Tense.PRESENT, allWords);

        this.immediateFuture = Utils.createTenseArray(Tense.IMMEDIATE_FUTURE, Tense.IMMEDIATE_FUTURE, allWords);

        this.futureWords = Utils.createTenseArray(Tense.FUTURE, Tense.FUTURE, allWords);

        this.remainder = Utils.createPrefixArray(WordImplication.REMINDER, WordImplication.REMINDER, allWords);

        this.yearWords = Utils.createTimeSpanArray(DateTimeSpan.YEAR, DateTimeSpan.YEAR, allWords);

        this.yearsWords = Utils.createTimeSpanArray(DateTimeSpan.YEARS, DateTimeSpan.YEARS, allWords);

        this.monthWords = Utils.createTimeSpanArray(DateTimeSpan.MONTH, DateTimeSpan.MONTH, allWords);

        this.monthsWords = Utils.createTimeSpanArray(DateTimeSpan.MONTHS, DateTimeSpan.MONTHS, allWords);

        this.weekWords = Utils.createTimeSpanArray(DateTimeSpan.WEEK, DateTimeSpan.WEEK, allWords);

        this.weeksWords = Utils.createTimeSpanArray(DateTimeSpan.WEEKS, DateTimeSpan.WEEKS, allWords);

        this.weekdayWords = Utils.createTimeSpanArray(DateTimeSpan.WEEKDAY, DateTimeSpan.WEEKDAY, allWords);

        this.weekdaysWords = Utils.createTimeSpanArray(DateTimeSpan.WEEKDAYS, DateTimeSpan.WEEKDAYS, allWords);

        this.weekendWords = Utils.createTimeSpanArray(DateTimeSpan.WEEKEND, DateTimeSpan.WEEKEND, allWords);

        this.weekendsWords = Utils.createTimeSpanArray(DateTimeSpan.WEEKENDS, DateTimeSpan.WEEKENDS, allWords);

        this.dayWords = Utils.createTimeSpanArray(DateTimeSpan.DAY, DateTimeSpan.DAY, allWords);

        this.daysWords = Utils.createTimeSpanArray(DateTimeSpan.DAYS, DateTimeSpan.DAYS, allWords);

        this.currentDayWords = Utils.createTimeSpanArray(DateTimeSpan.CURRENT_DAY, DateTimeSpan.CURRENT_DAY, allWords);

        this.hourWords = Utils.createTimeSpanArray(DateTimeSpan.HOUR, DateTimeSpan.HOUR, allWords);

        this.hoursWords = Utils.createTimeSpanArray(DateTimeSpan.HOURS, DateTimeSpan.HOURS, allWords);

        this.minuteWords = Utils.createTimeSpanArray(DateTimeSpan.MINUTE, DateTimeSpan.MINUTE, allWords);

        this.minutesWords = Utils.createTimeSpanArray(DateTimeSpan.MINUTES, DateTimeSpan.MINUTES, allWords);

        this.secondWords = Utils.createTimeSpanArray(DateTimeSpan.SECOND, DateTimeSpan.SECONDS, allWords);

        this.secondsWords = Utils.createTimeSpanArray(DateTimeSpan.SECONDS, DateTimeSpan.SECONDS, allWords);

        this.customDate = Utils.createTimeSpanArray(DateTimeSpan.CUSTOM_DATE, DateTimeSpan.CUSTOM_DATE, allWords);

        this.customDates = Utils.createTimeSpanArray(DateTimeSpan.CUSTOM_DATES, DateTimeSpan.CUSTOM_DATES, allWords);

        this.daysOfWeek = Utils.createDaysOfWeek(allWords);

        this.monthsOfYear = Utils.createMonthOfYear(allWords);

        this.partsOfDay = Utils.createPartOfDay(allWords);
    }


    public abstract List<DateTimeProperties> predict(String inputSentence, Date referenceDate, HawkingConfiguration config);

    public abstract String getTense(String inputText);

    public abstract List<Pair<Boolean, List<Triple<String, Integer, Integer>>>> getSeparateDates(List<Triple<String, Integer, Integer>> allDates);
}
