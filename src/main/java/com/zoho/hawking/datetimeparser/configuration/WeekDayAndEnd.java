//$Id$
package com.zoho.hawking.datetimeparser.configuration;

public class WeekDayAndEnd {
    private int weekDayStart;
    private int weekDayEnd;

    private int weekEndStart;
    private int weekEndEnd;

    private int weekStart;
    private int weekEnd;
    //decides whether half completed week to be displayed or the previous full week
    private int weekRange;

    WeekDayAndEnd() {

    }

    public int getWeekDayStart() {
        return weekDayStart;
    }

    public void setWeekDayStart(int weekDayStart) {
        this.weekDayStart = weekDayStart;
    }

    public int getWeekDayEnd() {
        return weekDayEnd;
    }

    public void setWeekDayEnd(int weekDayEnd) {
        this.weekDayEnd = weekDayEnd;
    }

    public int getWeekEndStart() {
        return weekEndStart;
    }

    public void setWeekEndStart(int weekEndStart) {
        this.weekEndStart = weekEndStart;
    }

    public int getWeekEndEnd() {
        return weekEndEnd;
    }

    public void setWeekEndEnd(int weekEndEnd) {
        this.weekEndEnd = weekEndEnd;
    }

    public int getWeekDayDiff() {
        return Math.abs((weekDayEnd - weekDayStart) + 7) % 7;
    }

    public int getWeekEndDiff() {
        return Math.abs((weekEndEnd - weekEndStart) + 7) % 7;
    }

    public int getWeekStart() {
        return weekStart;
    }

    public int getWeekEnd() {
        return weekEnd;
    }

    public int getWeekDiff() {
        return Math.abs((weekEnd - weekStart) + 7) % 7;
    }

    public int getWeekRange() {
        return weekRange;
    }

    public void setWeekRange(int weekRange) {
        this.weekRange = weekRange;
    }


}
