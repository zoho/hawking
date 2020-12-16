//$Id$
package com.zoho.hawking.datetimeparser.components;

import com.zoho.hawking.datetimeparser.DateAndTime;
import com.zoho.hawking.datetimeparser.constants.ConfigurationConstants;
import com.zoho.hawking.language.AbstractLanguage;
import com.zoho.hawking.datetimeparser.utils.DateTimeManipulation;
import com.zoho.hawking.utils.Constants;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.Duration;

import java.util.List;

public class DayParser extends DateTimeComponent {
    private boolean isCurrentSpanPresent = false;
    private boolean isOrdinalLast = false;

    public DayParser(String sentenceToParse, String sentenceTense, DateAndTime dateAndTime, AbstractLanguage abstractLanguage) {
        super(sentenceToParse, dateAndTime, sentenceTense, abstractLanguage);
        extractComponentsTags();
        computeNumber();
        findSpanRange();
    }

    @Override
    void extractComponentsTags() {
        if (getTagMap().containsKey(Constants.DAY_SPAN_TAG)) {
            timeSpan = getTagMap().get(Constants.DAY_SPAN_TAG);
        } else if (getTagMap().containsKey(Constants.CURRENT_DAY_TAG)) {
            timeSpan = getTagMap().get(Constants.CURRENT_DAY_TAG);
            isCurrentSpanPresent = true;
        } else if (getTagMap().containsKey(Constants.DAY_OF_WEEK_TAG)) {
            timeSpan = getTagMap().get(Constants.DAY_OF_WEEK_TAG);
            isExactTimeSpan = true;
            timeSpanValue = super.abstractLanguage.daysOfWeek.getOrDefault(timeSpan, 0);
        } else if (getTagMap().containsKey(Constants.SET_DAY_TAG)) {
            timeSpan = getTagMap().get(Constants.SET_DAY_TAG);
            isSet = true;
        }

    }

    @Override
    void computeNumber() {

        if (!isNumberPresent) {
            if (super.abstractLanguage.dayWords.contains(timeSpan)) {
                number = ConfigurationConstants.getConfiguration().getRangeDefault().getDay();
            } else if (super.abstractLanguage.daysWords.contains(timeSpan)) {
                number = ConfigurationConstants.getConfiguration().getRangeDefault().getDays();
            }
        }

//        if (!dateAndTime.getPreviousDependency().equals("") && tenseIndicator.equals(PrepositionConstants.LAST.getWord())) {
//            isOrdinal = true;
//            isNumberPresent = true;
//            isOrdinalLast = true;
//        }
    }

    @Override
    public void exactSpan() {
        if (isCurrentSpanPresent) {
            computeCurrentSpan(timeSpan);
        } else {
            DateTimeManipulation.setDayStartAndEndTime(dateAndTime, 0, 0, 1, 2);
        }

    }

    private void computeCurrentSpan(String timeSpanValue) {
        switch (timeSpanValue) {
            case "now":
                DateTimeManipulation.setDayStartAndEndTime(dateAndTime, 0, 0, 0, 0);
                break;
            case "today":
                DateTimeManipulation.setDayStartAndEndTime(dateAndTime, 0, 0, 1, 2);
                break;
            case "yesterday":
                dateAndTime.setDateAndTime(DateTimeManipulation.addDays(dateAndTime.getDateAndTime(), 0, -1));
                DateTimeManipulation.setDayStartAndEndTime(dateAndTime, 0, 0, 1, 2);
                break;
            case "tomorrow":
                dateAndTime.setDateAndTime(DateTimeManipulation.addDays(dateAndTime.getDateAndTime(), 0, 1));
                DateTimeManipulation.setDayStartAndEndTime(dateAndTime, 0, 0, 1, 2);
                break;
        }
    }

    private void ordinalLast() {
        int nthDayOfSpan;
        List<DateTime> startDateList;
        switch (dateAndTime.getPreviousDependency()) {
            case Constants.YEAR_SPAN_TAG:
                if (isExactTimeSpan) {
                    startDateList = DateTimeManipulation.noOfDayOfWeekBetween(dateAndTime.getStart(), dateAndTime.getEnd(), timeSpanValue);
                    nthDayOfSpan = startDateList.size();
                } else {
                    nthDayOfSpan = dateAndTime.getDateAndTime().dayOfYear().getMaximumValue();
                }
                break;
            case Constants.MONTH_SPAN_TAG:
                if (isExactTimeSpan) {
                    startDateList = DateTimeManipulation.noOfDayOfWeekBetween(dateAndTime.getStart(), dateAndTime.getEnd(), timeSpanValue);
                    nthDayOfSpan = startDateList.size();
                } else {
                    nthDayOfSpan = dateAndTime.getDateAndTime().dayOfMonth().getMaximumValue();
                }
                break;
            case Constants.WEEK_SPAN_TAG:
                if (isExactTimeSpan) {
                    startDateList = DateTimeManipulation.noOfDayOfWeekBetween(dateAndTime.getStart(), dateAndTime.getEnd(), timeSpanValue);
                    nthDayOfSpan = startDateList.size();
                } else {
                    nthDayOfSpan = dateAndTime.getDateAndTime().dayOfWeek().getMaximumValue();
                }
                break;
            default:
                nthDayOfSpan = 6;
                break;
        }
        nthDayOfSpan(nthDayOfSpan, dateAndTime.getPreviousDependency());
    }

    public void nthDayOfSpan(int nthDayOfSpan, String previousDep) {

        switch (previousDep) {
            case Constants.YEAR_SPAN_TAG:
                if (isExactTimeSpan) {
                    dateAndTime.setDateAndTime(DateTimeManipulation.nthWeekOfYear(dateAndTime.getDateAndTime(), nthDayOfSpan, timeSpanValue));
                } else {
                    dateAndTime.setDateAndTime(DateTimeManipulation.nthDayOfYear(dateAndTime.getDateAndTime(), nthDayOfSpan));
                }
                break;
            case Constants.MONTH_SPAN_TAG:
                if (isExactTimeSpan) {
                    dateAndTime.setDateAndTime(DateTimeManipulation.nthWeekOfMonth(dateAndTime.getDateAndTime(), nthDayOfSpan, timeSpanValue));
                } else {
                    dateAndTime.setDateAndTime(DateTimeManipulation.nthDayOfMonth(dateAndTime.getDateAndTime(), nthDayOfSpan));
                }

                break;
            case Constants.WEEK_SPAN_TAG:
                if (isExactTimeSpan) {
                    dateAndTime.setDateAndTime(DateTimeManipulation.nthDayofWeek(dateAndTime.getDateAndTime(), nthDayOfSpan, timeSpanValue));
                } else {
                    dateAndTime.setDateAndTime(DateTimeManipulation.nthDayOfWeek(dateAndTime.getDateAndTime(), nthDayOfSpan));
                }

                break;
            default:
                dateAndTime.setDateAndTime(DateTimeManipulation.nthDayOfWeek(dateAndTime.getDateAndTime(), nthDayOfSpan));
                break;
        }

        DateTimeManipulation.setDayStartAndEndTime(dateAndTime, 0, 0, 1, 2);
    }

    @Override
    public void nthSpan() {
        if (isOrdinalLast) {
            ordinalLast();
        } else {
            nthDayOfSpan(number, dateAndTime.getPreviousDependency());
        }

    }

    private void exactSpan(int daysToAdd, String tense) {
        /*
         * 1) Jump to next or past 7 days
         * 2) from there find two nearest dayOfWeek
         * 	Ex : currentDay 2018-06-18 (Saturday)
         * 		Jumped date - 2018-06-23
         * 		Query next Monday
         * 		Nearest Dates - 2018-06-18 2018-06-25
         * 		diff of two date with Jumped date 5 and 2
         * 		the date with lowest diff and not is the same week(sun - sat) of Jumped date considered */
        DateAndTime localDate = new DateAndTime(dateAndTime.getDateAndTime());
        DateTime jumpedDate = localDate.getDateAndTime().plusDays(daysToAdd); //jumped day
        int startWeekIncrement = 0;
        int endDaysIncrement = DateTimeConstants.SATURDAY % 7;
        localDate.setDateAndTime(DateTimeManipulation.addWeeks(localDate.getDateAndTime(), 0, 0).
                dayOfWeek().setCopy(DateTimeConstants.MONDAY).minusDays(1));
        DateTimeManipulation.setWeekSpanStartAndEndTime(localDate, startWeekIncrement, 0, 0, endDaysIncrement, false);
        DateTime startDate = localDate.getStart();
        DateTime endDate = localDate.getEnd();

        DateTime firstNearestDate = jumpedDate.plusDays((timeSpanValue == 7) ? -7 : 0).dayOfWeek().setCopy(timeSpanValue);
        DateTime secondNearestDate = jumpedDate.plusDays((timeSpanValue == 7) ? 0 : 7).dayOfWeek().setCopy(timeSpanValue);

        int firstDaysDiff = Math.abs(Days.daysBetween(jumpedDate, firstNearestDate).getDays());
        int secondDaysDiff = Math.abs(Days.daysBetween(jumpedDate, secondNearestDate).getDays());
        DateTime exactDate;
        if (tense.equals("PAST")) {
            if (timeSpanValue == dateAndTime.getDateAndTime().getDayOfWeek()) {
                exactDate = jumpedDate;
            } else if (firstDaysDiff < secondDaysDiff && firstNearestDate.isBefore(startDate)) {
                exactDate = firstNearestDate;
            } else {
                exactDate = secondNearestDate;
            }
        } else {
            if (timeSpanValue == dateAndTime.getDateAndTime().getDayOfWeek()) {
                exactDate = jumpedDate;
            } else if (firstDaysDiff < secondDaysDiff && firstNearestDate.isAfter(endDate)) {
                exactDate = firstNearestDate;
            } else {
                exactDate = secondNearestDate;
            }
        }
        dateAndTime.setDateAndTime(exactDate);
        exactSpan();
    }


    private void exactSpan(String tense) {
        if (tense.equals("PAST")) {
            dateAndTime.setDateAndTime(DateTimeManipulation.recentPastDay(dateAndTime.getDateAndTime(), timeSpanValue));
        } else {
            dateAndTime.setDateAndTime(DateTimeManipulation.recentFutureDay(dateAndTime.getDateAndTime(), timeSpanValue));
        }
        exactSpan();
    }


    @Override
    public void past() {

        if (isCurrentSpanPresent) {
            exactSpan();
        } else if (isExactTimeSpan) {
            if (tenseIndicator.equals("")) {
                exactSpan("PAST"); //No I18N
            } else {
                exactSpan(-7, "PAST"); //No I18N
            }
        } else {
            dateAndTime.setDateAndTime(DateTimeManipulation.addDays(dateAndTime.getDateAndTime(), 0, -number));
            DateTimeManipulation.setDaySpanStartAndEndTime(dateAndTime, 0, number, isImmediate);
        }

    }

    @Override
    public void present() {

        if (isCurrentSpanPresent) {
            exactSpan();
        } else if (isExactTimeSpan) {
            if (tenseIndicator.equals("")) {
                exactSpan("PRESENT"); //No I18N
            } else {
                exactSpan(7, "PRESENT"); //No I18N
            }
        } else {
            dateAndTime.setDateAndTime(DateTimeManipulation.addDays(dateAndTime.getDateAndTime(), 0, number));
            DateTimeManipulation.setDaySpanStartAndEndTime(dateAndTime, -number, 0, isImmediate);
        }

    }

    @Override
    public void future() {
        // TODO check both this and present are same
        if (isCurrentSpanPresent) {
            exactSpan();
        } else if (isExactTimeSpan) {
            if (tenseIndicator.equals("")) {
                exactSpan("FUTURE"); //No I18N
            } else {
                exactSpan(7, "FUTURE"); //No I18N
            }
        } else {
            present();
        }

    }

    @Override
    public void immediateFuture() {
        if (isExactTimeSpan) {
            exactSpan("PRESENT"); //No I18N
        } else {
            future();
        }

    }


    @Override
    public void immediatePast() {

        if (isExactTimeSpan) {
            exactSpan("PAST"); //No I18N
        } else {
            past();
        }

    }

    @Override
    public void immediate() {
        isImmediate = true;
        if (isExactTimeSpan) {
            if (sentenceTense.equals("PAST")) {
                exactSpan("PAST"); //No I18N
            } else {
                exactSpan("PRESENT"); //No I18N
            }
        } else if (isNumberPresent) {
            number = number - 1;
            if (sentenceTense.equals("PAST")) {
                past();
            } else {
                present();
            }
        } else {
            number = number - 1;
            present();
        }

    }

    @Override
    public void remainder() {
        DateTimeManipulation.setDayStartAndEndTime(dateAndTime, 0, 0, 0, 2);

    }


    void calculateRecurrentPeriod() {

        DateTime history;
        DateTime today;
        Duration duration;
        long millis;
        long recCount = -1;

        switch (dateAndTime.getPreviousDependency()) {
            case Constants.YEAR_SPAN_TAG:
                history = dateAndTime.getDateAndTime().withTimeAtStartOfDay();
                today = dateAndTime.getDateAndTime().plusMonths(12).withTimeAtStartOfDay();
                duration = new Duration(history, today);
                millis = Math.abs(duration.getMillis());
                if (!dateAndTime.getPreviousDependency().equals("")) {
                    recCount = sentenceTense.equals("PAST") ? calculateRecurrentCount(dateAndTime.getTmpStartTime(), dateAndTime.getDateAndTime().withTimeAtStartOfDay(), number)
                            : calculateRecurrentCount(dateAndTime.getDateAndTime().withTimeAtStartOfDay(), dateAndTime.getTmpEndTime(), number);
                }
                break;
            case Constants.MONTH_SPAN_TAG:
                history = dateAndTime.getDateAndTime().withTimeAtStartOfDay();
                today = dateAndTime.getDateAndTime().plusMonths(1).withTimeAtStartOfDay();
                duration = new Duration(history, today);
                millis = Math.abs(duration.getMillis());
                if (!dateAndTime.getPreviousDependency().equals("")) {
                    recCount = sentenceTense.equals("PAST") ? calculateRecurrentCount(dateAndTime.getTmpStartTime(), dateAndTime.getDateAndTime().withTimeAtStartOfDay(), number * 7)
                            : calculateRecurrentCount(dateAndTime.getDateAndTime().withTimeAtStartOfDay(), dateAndTime.getTmpEndTime(), number * 7);
                }
                break;
            case Constants.WEEK_SPAN_TAG:
                history = dateAndTime.getDateAndTime().withTimeAtStartOfDay();
                today = dateAndTime.getDateAndTime().plusWeeks(number).withTimeAtStartOfDay();
                duration = new Duration(history, today);
                millis = Math.abs(duration.getMillis());
                if (!dateAndTime.getPreviousDependency().equals("")) {
                    recCount = sentenceTense.equals("PAST") ? calculateRecurrentCount(dateAndTime.getTmpStartTime(), dateAndTime.getDateAndTime().withTimeAtStartOfDay(), dateAndTime.getStart().getDayOfWeek())
                            : calculateRecurrentCount(dateAndTime.getDateAndTime().withTimeAtStartOfDay(), dateAndTime.getTmpEndTime(), dateAndTime.getStart().getDayOfWeek());
                }
                break;
            default:
                if (isExactTimeSpan) {
                    history = dateAndTime.getDateAndTime().withTimeAtStartOfDay();
                    today = dateAndTime.getDateAndTime().plusDays(7).withTimeAtStartOfDay();
                    duration = new Duration(history, today);
                    millis = Math.abs(duration.getMillis());
                } else {
                    history = dateAndTime.getDateAndTime().withTimeAtStartOfDay();
                    today = dateAndTime.getDateAndTime().plusDays(number).withTimeAtStartOfDay();
                    duration = new Duration(history, today);
                    millis = Math.abs(duration.getMillis());
                }
                if (!dateAndTime.getPreviousDependency().equals("")) {
                    recCount = sentenceTense.equals("PAST") ? calculateRecurrentCount(dateAndTime.getTmpStartTime(), dateAndTime.getDateAndTime().withTimeAtStartOfDay(), number)
                            : calculateRecurrentCount(dateAndTime.getDateAndTime().withTimeAtStartOfDay(), dateAndTime.getTmpEndTime(), number);
                }

                break;
        }
        dateAndTime.setDayRecurrentPeriod(millis);
        if (recCount != -1) {
            dateAndTime.setDayRecurrentCount(recCount);
        }
    }

    private int calculateRecurrentCount(DateTime start, DateTime end, int dayOfSpan) {
        int count = 0;
        while (start.isBefore(end)) {
            start = start.plusDays(1);
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

    @Override
    public void setPreviousDependency() {
        if (isSet) {
            calculateRecurrentPeriod();
        }
        dateAndTime.setPreviousDependency(Constants.DAY_OF_WEEK_TAG);
    }


}
