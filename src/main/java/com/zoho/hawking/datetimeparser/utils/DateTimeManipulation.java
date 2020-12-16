//$Id$
package com.zoho.hawking.datetimeparser.utils;

import com.zoho.hawking.datetimeparser.DateAndTime;
import com.zoho.hawking.datetimeparser.configuration.Configuration;
import com.zoho.hawking.datetimeparser.constants.ConfigurationConstants;
import com.zoho.hawking.utils.Constants;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.base.BaseLocal;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateTimeManipulation {

    private Configuration configuration;

    public DateTimeManipulation(Configuration configuration) {
        this.configuration = configuration;
    }

    /*Functions to find the start and end of all TimeSpan
     * timeSpan
     * 	1 - Year Span
     * 	2 - Month Span
     * 	3 - Day Span, Week Span, Exact Date
     * 	4 - Hour Span
     * 	5 - Minute Span
     * 	6 - Second Span*/
    private static DateTime getStart(DateTime dateTime, int timeSpan) {
        DateTime localDateTime;
        switch (timeSpan) {
            case 1:
                localDateTime = dateTime.dayOfYear().withMinimumValue().withTimeAtStartOfDay();
                break;
            case 2:
                localDateTime = dateTime.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
                break;
            case 3:
                localDateTime = dateTime.withTimeAtStartOfDay();
                break;
            case 4:
                localDateTime = dateTime.hourOfDay().roundFloorCopy();
                break;
            case 5:
                localDateTime = dateTime.withMillisOfSecond(0).withSecondOfMinute(0);
                break;
            case 6:
                localDateTime = dateTime.withSecondOfMinute(0);
                break;
            default:
                localDateTime = dateTime;
        }
        return localDateTime;
    }

    private static DateTime getEnd(DateTime dateTime, int timeSpan) {
        DateTime localDateTime;
        switch (timeSpan) {
            case 1:
                localDateTime = dateTime.dayOfYear().withMaximumValue().millisOfDay().withMaximumValue();
                break;
            case 2:
                localDateTime = dateTime.dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue();
                break;
            case 3:
                localDateTime = dateTime.millisOfDay().withMaximumValue();
                break;
            case 4:
                localDateTime = dateTime.withMillisOfSecond(999).withSecondOfMinute(59).withMinuteOfHour(59);
                break;
            case 5:
                localDateTime = dateTime.withMillisOfSecond(999).withSecondOfMinute(59);
                break;
            case 6:
                localDateTime = dateTime.withMillisOfSecond(999);
                break;
            default:
                localDateTime = dateTime;
        }
        return localDateTime;
    }

    /*Get start and end of both startOfTimeSpan or referenceTime
     *
     * option - to specify the output
     * 	1 - start of startOfTimeSpan
     * 	2 - end of endSpan
     * */
    public static DateTime getStartOrEndTime(DateTime dateTime, int option, int timeSpan) {
        DateTime localDateTime;
        switch (option) {
            case 1:
                localDateTime = getStart(dateTime, timeSpan);
                break;
            case 2:
                localDateTime = getEnd(dateTime, timeSpan);
                break;
            default:
                localDateTime = dateTime;
        }
        return localDateTime;
    }

    //for part time Span one has to be decreased and for future/present time span one span has to be increased
    private static Pair<Integer, Integer> computeStartAndEnd(int startSpanIncrement, int endSpanIncrement) {
        startSpanIncrement = startSpanIncrement == 0 ? startSpanIncrement : startSpanIncrement < 0 ? startSpanIncrement + 1 : startSpanIncrement - 1;
        endSpanIncrement = endSpanIncrement == 0 ? endSpanIncrement : endSpanIncrement < 0 ? endSpanIncrement + 1 : endSpanIncrement - 1;
        return Pair.of(startSpanIncrement, endSpanIncrement);
    }

    /*Year Manipulations*/
    /*
     * Used to increment or decrement no of years
     *
     * 	dateAndTime - dateAndTime object have date manipulation data
     * 	yearToAdd - no of years to increment or decrement*/
    public static DateTime addYears(DateTime dateAndTime, int yearsToAdd) {
        return dateAndTime.plusYears(yearsToAdd);
    }

    /*Used to set Exact Year like 2018, 1947 */
    public static DateTime setYear(DateTime dateAndTime, int exactYear) {
        return dateAndTime.yearOfEra().setCopy(exactYear);
    }

    /*set the start date as start of year and end date as end of year*/
    public static void setYearStartAndEndTime(DateAndTime dateAndTime, int startIncrement, int endIncrement, int startOption, int endOption) {
        dateAndTime.setStart(getStartOrEndTime(addYears(dateAndTime.getDateAndTime(), startIncrement), startOption, 1));
        dateAndTime.setEnd(getStartOrEndTime(addYears(dateAndTime.getDateAndTime(), endIncrement), endOption, 1));
    }

    public static void setYearSpanStartAndEndTime(DateAndTime dateAndTime, int startYearIncrement, int endYearIncrement, boolean isImmediate) {
        if (ConfigurationConstants.getConfiguration().getSpanDefault().getYearSpan() == 0) {
            Pair<Integer, Integer> computedStartAndEnd = isImmediate ? Pair.of(startYearIncrement, endYearIncrement) : computeStartAndEnd(startYearIncrement, endYearIncrement);
            setYearStartAndEndTime(dateAndTime, computedStartAndEnd.getLeft(), computedStartAndEnd.getRight(), 1, 2);
        } else {
            setYearStartAndEndTime(dateAndTime, startYearIncrement, endYearIncrement, 0, 0);
        }
    }

    /*Month Manipulations*/
    /*
     * Used to increment or decrement no of months
     *
     * 	dateAndTime - dateAndTime object have date manipulation data
     * 	yearToAdd - no of years to increment or decrement
     * 	monthsToAdd - no of months to increment or decrement*/
    public static DateTime addMonths(DateTime dateAndTime, int yearsToAdd, int monthsToAdd) {
        return dateAndTime.plusYears(yearsToAdd).plusMonths(monthsToAdd);
    }

    /*Used to set Exact Month like Jan, Feb */
    public static DateTime setMonth(DateTime dateTime, int yearsToAdd, int monthOfYear) {
        return dateTime.plusYears(yearsToAdd).monthOfYear().setCopy(monthOfYear);
    }

    public static DateTime recentPastMonth(DateTime dateTime, int monthOfYear) {
        return dateTime.minusMonths((dateTime.getMonthOfYear() == monthOfYear) ? (12) : (((dateTime.getMonthOfYear() - monthOfYear) + 12) % 12));
    }

    public static DateTime recentFutureMonth(DateTime dateTime, int monthOfYear) {
        return dateTime.plusMonths((dateTime.getMonthOfYear() == monthOfYear) ? (0) : (((monthOfYear - dateTime.getMonthOfYear()) + 12) % 12));
    }

    /*set the start date as start of month and end date as end of month*/
    public static void setMonthStartAndEndTime(DateAndTime dateAndTime, int startIncrement, int endIncrement, int startOption, int endOption) {
        dateAndTime.setStart(getStartOrEndTime(addMonths(dateAndTime.getDateAndTime(), 0, startIncrement), startOption, 2));
        dateAndTime.setEnd(getStartOrEndTime(addMonths(dateAndTime.getDateAndTime(), 0, endIncrement), endOption, 2));
    }

    public static void setMonthStartAndEndTime(DateAndTime dateAndTime, DateTime startMonth, DateTime endMonth) {
        dateAndTime.setStart(getStartOrEndTime(startMonth, 0, 2));
        dateAndTime.setEnd(getStartOrEndTime(endMonth, 2, 2));
    }

    public static void setMonthSpanStartAndEndTime(DateAndTime dateAndTime, int startMonthIncrement, int endMonthIncrement, boolean isImmediate) {
        if (ConfigurationConstants.getConfiguration().getSpanDefault().getMonthSpan() == 0) {
            Pair<Integer, Integer> computedStartAndEnd = isImmediate ? Pair.of(startMonthIncrement, endMonthIncrement) : computeStartAndEnd(startMonthIncrement, endMonthIncrement);
            setMonthStartAndEndTime(dateAndTime, computedStartAndEnd.getLeft(), computedStartAndEnd.getRight(), 1, 2);
        } else {
            setMonthStartAndEndTime(dateAndTime, startMonthIncrement, endMonthIncrement, 0, 0);
        }
    }

    /*
     * Used find all dayOfWeek between two dates
     * 	startDay - Start Date of the range
     * 	endDay - End Date of the range
     * 	dayOfWeek - day of week for which the occurrence to be calculated
     *
     * returns List<DateiTime> of the occurrences*/
    public static List<DateTime> noOfDayOfWeekBetween(DateTime startDay, DateTime endDay, int dayOfWeek) {
        DateTime startOftheWeek = startDay.withDayOfWeek(dayOfWeek);
        List<DateTime> daysBetween = new ArrayList<>();
        startDay = startDay.isAfter(startOftheWeek) ? (startOftheWeek.plusWeeks(1)) : startOftheWeek;
        while (startDay.isBefore(endDay)) {
            daysBetween.add(startDay);
            startDay = startDay.plusWeeks(1);
        }

        return daysBetween;
    }

    /*Week Manipulations*/
    /*Used to increment or decrement no of weeks */
    public static DateTime addWeeks(DateTime dateTime, int weeksToAdd, int daysToAdd) {
        return dateTime.plusWeeks(weeksToAdd).plusDays(daysToAdd);
    }

    public static DateTime setWeek(DateTime dateTime, int dayOfWeek, int nthWeek) {
        List<DateTime> dateOfWeekDay = noOfDayOfWeekBetween(dateTime.monthOfYear().withMinimumValue(), dateTime.monthOfYear().withMaximumValue(), dayOfWeek);
        return dateOfWeekDay.get(nthWeek);
    }

    public static DateTime nthWeekOfMonth(DateTime dateTime, int nthWeekOfMonth, int dayOfWeek) {
        DateTime start = dateTime.dayOfMonth().withMinimumValue();
        DateTime date = start.withDayOfWeek(dayOfWeek);
        return (date.isBefore(start)) ? date.plusWeeks(nthWeekOfMonth) : date.plusWeeks(nthWeekOfMonth - 1);
    }

    public static DateTime nthDayofWeek(DateTime dateTime, int nthDayofWeek, int dayOfWeek) {
        DateTime start = dateTime.dayOfWeek().withMinimumValue();
        DateTime date = start.withDayOfWeek(dayOfWeek);
        return (date.isBefore(start)) ? date.plusDays(nthDayofWeek) : date.plusDays(nthDayofWeek - 1);
    }

    public static DateTime nthWeekOfYear(DateTime dateTime, int nthWeekOfYear, int dayOfWeek) {
        DateTime start = dateTime.dayOfYear().withMinimumValue();
        DateTime date = start.withDayOfWeek(dayOfWeek);
        return (date.isBefore(start)) ? date.plusWeeks(nthWeekOfYear) : date.plusWeeks(nthWeekOfYear - 1);
    }

    /*set the start date as start of month and end date as end of month*/
    public static void setWeekStartAndEndTime(DateAndTime dateAndTime, int startWeekIncrement,
                                              int startDayIncrement, int endWeekIncrement, int endDayIncrement,
                                              int startOption, int endOption) {
        dateAndTime.setStart(getStartOrEndTime(addWeeks(dateAndTime.getDateAndTime(), startWeekIncrement, startDayIncrement), startOption, 3));
        dateAndTime.setEnd(getStartOrEndTime(addWeeks(dateAndTime.getDateAndTime(), endWeekIncrement, endDayIncrement), endOption, 3));
    }

    public static void setWeekSpanStartAndEndTime(DateAndTime dateAndTime, int startWeekIncrement,
                                                  int startDayIncrement, int endWeekIncrement,
                                                  int endDayIncrement, boolean isImmediate) {
        if (ConfigurationConstants.getConfiguration().getSpanDefault().getWeekSpan() == 0) {
            Pair<Integer, Integer> computedStartAndEnd = isImmediate ? Pair.of(startWeekIncrement, endWeekIncrement) : computeStartAndEnd(startWeekIncrement, endWeekIncrement);
            setWeekStartAndEndTime(dateAndTime, computedStartAndEnd.getLeft(), startDayIncrement, computedStartAndEnd.getRight(), endDayIncrement, 1, 2);
        } else {
            setWeekStartAndEndTime(dateAndTime, startWeekIncrement, startDayIncrement, endWeekIncrement, endDayIncrement, 0, 0);
        }
    }

    //	public static void setWeekStartAndEndTime(DateAndTime dateAndTime, DateTime startDate, DateTime endDate, int startOption, int endOption) {
    public static void setWeekStartAndEndTime(DateAndTime dateAndTime, DateTime startDate, DateTime endDate, int startOption) {
        dateAndTime.setStart(getStartOrEndTime(startDate, startOption, 3));
        dateAndTime.setEnd(getStartOrEndTime(endDate, startOption, 3));
    }

    /*Day Manipulations*/

    public static DateTime addDays(DateTime dateTime, int weeksToAdd, int daysToAdd) {
        return dateTime.plusWeeks(weeksToAdd).plusDays(daysToAdd);
    }

    public static DateTime setDayOfWeek(DateTime dateTime, int weeksToAdd, int dayOfWeek) {
        return dateTime.plusWeeks(weeksToAdd).dayOfWeek().setCopy(dayOfWeek);
    }

    public static DateTime nthDayOfWeek(DateTime dateTime, int dayOfWeek) {
        return dateTime.plusDays(dayOfWeek - 1);
    }

    public static DateTime nthDayOfMonth(DateTime dateTime, int dayOfWeek) {
        return dateTime.dayOfMonth().setCopy(dayOfWeek);
    }

    public static DateTime nthDayOfYear(DateTime dateTime, int dayOfWeek) {
        return dateTime.dayOfYear().setCopy(dayOfWeek);
    }

    public static DateTime recentPastDay(DateTime dateTime, int dayOfWeek) {
        DateTime localDateTime = dateTime.minusDays(((dateTime.getDayOfWeek() - dayOfWeek) + 7) % 7);
        return localDateTime.toLocalDate().equals(dateTime.toLocalDate()) ? localDateTime.plusDays(-7) : localDateTime;
    }

    public static DateTime recentFutureDay(DateTime dateTime, int dayOfWeek) {
        DateTime localDateTime = dateTime.plusDays(((dayOfWeek - dateTime.getDayOfWeek()) + 7) % 7);
        return localDateTime.toLocalDate().equals(dateTime.toLocalDate()) ? localDateTime.plusDays(7) : localDateTime;
    }

    public static void setDayStartAndEndTime(DateAndTime dateAndTime, int startDayIncrement, int endDayIncrement, int startOption, int endOption) {
        dateAndTime.setStart(getStartOrEndTime(addDays(dateAndTime.getDateAndTime(), 0, startDayIncrement), startOption, 3));
        dateAndTime.setEnd(getStartOrEndTime(addDays(dateAndTime.getDateAndTime(), 0, endDayIncrement), endOption, 3));
    }

    public static void setDaySpanStartAndEndTime(DateAndTime dateAndTime, int startDayIncrement, int endDayIncrement, boolean isImmediate) {
        if (ConfigurationConstants.getConfiguration().getSpanDefault().getDaySpan() == 0) {
            Pair<Integer, Integer> computedStartAndEnd = isImmediate ? Pair.of(startDayIncrement, endDayIncrement) : computeStartAndEnd(startDayIncrement, endDayIncrement);
            setDayStartAndEndTime(dateAndTime, computedStartAndEnd.getLeft(), computedStartAndEnd.getRight(), 1, 2);
        } else {
            setDayStartAndEndTime(dateAndTime, startDayIncrement, endDayIncrement, 0, 0);
        }
    }

    /*Hour Manipulation*/

    public static DateTime addHours(DateTime dateTime, int hoursToAdd) {
        return dateTime.plusHours(hoursToAdd);
    }

    public static DateTime nthHourDay(DateTime dateTime, int hoursAdd, int nthHourOfDay) {
        return dateTime.plusHours(hoursAdd).hourOfDay().setCopy(nthHourOfDay);
    }

    public static DateTime recentPastHour(DateTime dateTime, int hourOfDay) {
        return dateTime.minusHours(((dateTime.getHourOfDay() - hourOfDay) + 24) % 24);
    }

    public static DateTime recentFutureHour(DateTime dateTime, int hourOfDay) {
        return dateTime.plusHours(((hourOfDay - dateTime.getHourOfDay()) + 24) % 24);
    }

    public static DateTime exactPastTime(DateTime dateTime, DateTime referenceTime) {
        return (dateTime.getMillis() < referenceTime.getMillis()) ? dateTime : dateTime.minusDays(1);
    }

    public static DateTime exactFutureTime(DateTime dateTime, DateTime referenceTime) {
        return (dateTime.getMillis() > referenceTime.getMillis()) ? dateTime : dateTime.plusDays(1);
    }

    public static void setHourStartAndEndTime(DateAndTime dateAndTime, int startHourIncrement, int endHourIncrement, int startOption, int endOption) {
        dateAndTime.setStart(getStartOrEndTime(addHours(dateAndTime.getDateAndTime(), startHourIncrement), startOption, 4));
        dateAndTime.setEnd(getStartOrEndTime(addHours(dateAndTime.getDateAndTime(), endHourIncrement), endOption, 4));
    }

    public static void setHourStartAndEndTime(DateAndTime dateAndTime, DateTime startTime, DateTime endTime) {
        dateAndTime.setStart(getStartOrEndTime(startTime, 1, 4));
        dateAndTime.setEnd(getStartOrEndTime(endTime, 2, 4));
    }

    public static void setHourSpanStartAndEndTime(DateAndTime dateAndTime, int startHourIncrement, int endHourIncrement, boolean isImmediate) {
        if (ConfigurationConstants.getConfiguration().getSpanDefault().getHourSpan() == 0) {
            Pair<Integer, Integer> computedStartAndEnd = isImmediate ? Pair.of(startHourIncrement, endHourIncrement) : computeStartAndEnd(startHourIncrement, endHourIncrement);
            setHourStartAndEndTime(dateAndTime, computedStartAndEnd.getLeft(), computedStartAndEnd.getRight(), 1, 2);
        } else {
            setHourStartAndEndTime(dateAndTime, startHourIncrement, endHourIncrement, 0, 0);
        }
    }

    /*Minute Manipulation*/
    public static DateTime addMinutes(DateTime dateTime, int minutesToAdd) {
        return dateTime.plusMinutes(minutesToAdd);
    }

    public static DateTime setMinute(DateTime dateTime, int nthMinuteOfHour) {
        return dateTime.minuteOfHour().setCopy(nthMinuteOfHour);
    }

    public static void setMinuteStartAndEndTime(DateAndTime dateAndTime, int startMinuteIncrement, int endMinuteIncrement, int startOption, int endOption) {
        dateAndTime.setStart(getStartOrEndTime(addMinutes(dateAndTime.getDateAndTime(), startMinuteIncrement), startOption, 5));
        dateAndTime.setEnd(getStartOrEndTime(addMinutes(dateAndTime.getDateAndTime(), endMinuteIncrement), endOption, 5));
    }

    public static void setMinuteSpanStartAndEnd(DateAndTime dateAndTime, int startMinuteIncrement, int endMinuteIncrement, boolean isImmediate) {
        if (ConfigurationConstants.getConfiguration().getSpanDefault().getMinuteSpan() == 0) {
            Pair<Integer, Integer> computedStartAndEnd = isImmediate ? Pair.of(startMinuteIncrement, endMinuteIncrement) : computeStartAndEnd(startMinuteIncrement, endMinuteIncrement);
            setMinuteStartAndEndTime(dateAndTime, computedStartAndEnd.getLeft(), computedStartAndEnd.getRight(), 1, 2);
        } else {
            setMinuteStartAndEndTime(dateAndTime, startMinuteIncrement, endMinuteIncrement, 0, 0);
        }
    }

    /*Second Manipulation*/

    public static DateTime addSeconds(DateTime dateTime, int secondsToAdd) {
        return dateTime.plusSeconds(secondsToAdd);
    }

    public static DateTime setSecond(DateTime dateTime, int nthSecondOfHour) {
        return dateTime.secondOfMinute().setCopy(nthSecondOfHour);
    }

    public static void setSecondStartAndEndTime(DateAndTime dateAndTime, int startSecondIncrement, int endSecondIncrement, int startOption, int endOption) {
        dateAndTime.setStart(getStartOrEndTime(addSeconds(dateAndTime.getDateAndTime(), startSecondIncrement), startOption, 6));
        dateAndTime.setEnd(getStartOrEndTime(addSeconds(dateAndTime.getDateAndTime(), endSecondIncrement), endOption, 6));
    }

    public static void setSecondSpanStartAndEnd(DateAndTime dateAndTime, int startSecondIncrement, int endSecondIncrement, boolean isImmediate) {
        if (ConfigurationConstants.getConfiguration().getSpanDefault().getSecondSpan() == 0) {
            Pair<Integer, Integer> computedStartAndEnd = isImmediate ? Pair.of(startSecondIncrement, endSecondIncrement) : computeStartAndEnd(startSecondIncrement, endSecondIncrement);
            setSecondStartAndEndTime(dateAndTime, computedStartAndEnd.getLeft(), computedStartAndEnd.getRight(), 1, 2);
        } else {
            setSecondStartAndEndTime(dateAndTime, startSecondIncrement, endSecondIncrement, 0, 0);
        }
    }

    /*Exact Date and Time Manipulation*/

    public static BaseLocal findExactSpan(String sentenceToParse, String normalizationRegex, String[] spanFormats, String span) {
        BaseLocal baseLocal = null;

        sentenceToParse = sentenceToParse.replace("a.m.", "am") //NO I18n
                .replace("a.m", "am") //NO I18n
                .replace(".a.m", "am") //NO I18n
                .replace("p.m.", "pm") //NO I18n
                .replace("p.m", "pm") //NO I18n
                .replace(".p.m", "pm"); //NO I18n
        sentenceToParse = sentenceToParse.replace(":am", "am"); //NO I18n
        sentenceToParse = sentenceToParse.replace(":pm", "pm"); //NO I18n
        sentenceToParse = sentenceToParse.replace("to", ""); //NO I18n
        sentenceToParse = sentenceToParse.replace("by", ""); //NO I18n
        String normalizedSentence = sentenceToParse.replaceAll(normalizationRegex, ""); //NO I18n
        if (normalizedSentence.equals("")) {
            return null;
        }
        for (String dateFormat : spanFormats) {
            try {
                DateTimeFormatter dtf = DateTimeFormat.forPattern(dateFormat);
                DateTime date = dtf.parseDateTime(normalizedSentence);
                Date localSpan = date.toDate();
                baseLocal = (span.equals(Constants.EXACT_DATE_TAG)) ? new LocalDate(localSpan) : new LocalTime(localSpan);
                break;
            } catch(Exception e) {
            }
        }
        return baseLocal;
    }
}
