//$Id$
package com.zoho.hawking.datetimeparser;

import com.zoho.hawking.datetimeparser.components.*;
import com.zoho.hawking.language.AbstractLanguage;
import org.joda.time.DateTime;

import java.util.Map;

public class DateTimeParser {
    public static DateAndTime timeParser(DateTime referenceDateTime, String tense, Map<String, String> componentsMap, AbstractLanguage abstractLanguage){
        DateAndTime dateAndTime = new DateAndTime(referenceDateTime);
        String[] spanHierarchy = {
                "year", "custom_date", "month", "week", "day", //No I18N
                "hour", "minute", "second", "date", "time"}; //No I18N

        for (String timeSpan : spanHierarchy) {
            if (componentsMap.get(timeSpan) != null) {
                DateTimeComponent localDateTimeComponent = getInstance(timeSpan, componentsMap.get(timeSpan), tense, dateAndTime, abstractLanguage);
                parseDateTimeComponent(localDateTimeComponent, abstractLanguage);
            }
        }
        return dateAndTime;
    }

    private static DateTimeComponent getInstance(String span, String sentenceToParse, String sentenceTense, DateAndTime dateAndTime, AbstractLanguage abstractLanguage) {
        DateTimeComponent localDateTimeComponent = null;
        switch (span) {
            case "year":
                localDateTimeComponent = new YearParser(sentenceToParse, sentenceTense, dateAndTime, abstractLanguage);
                break;

            case "month":
                localDateTimeComponent = new MonthParser(sentenceToParse, sentenceTense, dateAndTime, abstractLanguage);
                break;

            case "week":
                localDateTimeComponent = new WeekParser(sentenceToParse, sentenceTense, dateAndTime, abstractLanguage);
                break;

            case "day":
                localDateTimeComponent = new DayParser(sentenceToParse, sentenceTense, dateAndTime, abstractLanguage);
                break;

            case "hour":
                localDateTimeComponent = new HourParser(sentenceToParse, sentenceTense, dateAndTime, abstractLanguage);
                break;

            case "minute":
                localDateTimeComponent = new MinuteParser(sentenceToParse, sentenceTense, dateAndTime, abstractLanguage);
                break;

            case "second":
                localDateTimeComponent = new SecondParser(sentenceToParse, sentenceTense, dateAndTime, abstractLanguage);
                break;

            case "date":
                localDateTimeComponent = new ExactDateParser(sentenceToParse, sentenceTense, dateAndTime, abstractLanguage);
                break;

            case "time":
                localDateTimeComponent = new ExactTimeParser(sentenceToParse, sentenceTense, dateAndTime, abstractLanguage);
                break;

            case "custom_date":
                localDateTimeComponent = new CustomDateParser(sentenceToParse, sentenceTense, dateAndTime, abstractLanguage);
                break;
        }

        return localDateTimeComponent;
    }

    public static void parseDateTimeComponent(DateTimeComponent dateTimeComponent, AbstractLanguage abstractLanguage) {

        if (dateTimeComponent.timeSpan.equals("")) {
            return;
        } else if (dateTimeComponent.isOrdinal && !dateTimeComponent.isExactTimeSpan) {
            dateTimeComponent.nthSpan();
        } else if (!dateTimeComponent.tenseIndicator.equals("")) {
            if (abstractLanguage.pastWords.contains(dateTimeComponent.tenseIndicator)) {
                dateTimeComponent.past();
            } else if (abstractLanguage.presentWords.contains(dateTimeComponent.tenseIndicator)) {
                dateTimeComponent.present();
            } else if (abstractLanguage.futureWords.contains(dateTimeComponent.tenseIndicator)) {
                dateTimeComponent.future();
            } else if (abstractLanguage.immediatePast.contains(dateTimeComponent.tenseIndicator)) {
                dateTimeComponent.immediatePast();
            } else if (abstractLanguage.immediateFuture.contains(dateTimeComponent.tenseIndicator)) {
                dateTimeComponent.immediateFuture();
            } else if (abstractLanguage.remainder.contains(dateTimeComponent.tenseIndicator)) {
                dateTimeComponent.remainder();
            } else if (abstractLanguage.immediate.contains(dateTimeComponent.tenseIndicator)) {
                dateTimeComponent.immediate();
            }
        } else {
            if (dateTimeComponent.sentenceTense.equals("")) {
                dateTimeComponent.sentenceTense = "PRESENT"; //No I18N
            }

            switch (dateTimeComponent.sentenceTense) {
                case "PAST":
                    dateTimeComponent.past();
                    break;
                case "FUTURE":
                    dateTimeComponent.future();
                    break;
                default:
                    dateTimeComponent.present();
                    break;
            }
        }

        dateTimeComponent.setPreviousDependency();
    }
}
