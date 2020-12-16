//$Id$
package com.zoho.hawking.datetimeparser;

import com.zoho.hawking.datetimeparser.constants.*;
import org.apache.commons.lang3.tuple.Pair;

public class WordProperty {

    private String word;
    private int timeSpan;
    private int prefix;
    private int tenseIndicator;
    private int relationType = -1;
    private boolean isAnyRangeNotApplicable;
    private String[] variations;

    private DateTimeSpan dateTimeSpan;
    private WordImplication wordImplication;
    private Tense tense;
    private RelationType relation;
    private DaysOfWeek daysOfWeek;
    private MonthsOfYear monthsOfYear;

    Pair<HoursOfDay, HoursOfDay> pairOfHoursOfDay;

    public WordProperty() {

    }


    public WordProperty(String word, DaysOfWeek daysOfWeek, String[] variations) {
        this.word = word;
        this.daysOfWeek = daysOfWeek;
        this.variations = variations;
    }


    public WordProperty(String word, MonthsOfYear monthsOfYear, String[] variations) {
        this.word = word;
        this.monthsOfYear = monthsOfYear;
        this.variations = variations;
    }

    public WordProperty(String word, Pair<HoursOfDay, HoursOfDay> pairOfHoursOfDay, String[] variations) {
        this.word = word;
        this.pairOfHoursOfDay = pairOfHoursOfDay;
        this.variations = variations;
    }


    public WordProperty(String word, DateTimeSpan dateTimeSpan) {
        this.word = word;
        this.dateTimeSpan = dateTimeSpan;
    }

    /*
     *
     * Actual Word
     * TimeSpan used to identify the span property of the word
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
     *
     * */
    public WordProperty(String word, int timeSpan) {
        this.word = word;
        if (timeSpan >= 0 && timeSpan <= 9) {
            this.timeSpan = timeSpan;
        } else {
            this.relationType = timeSpan - 10;
            this.tenseIndicator = -1;
        }
    }

    public WordProperty(String word, WordImplication wordImplication) {
        this.word = word;
        this.wordImplication = wordImplication;
    }

    public WordProperty(String word, WordImplication wordImplication, boolean isAnyRangeNotApplicable) {
        this.word = word;
        this.wordImplication = wordImplication;
        this.isAnyRangeNotApplicable = isAnyRangeNotApplicable;
    }

    public WordProperty(String word, DateTimeSpan dateTimeSpan, Tense tense) {
        this.word = word;
        this.tense = tense;
        this.dateTimeSpan = dateTimeSpan;
    }

    public WordProperty(String word, Tense tense) {
        this.word = word;
        this.tense = tense;
    }

    public WordProperty(String word, WordImplication wordImplication, Tense tense) {
        this.word = word;
        this.wordImplication = wordImplication;
        this.tense = tense;
    }

    public WordProperty(String word, WordImplication wordImplication, Tense tense, RelationType relationType, DateTimeSpan dateTimeSpan) {
        this.word = word;
        this.wordImplication = wordImplication;
        this.tense = tense;
        this.relation = relationType;
        this.dateTimeSpan = dateTimeSpan;
    }

    public WordProperty(String word, WordImplication wordImplication, Tense tense, RelationType relationType) {
        this.word = word;
        this.wordImplication = wordImplication;
        this.tense = tense;
        this.relation = relationType;
    }

    /*
     * Word
     * 	Actual Word
     * Prefix used define the property of the word
     * 	start - 0
     * 	end - 1
     * 	secondary - 2
     *
     * TenseIndicator used define property of the word
     * 	past - 0
     * 	present - 1
     * 	future - 2
     * 	immediate_future - 3
     * 	Immediate - 4
     * 	Remainder - 5
     * */
    public WordProperty(String word, int prefix, int tenseIndicator) {
        this.word = word;
        this.prefix = prefix;
        this.tenseIndicator = tenseIndicator;
    }

    public WordProperty(String word, int prefix, int tenseIndicator, int relationType, int timeSpan) {
        this.word = word;
        this.prefix = prefix;
        this.tenseIndicator = tenseIndicator;
        this.relationType = relationType;
        if (timeSpan >= 0 && timeSpan <= 9) {
            this.timeSpan = timeSpan;
        } else {
            this.relationType = timeSpan - 10;
        }
    }

    /*
     * 1) range relation (from to)
     * 2) date set (and or aswellas)
     * 3) conditional (30 days from today)*/
    public WordProperty(String word, int prefix, int tenseIndicator, int relationType) {
        this.word = word;
        this.prefix = prefix;
        this.tenseIndicator = tenseIndicator;
        this.relationType = relationType;
    }

    public String getWord() {
        return word;
    }

    public int getPrefix() {
        return prefix;
    }

    public int getTenseIndicator() {
        return tenseIndicator;
    }

    public int getTimeSpan() {
        return timeSpan;
    }

    public int getRelationType() {
        return relationType;
    }

    public DateTimeSpan getDateTimeSpan() {
        return dateTimeSpan;
    }

    public void setDateTimeSpan(DateTimeSpan dateTimeSpan) {
        this.dateTimeSpan = dateTimeSpan;
    }

    public WordImplication getWordImplication() {
        return wordImplication;
    }

    public void setWordImplication(WordImplication wordImplication) {
        this.wordImplication = wordImplication;
    }

    public Tense getTense() {
        return tense;
    }

    public void setTense(Tense tense) {
        this.tense = tense;
    }

    public RelationType getRelation() {
        return relation;
    }

    public void setRelation(RelationType relation) {
        this.relation = relation;
    }

    public boolean isAnyRangeNotApplicable() {
        return isAnyRangeNotApplicable;
    }

    public void setAnyRangeNotApplicable(boolean anyRangeNotApplicable) {
        isAnyRangeNotApplicable = anyRangeNotApplicable;
    }

    public MonthsOfYear getMonthsOfYear() {
        return monthsOfYear;
    }

    public void setMonthsOfYear(MonthsOfYear monthsOfYear) {
        this.monthsOfYear = monthsOfYear;
    }

    public DaysOfWeek getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(DaysOfWeek daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String[] getVariations() {
        return variations;
    }

    public void setVariations(String[] variations) {
        this.variations = variations;
    }

    public Pair<HoursOfDay, HoursOfDay> getPairOfHoursOfDay() {
        return pairOfHoursOfDay;
    }

    public void setPairOfHoursOfDay(Pair<HoursOfDay, HoursOfDay> pairOfHoursOfDay) {
        this.pairOfHoursOfDay = pairOfHoursOfDay;
    }
}
