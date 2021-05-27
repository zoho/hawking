//$Id$
package com.zoho.hawking.datetimeparser.components;

import com.zoho.hawking.datetimeparser.DateAndTime;
import com.zoho.hawking.datetimeparser.constants.ConfigurationConstants;
import com.zoho.hawking.language.AbstractLanguage;
import com.zoho.hawking.datetimeparser.utils.DateTimeManipulation;
import com.zoho.hawking.utils.Constants;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;

import java.util.List;

public class WeekParser extends DateTimeComponent {

    private boolean isOrdinalLast = false;

    public WeekParser(String sentenceToParse, String sentenceTense, DateAndTime dateAndTime, AbstractLanguage abstractLanguage) {
        super(sentenceToParse, dateAndTime, sentenceTense, abstractLanguage);
        extractComponentsTags();
        computeNumber();
        findSpanRange();
    }

    @Override
    public void exactSpan() {


    }

    private void ordinalLast(int startDayOfWeek, int endDayOfWeek, int dayDiff) {
        List<DateTime> startDateList;

        if (dateAndTime.getPreviousDependency().equals(Constants.MONTH_SPAN_TAG)) {
            startDateList = DateTimeManipulation.noOfDayOfWeekBetween(dateAndTime.getStart(), dateAndTime.getEnd(), startDayOfWeek);
        } else if (dateAndTime.getPreviousDependency().equals(Constants.YEAR_SPAN_TAG)) {
            startDateList = DateTimeManipulation.noOfDayOfWeekBetween(
                    dateAndTime.getStart().withMonthOfYear(DateTimeConstants.DECEMBER).monthOfYear().withMinimumValue(),
                    dateAndTime.getEnd().withMonthOfYear(DateTimeConstants.DECEMBER).monthOfYear().withMaximumValue(),
                    startDayOfWeek);
        } else {
            startDateList = DateTimeManipulation.noOfDayOfWeekBetween(
                    dateAndTime.getStart().monthOfYear().withMinimumValue(),
                    dateAndTime.getEnd().monthOfYear().withMaximumValue(),
                    startDayOfWeek);
        }
        nthWeekOfSpan(dateAndTime.getStart(), startDateList.size(), startDayOfWeek, endDayOfWeek, dayDiff, dateAndTime.getPreviousDependency());
    }

    private void nthWeekOfSpan(DateTime dateTime, int nthWeekOfMonth, int startDayOfWeek, int endDayOfWeek, int dayDiff, String previousDep) {
        DateTime startDate;
        DateTime endDate;
        if (previousDep.equals(Constants.YEAR_SPAN_TAG)) {
            startDate = DateTimeManipulation.nthWeekOfYear(dateTime, nthWeekOfMonth, startDayOfWeek);
        } else {
            startDate = DateTimeManipulation.nthWeekOfMonth(dateTime, nthWeekOfMonth, startDayOfWeek);
        }

        if (previousDep.equals(Constants.MONTH_SPAN_TAG) &&
                startDate.plusDays(dayDiff).monthOfYear().get() == dateTime.monthOfYear().get()) {
            endDate = startDate.plusDays(dayDiff);
        } else if (previousDep.equals(Constants.YEAR_SPAN_TAG) &&
                startDate.plusDays(dayDiff).year().get() == dateTime.year().get()) {
            endDate = startDate.plusDays(dayDiff);
        } else if (ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekRange() == 0) {
            startDate = startDate.minusWeeks(1);
            endDate = startDate.plusDays(dayDiff);
        } else if (ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekRange() == 1) {
            endDate = dateTime.monthOfYear().withMaximumValue();
        } else {
            endDate = startDate.plusDays(dayDiff);
        }
        dateAndTime.setDateAndTime(startDate);
        //DateTimeManipulation.setWeekStartAndEndTime(dateAndTime, startDate, endDate, 1, 2);
        DateTimeManipulation.setWeekStartAndEndTime(dateAndTime, startDate, endDate, 1);

    }

    //
//	private static Pair<DateTime,DateTime> getDateNearest(List<DateTime> dates, List<DateTime> targetDates, int diff){
//		DateTime startDate = targetDates.get(0);
//		DateTime returnDate = dates.get(0);
//		for(DateTime targetDate: targetDates) {
//			for (DateTime date : dates) {
//			// if the current iteration'sdate is "before" the target date
//				if (Days.daysBetween(targetDate, date).getDays() == diff) { 
//					startDate = targetDate;
//					returnDate=date;
//				}
//			}  
//		}
//		return Pair.of(startDate, returnDate);
//	}
//	
    @Override
    public void nthSpan() {
        if (super.abstractLanguage.weekdayWords.contains(timeSpan)) {
            if (isOrdinalLast) {
                ordinalLast(ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDayStart(),
                        ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDayEnd(),
                        ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDayDiff());
            } else {
                nthWeekOfSpan(dateAndTime.getDateAndTime(), number,
                        ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDayStart(),
                        ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDayEnd(),
                        ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDayDiff(),
                        dateAndTime.getPreviousDependency());
            }

        } else if (super.abstractLanguage.weekendWords.contains(timeSpan)) {
            if (isOrdinalLast) {
                ordinalLast(ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekEndStart(),
                        ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekEndEnd(),
                        ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekEndDiff());
            } else {
                nthWeekOfSpan(dateAndTime.getDateAndTime(), number,
                        ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekEndStart(),
                        ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekEndEnd(),
                        ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekEndDiff(),
                        dateAndTime.getPreviousDependency());
            }
        } else {
            if (isOrdinalLast) {
                ordinalLast(ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekStart(),
                        ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekEnd(),
                        ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDiff());
            } else {
                nthWeekOfSpan(dateAndTime.getDateAndTime(),
                        number,
                        ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekStart(),
                        ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekEnd(),
                        ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDiff(),
                        dateAndTime.getPreviousDependency());
            }
        }

    }

    @Override
    public void past() {
        int endWeekIncrement;
        int endDaysIncrement;
        if (super.abstractLanguage.weekdayWords.contains(timeSpan)) {
            dateAndTime.setDateAndTime(DateTimeManipulation.addWeeks(dateAndTime.getDateAndTime(), -number, 0).
                    dayOfWeek().setCopy(ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDayStart()));
            endWeekIncrement = number - 1;
            endDaysIncrement = ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDayDiff();
            DateTimeManipulation.setWeekStartAndEndTime(dateAndTime, 0, 0, endWeekIncrement, endDaysIncrement, 1, 2);
        } else if (super.abstractLanguage.weekendWords.contains(timeSpan)) {
            dateAndTime.setDateAndTime(DateTimeManipulation.addWeeks(dateAndTime.getDateAndTime(), -number, 0).
                    dayOfWeek().setCopy(ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekEndStart()));
            endWeekIncrement = number - 1;
            endDaysIncrement = ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekEndDiff();
            DateTimeManipulation.setWeekStartAndEndTime(dateAndTime, 0, 0, endWeekIncrement, endDaysIncrement, 1, 2);
        } else {
            if (dateAndTime.getDateAndTime().getDayOfWeek() == 7 &&
                    dateAndTime.getDateAndTime().getDayOfWeek() == ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekStart()) {
                dateAndTime.setDateAndTime(DateTimeManipulation.addWeeks(dateAndTime.getDateAndTime(), -number, 0).
                        dayOfWeek().setCopy(ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekStart()));
            } else if (ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekStart() == 7) {
                dateAndTime.setDateAndTime(DateTimeManipulation.addWeeks(dateAndTime.getDateAndTime(), -number, 0).
                        dayOfWeek().setCopy(DateTimeConstants.MONDAY).minusDays(1));
            } else {
                dateAndTime.setDateAndTime(DateTimeManipulation.addWeeks(dateAndTime.getDateAndTime(), -number, -ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDiff()).
                        dayOfWeek().setCopy(ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekStart()));
            }

            endWeekIncrement = number;
            endDaysIncrement = ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDiff() % 7;
            DateTimeManipulation.setWeekSpanStartAndEndTime(dateAndTime, 0, 0, endWeekIncrement, endDaysIncrement, isImmediate);
//			number = (ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekStart() == 7)?number-1:number;
//			dateAndTime.setDateAndTime(DateTimeManipulation.addWeeks(dateAndTime.getTmpStart(), -number, 0).
//					dayOfWeek().setCopy(ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekStart()));
//			endWeekIncrement = number;
//			endDaysIncrement = ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDiff();
//			DateTimeManipulation.setWeekStartAndEndTime(dateAndTime, 0, 0, endWeekIncrement, endDaysIncrement, 1, 2);
        }

    }

    @Override
    public void present() {
        int startWeekIncrement;
        int endDaysIncrement;
        if (super.abstractLanguage.weekdayWords.contains(timeSpan)) {
            dateAndTime.setDateAndTime(DateTimeManipulation.addWeeks(dateAndTime.getDateAndTime(), number, 0).
                    dayOfWeek().setCopy(ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDayStart()));
            startWeekIncrement = (number - 1);
            endDaysIncrement = ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDayDiff();
            DateTimeManipulation.setWeekStartAndEndTime(dateAndTime, startWeekIncrement, 0, 0, endDaysIncrement, 1, 2);
        } else if (super.abstractLanguage.weekendWords.contains(timeSpan)) {
            dateAndTime.setDateAndTime(DateTimeManipulation.addWeeks(dateAndTime.getDateAndTime(), number, 0).
                    dayOfWeek().setCopy(ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekEndStart()));
            startWeekIncrement = number != 0 ? (number - 1) : 0;
            endDaysIncrement = ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekEndDiff();
            DateTimeManipulation.setWeekStartAndEndTime(dateAndTime, startWeekIncrement, 0, 0, endDaysIncrement, 1, 2);
        } else {
            if (dateAndTime.getDateAndTime().getDayOfWeek() == 7 &&
                    dateAndTime.getDateAndTime().getDayOfWeek() == ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekStart()) {
                dateAndTime.setDateAndTime(DateTimeManipulation.addWeeks(dateAndTime.getDateAndTime(), number, 0).
                        dayOfWeek().setCopy(DateTimeConstants.SUNDAY));
            } else if (ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekStart() == 7) {
                dateAndTime.setDateAndTime(DateTimeManipulation.addWeeks(dateAndTime.getDateAndTime(), number, 0).
                        dayOfWeek().setCopy(DateTimeConstants.MONDAY).minusDays(1));
            } else {
                dateAndTime.setDateAndTime(DateTimeManipulation.addWeeks(dateAndTime.getDateAndTime(), number, 0).
                        dayOfWeek().setCopy(ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekStart()));
            }

            startWeekIncrement = number;
            endDaysIncrement = ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDiff() % 7;
            DateTimeManipulation.setWeekSpanStartAndEndTime(dateAndTime, -startWeekIncrement, 0, 0, endDaysIncrement, isImmediate);
//			number = (ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekStart() == 7)?number-1:number;
//			dateAndTime.setDateAndTime(DateTimeManipulation.addWeeks(dateAndTime.getTmpStart(), number, 0).
//					dayOfWeek().setCopy(ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekStart()));
//			startWeekIncrement = number;
//			endDaysIncrement = ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDiff();
//			DateTimeManipulation.setWeekStartAndEndTime(dateAndTime, startWeekIncrement, 0, 0, endDaysIncrement,1 , 2);

        }

    }

    @Override
    public void future() {

        present();

    }

    @Override
    public void immediateFuture() {

        present();

    }


    @Override
    public void immediatePast() {

        past();

    }


    @Override
    public void immediate() {

        isImmediate = true;
        number = number - 1;

        if (sentenceTense.equals("PAST")) {
            past();
        } else {
            present();
        }


    }

    @Override
    public void remainder() {

        int endDaysIncrement;
        if (super.abstractLanguage.weekdayWords.contains(timeSpan)) {
            endDaysIncrement = ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekDayEnd() % 7 - dateAndTime.getDateAndTime().getDayOfWeek();
            DateTimeManipulation.setWeekStartAndEndTime(dateAndTime, 0, 0, 0, endDaysIncrement, 1, 2);
        } else if (super.abstractLanguage.weekendWords.contains(timeSpan)) {
            endDaysIncrement = ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekEndStart() % 7 - dateAndTime.getDateAndTime().getDayOfWeek();
            DateTimeManipulation.setWeekStartAndEndTime(dateAndTime, 0, 0, 0, endDaysIncrement, 1, 2);
        } else {
            endDaysIncrement = ConfigurationConstants.getConfiguration().getWeekDayAndEnd().getWeekStart() % 7 - dateAndTime.getDateAndTime().getDayOfWeek();
            DateTimeManipulation.setWeekStartAndEndTime(dateAndTime, 0, 0, 0, endDaysIncrement, 1, 2);
        }

    }


    /*Used to parse week related tags from the sentence*/
    @Override
    void extractComponentsTags() {
        if (getTagMap().containsKey(Constants.SET_WEEK_TAG)) {
            timeSpan = getTagMap().get(Constants.SET_WEEK_TAG);
            isSet = true;
        } else if (getTagMap().containsKey(Constants.WEEK_SPAN_TAG) || isSet) {
            timeSpan = getTagMap().get(Constants.WEEK_SPAN_TAG);
        }
    }

    @Override
    void computeNumber() {
        if (!isNumberPresent) {
            if (super.abstractLanguage.weekdayWords.contains(timeSpan) ||
                    super.abstractLanguage.weekendWords.contains(timeSpan) ||
                    super.abstractLanguage.weekWords.contains(timeSpan)) {
                number = ConfigurationConstants.getConfiguration().getRangeDefault().getWeek();
            } else if (super.abstractLanguage.weekdaysWords.contains(timeSpan) ||
                    super.abstractLanguage.weekendsWords.contains(timeSpan) ||
                    super.abstractLanguage.weeksWords.contains(timeSpan)) {
                number = ConfigurationConstants.getConfiguration().getRangeDefault().getWeeks();
            }
        }

        //TODO
//        if (!dateAndTime.getPreviousDependency().equals("") && tenseIndicator.equals(PrepositionConstants.LAST.getWord())) {
//            isOrdinal = true;
//            isNumberPresent = true;
//            isOrdinalLast = true;
//        }
    }

    @Override
    public void setPreviousDependency() {
        if (isSet) {
            calculateRecurrentPeriod();
        }
        dateAndTime.setPreviousDependency(Constants.WEEK_SPAN_TAG);
    }

    private void calculateRecurrentPeriod() {
        DateTime history;
        DateTime today;
        Duration duration;
        long millis;
        long recCount = -1;

        if (dateAndTime.getPreviousDependency().equals(Constants.YEAR_SPAN_TAG)) {
            history = dateAndTime.getDateAndTime().withTimeAtStartOfDay();
            today = dateAndTime.getDateAndTime().plusMonths(12).withTimeAtStartOfDay();
            duration = new Duration(history, today);
            millis = Math.abs(duration.getMillis());
            if (!dateAndTime.getPreviousDependency().equals("")) {
                recCount = sentenceTense.equals("PAST") ? calculateRecurrentCount(dateAndTime.getTmpStartTime(), dateAndTime.getDateAndTime().withTimeAtStartOfDay(), number)
                        : calculateRecurrentCount(dateAndTime.getDateAndTime().withTimeAtStartOfDay(), dateAndTime.getTmpEndTime(), number);
            }
        } else if (dateAndTime.getPreviousDependency().equals(Constants.MONTH_SPAN_TAG)) {
            history = dateAndTime.getDateAndTime().withTimeAtStartOfDay();
            today = dateAndTime.getDateAndTime().plusMonths(1).withTimeAtStartOfDay();
            duration = new Duration(history, today);
            millis = Math.abs(duration.getMillis());
            if (!dateAndTime.getPreviousDependency().equals("")) {
                recCount = sentenceTense.equals("PAST") ? calculateRecurrentCount(dateAndTime.getTmpStartTime(), dateAndTime.getDateAndTime().withTimeAtStartOfDay(), number * 7)
                        : calculateRecurrentCount(dateAndTime.getDateAndTime().withTimeAtStartOfDay(), dateAndTime.getTmpEndTime(), number * 7);
            }
        } else {
            history = dateAndTime.getDateAndTime().withTimeAtStartOfDay();
            today = dateAndTime.getDateAndTime().plusWeeks(number).withTimeAtStartOfDay();
            duration = new Duration(history, today);
            millis = Math.abs(duration.getMillis());
            if (!dateAndTime.getPreviousDependency().equals("")) {
                recCount = sentenceTense.equals("PAST") ? calculateRecurrentCount(dateAndTime.getTmpStartTime(), dateAndTime.getDateAndTime().withTimeAtStartOfDay(), dateAndTime.getStart().getDayOfWeek())
                        : calculateRecurrentCount(dateAndTime.getDateAndTime().withTimeAtStartOfDay(), dateAndTime.getTmpEndTime(), dateAndTime.getStart().getDayOfWeek());
            }
        }
        dateAndTime.setWeekRecurrentPeriod(millis);
        if (recCount != -1) {
            dateAndTime.setWeekRecurrentCount(recCount);
        }

    }

    private int calculateRecurrentCount(DateTime start, DateTime end, int dayOfSpan) {
        int count = 0;
        while (start.isBefore(end)) {
            start = start.plusWeeks(1);
            if (start.getDayOfWeek() == dayOfSpan) {
                count++;
            } else if (start.getWeekOfWeekyear() == dayOfSpan) {
                count++;
            } else if (start.getDayOfMonth() == dayOfSpan) {
                count++;
            }
        }
        return count;
    }


}
