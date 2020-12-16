//$Id$
package com.zoho.hawking.datetimeparser;

import com.zoho.hawking.language.english.model.DateGroup;
import com.zoho.hawking.language.english.model.RepeatCount;
import com.zoho.hawking.language.english.model.RepeatPeriod;
import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.HashMap;

public class DateAndTime {

    private boolean isStart = true;
    private boolean isEnd = true;
    private boolean isAfter = false;
    private boolean isBefore = false;
    private boolean isSet = false;
    private int spanPeriod = 0;
    private String previousDependency = "";
    private String tempPreviousDependency = "";
    private DateTime start = null;
    private DateTime end = null;
    private DateTime dateAndTime;
    private long yearRecurrentPeriod;
    private long yearRecurrentCount = 6;
    private long monthRecurrentPeriod;
    private long monthRecurrentCount = 6;
    private long weekRecurrentPeriod;
    private long weekRecurrentCount = 6;
    private long dayRecurrentPeriod;
    private long dayRecurrentCount = 6;
    private long hourRecurrentPeriod;
    private long hourRecurrentCount = 6;
    private long customRecurrentPeriod;
    private long customRecurrentCount = 6;
    private DateTime tmpStartTime;
    private DateTime referenceTime;
    private DateTime tmpEndTime;

    public DateAndTime(DateTime dateAndTime) {
        this.referenceTime = dateAndTime;
        this.dateAndTime = dateAndTime;
    }

    public DateAndTime() {
        this.dateAndTime = new DateTime();
    }

    public DateTime getDateAndTime() {
        return this.dateAndTime;
    }

    public void setDateAndTime(DateTime start) {
        this.dateAndTime = start;
    }

    public DateTime getStart() {
        if (isStart) {
            return this.start;
        }
        return null;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getEnd() {
        if (isEnd) {
            return this.end;
        }
        return null;
    }

    public void setEnd(DateTime end) {
        if ((!previousDependency.equals(tempPreviousDependency) || previousDependency.equals(""))) {
            tmpStartTime = this.start;
            tmpEndTime = this.end != null ? this.end : end;
        }
        this.end = end;

        if (isAfter) {
            this.start = this.end.minuteOfHour().roundCeilingCopy();
        }

        if (isBefore) {
            this.end = this.start.minuteOfHour().roundFloorCopy().minusMillis(1);
        }

    }

    public void setIsStart(boolean isStart) {
        this.isStart = isStart;
    }

    public void setIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public String toString() {
        String range = "";
        if (isStart || isAfter) {
            range += "start:" + this.start + "\n"; //NO I18n
        }
        if (isEnd || isBefore) {
            range += "end:" + this.end + "\n";  //NO I18n
        }
        if (yearRecurrentPeriod != 0) {
            range += "yearRecurrentPeriod:" + this.yearRecurrentPeriod + "\n";//NO I18n
            long days = yearRecurrentPeriod / 86400000;
            range += "year:" + (days / 365); //No I18N
        }

        if (monthRecurrentPeriod != 0) {
            range += "monthRecurrentPeriod:" + this.monthRecurrentPeriod + "\n"; //NO I18n
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(monthRecurrentPeriod);
            range += "month:" + c.get(Calendar.MONTH); //NO I18n

            range += "monthReccurentCount:" + this.monthRecurrentCount; //No I18N
        }

        if (weekRecurrentPeriod != 0) {
            range += "weekRecurrentPeriod:" + this.weekRecurrentPeriod + "\n"; //NO I18n

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(weekRecurrentPeriod);
            range += "week:" + (c.get(Calendar.DAY_OF_MONTH) - 1) / 7; //NO I18n

            range += "weekReccurentCount:" + this.weekRecurrentCount; //No I18N
        }

        if (dayRecurrentPeriod != 0) {
            range += "dayRecurrentPeriod:" + this.dayRecurrentPeriod + "\n"; //NO I18n

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(dayRecurrentPeriod);
            range += "day:" + (this.dayRecurrentPeriod / (60 * 60 * 24 * 1000)); //NO I18n

            range += "dayReccurentCount:" + this.dayRecurrentCount; //No I18N
        }

        if (hourRecurrentPeriod != 0) {
            range += "hourRecurrentPeriod:" + this.hourRecurrentPeriod + "\n"; //NO I18n

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(hourRecurrentPeriod);
            range += "hour:" + (this.hourRecurrentPeriod / (60 * 60 * 24 * 1000)); //NO I18n

            range += "hourReccurentCount:" + this.hourRecurrentCount; //No I18N
        }

        if (customRecurrentPeriod != 0) {
            range += "CustomRecurrentPeriod:" + this.customRecurrentPeriod + "\n"; //NO I18n

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(customRecurrentPeriod);
            range += "custom:" + (this.customRecurrentPeriod / (60 * 60 * 24 * 1000)); //NO I18n

            range += "customReccurentCount:" + this.customRecurrentCount; //No I18N
        }
        return range;
    }

    public HashMap<String, DateTime> toMap() {
        HashMap<String, DateTime> rangeMap = new HashMap<>();
        if (isStart) {
            rangeMap.put("start", this.start);
        }
        if (isEnd) {
            rangeMap.put("end", this.end);
        }
        return rangeMap;
    }

    public void setIsAfter(boolean isAfter) {
        this.isAfter = isAfter;
    }

    public void setIsBefore(boolean isBefore) {
        this.isBefore = isBefore;
    }

    public String getPreviousDependency() {
        return previousDependency;
    }

    public void setPreviousDependency(String previousDependency) {
        this.tempPreviousDependency = this.previousDependency.equals("") ? previousDependency : this.previousDependency;
        this.previousDependency = previousDependency;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean isSet) {
        this.isSet = isSet;
    }

    public int getSpanPeriod() {
        return spanPeriod;
    }

    public void setSpanPeriod(int spanPeriod) {
        this.spanPeriod = spanPeriod;
    }

    public long getYearRecurrentPeriod() {
        return yearRecurrentPeriod;
    }

    public void setYearRecurrentPeriod(long yearRecurrentPeriod) {
        this.yearRecurrentPeriod = yearRecurrentPeriod;
    }

    public long getYearRecurrentCount() {
        return yearRecurrentCount;
    }

    public void setYearRecurrentCount(long yearRecurrentCount) {
        this.yearRecurrentCount = yearRecurrentCount;
    }

    public long getMonthRecurrentPeriod() {
        return monthRecurrentPeriod;
    }

    public void setMonthRecurrentPeriod(long monthRecurrentPeriod) {
        this.monthRecurrentPeriod = monthRecurrentPeriod;
    }

    public long getMonthRecurrentCount() {
        return monthRecurrentCount;
    }

    public void setMonthRecurrentCount(long monthRecurrentCount) {
        this.monthRecurrentCount = monthRecurrentCount;
    }

    public DateTime getTmpStartTime() {
        return tmpStartTime;
    }

    public DateTime getTmpEndTime() {
        return tmpEndTime;
    }

    public long getWeekRecurrentPeriod() {
        return weekRecurrentPeriod;
    }

    public void setWeekRecurrentPeriod(long weekRecurrentPeriod) {
        this.weekRecurrentPeriod = weekRecurrentPeriod;
    }

    public long getWeekRecurrentCount() {
        return weekRecurrentCount;
    }

    public void setWeekRecurrentCount(long weekRecurrentCount) {
        this.weekRecurrentCount = weekRecurrentCount;
    }

    public long getDayRecurrentPeriod() {
        return dayRecurrentPeriod;
    }

    public void setDayRecurrentPeriod(long dayRecurrentPeriod) {
        this.dayRecurrentPeriod = dayRecurrentPeriod;
    }

    public long getDayRecurrentCount() {
        return dayRecurrentCount;
    }

    public void setDayRecurrentCount(long dayRecurrentCount) {
        this.dayRecurrentCount = dayRecurrentCount;
    }

    public long getHourRecurrentPeriod() {
        return hourRecurrentPeriod;
    }

    public void setHourRecurrentPeriod(long hourRecurrentPeriod) {
        this.hourRecurrentPeriod = hourRecurrentPeriod;
    }

    public long getHourRecurrentCount() {
        return hourRecurrentCount;
    }

    public void setHourRecurrentCount(long hourRecurrentCount) {
        this.hourRecurrentCount = hourRecurrentCount;
    }

    /**
     * @return the customRecurrentPeriod
     */
    public long getCustomRecurrentPeriod() {
        return customRecurrentPeriod;
    }

    /**
     * @param customRecurrentPeriod the customRecurrentPeriod to set
     */
    public void setCustomRecurrentPeriod(long customRecurrentPeriod) {
        this.customRecurrentPeriod = customRecurrentPeriod;
    }

    /**
     * @return the customRecurrentCount
     */
    public long getCustomRecurrentCount() {
        return customRecurrentCount;
    }

    /**
     * @param customRecurrentCount the customRecurrentCount to set
     */
    public void setCustomRecurrentCount(long customRecurrentCount) {
        this.customRecurrentCount = customRecurrentCount;
    }

    public DateTime getReferenceTime() {
        return referenceTime;
    }


    public DateGroup getDateGroup() {
        DateGroup dateGroup = new DateGroup();
        if (yearRecurrentPeriod != 0 || monthRecurrentPeriod != 0 || weekRecurrentPeriod != 0 ||
                dayRecurrentPeriod != 0 || hourRecurrentPeriod != 0) {
            dateGroup.setSequenceType("REPEAT"); //No I18N

            RepeatPeriod recurrentPeriod = new RepeatPeriod(yearRecurrentPeriod, monthRecurrentPeriod, weekRecurrentPeriod,
                    dayRecurrentPeriod, hourRecurrentPeriod, customRecurrentPeriod);
            dateGroup.setRecurrentPeriod(recurrentPeriod);
            long recurrentCountYear = (yearRecurrentPeriod != 0) ? yearRecurrentCount : 0;
            long recurrentCountMonth = (monthRecurrentPeriod != 0) ? monthRecurrentCount : 0;
            long recurrentCountWeek = (weekRecurrentPeriod != 0) ? weekRecurrentCount : 0;
            long recurrentCountDay = (dayRecurrentPeriod != 0) ? dayRecurrentCount : 0;
            long recurrentCountHour = (hourRecurrentPeriod != 0) ? hourRecurrentCount : 0;
            long recurrentCountCustomDate = customRecurrentCount;
            RepeatCount recurrentCount = new RepeatCount(recurrentCountYear, recurrentCountMonth, recurrentCountWeek,
                    recurrentCountDay, recurrentCountHour, recurrentCountCustomDate);
            dateGroup.setRecurrentCount(recurrentCount);
        }
        return dateGroup;
    }


}
