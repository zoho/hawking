//$Id$
package com.zoho.hawking.datetimeparser.configuration;

import org.joda.time.DateTimeConstants;

public class HawkingConfiguration {
    private int year = 1;
    private int years = 2;
    private int month = 1;
    private int months = 2;
    private int week = 1;
    private int weeks = 2;
    private int day = 1;
    private int days = 2;
    private int second = 1;
    private int seconds = 2;
    private int hour = 1;
    private int hours = 2;
    private int minute = 1;
    private int minutes = 2;
    private int few = 2;
    private int customDate = 1;
    private int customDates = 2;

    private int yearSpan = 0;
    private int monthSpan = 0;
    private int weekSpan = 0;
    private int daySpan = 0;
    private int hourSpan = 0;
    private int minuteSpan = 0;
    private int secondSpan = 1;

    private int dayhourStart = 0;
    private int dayhourEnd = 24;

    private int weekDayStart = DateTimeConstants.MONDAY;
    private int weekDayEnd = DateTimeConstants.FRIDAY;

    private int weekEndStart = DateTimeConstants.SATURDAY;
    private int weekEndEnd = DateTimeConstants.SUNDAY;

    private int weekStart = DateTimeConstants.SUNDAY;
    private int weekEnd = DateTimeConstants.SATURDAY;

    private int fiscalYearStart = 4 ;
    private int fiscalYearEnd = 3;

    private int weekRange = 0;
    private String dateFormat = "";
    private String timeZone = "";

    /**
     * @return the year range
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year represent how many years the year mean.recommended 1
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the years range
     */
    public int getYears() {
        return years;
    }

    /**
     * @param years represent how many years the year mean.recommended 2 or greater than 1
     */
    public void setYears(int years) {
        this.years = years;
    }

    /**
     * @return the month range
     */
    public int getMonth() {
        return month;
    }

    /**
     * @param month represent how many months the month mean.recommended 1
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * @return the months range
     */
    public int getMonths() {
        return months;
    }

    /**
     * @param months represent how many months the months mean.recommended 2 or greater than 1
     */
    public void setMonths(int months) {
        this.months = months;
    }

    /**
     * @return the week range
     */
    public int getWeek() {
        return week;
    }

    /**
     * @param week represent how many weeks the week mean.recommended 1
     */
    public void setWeek(int week) {
        this.week = week;
    }

    /**
     * @return the weeks range
     */
    public int getWeeks() {
        return weeks;
    }

    /**
     * @param weeks represent how many weeks the weeks mean.recommended 2 or greater than 1
     */
    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }

    /**
     * @return the day range
     */
    public int getDay() {
        return day;
    }

    /**
     * @param day represent how many days the day mean.recommended 1
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * @return the days range
     */
    public int getDays() {
        return days;
    }

    /**
     * @param days  represent how many days the days mean.recommended 2 or greater than 1
     */
    public void setDays(int days) {
        this.days = days;
    }

    /**
     * @return the second range
     */
    public int getSecond() {
        return second;
    }

    /**
     * @param second represent how many seconds the second mean.recommended 1
     */
    public void setSecond(int second) {
        this.second = second;
    }

    /**
     * @return the seconds range
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * @param seconds represent how many seconds the seconds mean.recommended 2 or greater than 1
     */
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    /**
     * @return the hour range
     */
    public int getHour() {
        return hour;
    }

    /**
     * @param hour represent how many hours the hour mean.recommended 1
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * @return the hours range
     */
    public int getHours() {
        return hours;
    }

    /**
     * @param hours represent how many hours the hours mean.recommended 2 or greater than 1
     */
    public void setHours(int hours) {
        this.hours = hours;
    }

    /**
     * @return the minute range
     */
    public int getMinute() {
        return minute;
    }

    /**
     * @param minute represent how many minutes the minute mean.recommended 1
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * @return the minutes range
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * @param minutes represent how many minutes the minutes mean.recommended 2 or greater than 1
     */
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     * @return the customDate
     */
    public int getCustomDate() {
        return customDate;
    }

    /**
     * @param customDate customDate the customDate to set
     */
    public void setCustomDate(int customDate) {
        this.customDate = customDate;
    }

    /**
     * @return the customDates
     */
    public int getCustomDates() {
        return customDates;
    }

    /**
     * @param customDates the customDates to set
     */
    public void setCustomDates(int customDates) {
        this.customDates = customDates;
    }

    /**
     * @return the few
     */
    public int getFew() {
        return few;
    }

    /**
     * @param few represent how many span the few mean.recommended 4 or greater than 4
     */
    public void setFew(int few) {
        this.few = few;
    }

    /**
     * @return the yearSpan
     */
    public int getYearSpan() {
        return yearSpan;
    }

    /**
     * @param yearSpan set 0 for result to start from next year or 1 to include the current year
     *            example statement next 2 years
     *            current year 2018
     *            0 - (2019 - 2020)
     *            1 - (2018 - 2019)
     */
    public void setYearSpan(int yearSpan) {
        this.yearSpan = yearSpan;
    }

    /**
     * @return the monthSpan
     */
    public int getMonthSpan() {
        return monthSpan;
    }

    /**
     * @param monthSpan set 0 for result to start from next month or 1 to include the current month
     *            example statement next 2 months
     *            current month July
     *            0 - (August - September)
     *            1 - (July - August)
     */
    public void setMonthSpan(int monthSpan) {
        this.monthSpan = monthSpan;
    }

    /**
     * @return the weekSpan
     */
    public int getWeekSpan() {
        return weekSpan;
    }

    /**
     * @param weekSpan set 0 for result to start from next week or 1 to include the current week
     *            example statement next 2 weeks
     *            current week July 01, 2018 - July 07, 2018
     *            0 - (July 08, 2018 - July 21, 2018)
     *            1 - (July 01, 2018 - July 14, 2018)
     */
    public void setWeekSpan(int weekSpan) {
        this.weekSpan = weekSpan;
    }

    /**
     * @return the daySpan
     */
    public int getDaySpan() {
        return daySpan;
    }

    /**
     * @param daySpan set 0 for result to start from next day or 1 to include the current day
     *            example statement next 2 days
     *            current day Thu
     *            0 - (Fri - Sat)
     *            1 - (Thu - Fri)
     */
    public void setDaySpan(int daySpan) {
        this.daySpan = daySpan;
    }

    /**
     * @return the hourSpan
     */
    public int getHourSpan() {
        return hourSpan;
    }

    /**
     * @param hourSpan set 0 for result to start from next hour or 1 to include the current hour
     *            example statement next 2 hours
     *            current hour 17:00
     *            0 - (18:00 - 19:00)
     *            1 - (17:00 - 18:00)
     */
    public void setHourSpan(int hourSpan) {
        this.hourSpan = hourSpan;
    }

    /**
     * @return the minuteSpan
     */
    public int getMinuteSpan() {
        return minuteSpan;
    }

    /**
     * @param minuteSpan set 0 for result to start from next minute or 1 to include the current minute
     *            example statement next 30 minutes
     *            current hour 17:30
     *            0 - (17:31 - 18:00)
     *            1 - (17:30 - 17:59)
     */
    public void setMinuteSpan(int minuteSpan) {
        this.minuteSpan = minuteSpan;
    }

    /**
     * @return the secondSpan
     */
    public int getSecondSpan() {
        return secondSpan;
    }

    /**
     * @param secondSpan set 0 for result to start from next second or 1 to include the current second
     *            example statement next 30 seconds
     *            current hour 17:30:00
     *            0 - (17:30:01 - 17:30:30)
     *            1 - (17:30:00 - 17:30:29)
     */
    public void setSecondSpan(int secondSpan) {
        this.secondSpan = secondSpan;
    }

    /**
     * @return the weekDayStart
     */
    public int getWeekDayStart() {
        return weekDayStart;
    }

    /**
     * @param weekDayStart can be from 1 - 7 where Monday is 1
     * @throws Exception
     */
    public void setWeekDayStart(int weekDayStart) throws Exception {
        if (weekDayStart <= 7 && weekDayStart >= 1) {
            this.weekDayStart = weekDayStart;
        } else {
            throw new Exception("WeekdayStart start should be between 1 and 7");
        }
    }

    /**
     * @return the weekDayEnd
     */
    public int getWeekDayEnd() {
        return weekDayEnd;
    }

    /**
     * @param weekDayEnd can be from 1 - 7 where Monday is 1
     * @throws Exception
     */
    public void setWeekDayEnd(int weekDayEnd) throws Exception {
        if (weekDayEnd <= 7 && weekDayEnd >= 1) {
            this.weekDayEnd = weekDayEnd;
        } else {
            throw new Exception("WeekdayEnd start should be between 1 and 7");
        }
    }

    /**
     * @return the weekEndStart
     */
    public int getWeekEndStart() {
        return weekEndStart;
    }

    /**
     * @param weekEndStart can be from 1 - 7 where Monday is 1
     * @throws Exception
     */
    public void setWeekEndStart(int weekEndStart) throws Exception {
        if (weekEndStart <= 7 && weekEndStart >= 1) {
            this.weekEndStart = weekEndStart;
        } else {
            throw new Exception("WeekEndStart start should be between 1 and 7");
        }
    }

    /**
     * @return the weekEndEnd
     */
    public int getWeekEndEnd() {
        return weekEndEnd;
    }

    /**
     * @param weekEndEnd can be from 1 - 7 where Monday is 1
     * @throws Exception
     */
    public void setWeekEndEnd(int weekEndEnd) throws Exception {
        if (weekEndEnd <= 7 && weekEndEnd >= 1) {
            this.weekEndEnd = weekEndEnd;
        } else {
            throw new Exception("WeekEndEnd start should be between 1 and 7");
        }
    }

    /**
     * @return the weekStart
     */
    public int getWeekStart() {
        return weekStart;
    }

    /**
     * @param weekStart can be from 1 - 7 where Monday is 1
     * @throws Exception
     */
    public void setWeekStart(int weekStart) throws Exception {
        if (weekStart <= 7 && weekStart >= 1) {
            this.weekStart = weekStart;
        } else {
            throw new Exception("WeekStart start should be between 1 and 7");
        }
    }

    /**
     * @return the weekEnd
     */
    public int getWeekEnd() {
        return weekEnd;
    }

    /**
     * @param weekEnd can be from 1 - 7 where Monday is 1
     * @throws Exception
     */
    public void setWeekEnd(int weekEnd) throws Exception {
        if (weekEnd <= 7 && weekEnd >= 1) {
            this.weekEnd = weekEnd;
        } else {
            throw new Exception("WeekEnd start should be between 1 and 7");
        }
    }

    /**
     * @return the fiscalYearStart
     */
    public int getFiscalYearStart() {
        return fiscalYearStart;
    }

    /**
     * @param fiscalYearStart can be from 1 - 12 where January is 1
     * @throws Exception
     */
    public void setFiscalYearStart(int fiscalYearStart) throws Exception {
        if (fiscalYearStart <= 12 && fiscalYearStart >= 1) {
            this.fiscalYearStart = fiscalYearStart;
        } else {
            throw new Exception("FiscalYearStart start should be between 1 and 12");
        }
    }

    /**
     * @return the fiscalYearEnd
     */
    public int getFiscalYearEnd() {
        return fiscalYearEnd;
    }

    /**
     * @param fiscalYearEnd can be from 1 - 12 where January is 1
     * @throws Exception
     */
    public void setFiscalYearEnd(int fiscalYearEnd) throws Exception {
        if (fiscalYearEnd <= 12 && fiscalYearEnd >= 1) {
            this.fiscalYearEnd = fiscalYearEnd;
        } else {
            throw new Exception("FiscalYearEnd start should be between 1 and 12");
        }
    }

    /**
     * @return the weekRange
     */
    public int getWeekRange() {
        return weekRange;
    }

    /**
     * @param weekRange represent whether to return full weekspan where includes current month and next month
     *                  example current month July
     *                  query last week
     *                  weekStart - Sunday
     *                  weekEnd - Saturday
     *                  0 - (July 29, 2018 - July 31, 2018)
     *                  1 - (July 29, 2018 - August 04, 2018)
     */
    public void setWeekRange(int weekRange) {
        this.weekRange = weekRange;
    }

    /**
     * @return the dateFormat
     */
    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * @param dateFormat either dd/mm/yyyy or yyyy/mm/dd
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * @return the timeZone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * @param timeZone represent whether to set timezone to referenceDate
     *                 IST, PST, CST
     */

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * @return the dayhourStart
     * */

    public int getDayhourStart() { return dayhourStart; }

    /**
     * @param dayhourStart represent start of the day hour
     * range 0-23 , dayhourStart should always less than dayhourEnd
     * 0 - 12 AM, 1 - 1 AM, 14 - 2 PM
     */

    public void setDayhourStart(int dayhourStart) { this.dayhourStart = dayhourStart; }

    /**
     * @return the dayhourEnd
     */

    public int getDayhourEnd() { return dayhourEnd; }

    /**
     * @param dayhourEnd represent end of the day hour
     * range 1-24 , dayhourEnd should always greater than dayhourStart
     * 17 - 5 PM, 20 - 8 PM, 22 - 10 PM
     */

    public void setDayhourEnd(int dayhourEnd) { this.dayhourEnd = dayhourEnd; }

}
