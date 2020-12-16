//$Id$
package com.zoho.hawking.datetimeparser.configuration;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class RangeDefault {

    private int year;
    private int years;
    private int month;
    private int months;
    private int week;
    private int weeks;
    private int day;
    private int days;
    private int second;
    private int seconds;
    private int hour;
    private int hours;
    private int minute;
    private int minutes;
    private int few;
    private int customDate;
    private int customDates;

    RangeDefault() {
        // TODO Auto-generated constructor stub
    }

    RangeDefault(HawkingConfiguration hawkingConfiguration) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, InvocationTargetException, IntrospectionException {
        // TODO Auto-generated constructor stub
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object val = new PropertyDescriptor(field.getName(), hawkingConfiguration.getClass()).getReadMethod().invoke(hawkingConfiguration.getClass());
            field.set(this.getClass(), val);
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeeks() {
        return weeks;
    }

    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getFew() {
        return few;
    }

    public void setFew(int few) {
        this.few = few;
    }

    /**
     * @return the customDate
     */
    public int getCustomDate() {
        return customDate;
    }

    /**
     * @param customDate the customDate to set
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


}
