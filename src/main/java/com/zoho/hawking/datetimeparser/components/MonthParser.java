//$Id$
package com.zoho.hawking.datetimeparser.components;

import com.zoho.hawking.datetimeparser.DateAndTime;
import com.zoho.hawking.datetimeparser.constants.ConfigurationConstants;
import com.zoho.hawking.language.AbstractLanguage;
import com.zoho.hawking.language.english.DateTimeWordProperties;
import com.zoho.hawking.datetimeparser.utils.DateTimeManipulation;
import com.zoho.hawking.utils.Constants;
import org.joda.time.DateTime;
import org.joda.time.Duration;

public class MonthParser extends DateTimeComponent {

    private boolean isDatePresent = false;
    private int nthDayOfMonth;
    private int nthMonthOfYear;
    private int monthSpan;

    public MonthParser(String sentenceToParse, String sentenceTense, DateAndTime dateAndTime, AbstractLanguage abstractLanguage) {
        super(sentenceToParse, dateAndTime, sentenceTense, abstractLanguage);
        extractComponentsTags();
        computeNumber();
        findSpanRange();
    }


    void extractComponentsTags() {

        if (getTagMap().containsKey(Constants.SET_MONTH_TAG) || isSet) {
            if (isSet) {
                if (getTagMap().containsKey(Constants.MONTH_SPAN_TAG)) {
                    timeSpan = getTagMap().get(Constants.MONTH_SPAN_TAG);
                } else {
                    timeSpan = getTagMap().get(Constants.MONTH_OF_YEAR_TAG);
                    nthMonthOfYear = super.abstractLanguage.monthsOfYear.getOrDefault(timeSpan, 0);
                    isExactTimeSpan = true;
                }
            } else {
                timeSpan = getTagMap().get(Constants.SET_MONTH_TAG);
            }
            if (!dateAndTime.getPreviousDependency().equals("")) {
                dateAndTime.setDateAndTime(dateAndTime.getStart());
            }
//			}else if(dateAndTime.getEnd() != null){
//				dateAndTime.setDateAndTime(dateAndTime.getEnd());
//			}
            isSet = true;
        } else if (getTagMap().containsKey(Constants.MONTH_SPAN_TAG)) {
            timeSpan = getTagMap().get(Constants.MONTH_SPAN_TAG);
        } else if (getTagMap().containsKey(Constants.MONTH_OF_YEAR_TAG)) {
            timeSpan = getTagMap().get(Constants.MONTH_OF_YEAR_TAG);
            nthMonthOfYear = super.abstractLanguage.monthsOfYear.getOrDefault(timeSpan, 0);
            isExactTimeSpan = true;
        }
    }

    public void computeNumber() {
        if (isNumberPresent && !isOrdinal && (timeSpan.equals(DateTimeWordProperties.MONTH.getWord()) ||
                timeSpan.equals(DateTimeWordProperties.MONTHS.getWord()))) {
            monthSpan = number;
        } else {
            if (super.abstractLanguage.monthWords.contains(timeSpan)) {

                monthSpan = ConfigurationConstants.getConfiguration().getRangeDefault().getMonth();
            } else if (super.abstractLanguage.monthsWords.contains(timeSpan)) {
                monthSpan = ConfigurationConstants.getConfiguration().getRangeDefault().getMonths();
            }
        }

        //in last year last month,last month refers to 12th month of the year
        //in order to distinguish between last month and last year last month this check is done
        //TODO
//        if (!isSet && dateAndTime.getPreviousDependency().equals(Constants.YEAR_SPAN_TAG)) {
//            if (tenseIndicator.equals(PrepositionConstants.LAST.getWord())) {
//                isOrdinal = true;
//                isNumberPresent = true;
//                nthMonthOfYear = 12;
//            }
//        }

        if (isExactTimeSpan && isNumberPresent) {
            isDatePresent = true;
            nthDayOfMonth = number;
        }

        if (monthSpan != 0 && isOrdinal) {
            nthDayOfMonth = number;
            isDatePresent = true;
            isOrdinal = false;
        }

    }

    @Override
    public void exactSpan() {
        if (isDatePresent) {
            dateAndTime.setDateAndTime(DateTimeManipulation.nthDayOfMonth(dateAndTime.getDateAndTime(), nthDayOfMonth));
            DateTimeManipulation.setDayStartAndEndTime(dateAndTime, 0, 0, 1, 2);
        } else {
            DateTimeManipulation.setMonthStartAndEndTime(dateAndTime, 0, 0, 1, 2);
        }
    }

    private void calculateRecurrentPeriod() {
        DateTime history;
        DateTime todayE;
        DateTime todayB;
        Duration duration;
        long millis;
        long recCount = -1;
        history = dateAndTime.getDateAndTime().withTimeAtStartOfDay();  // Technically, the call to withTimeAtStartOfDay is not necessary here as Joda-Time defaults to that for parsing a date-only string. But the call is a good habit and makes clear out intention.

        if (isExactTimeSpan && !timeSpan.contains("month")) {
            int monthToAdd = sentenceTense.equals("PAST") ? -12 : 12;
            //history = dateAndTime.getDateAndTime().withTimeAtStartOfDay();  // Technically, the call to withTimeAtStartOfDay is not necessary here as Joda-Time defaults to that for parsing a date-only string. But the call is a good habit and makes clear out intention.
            todayE = dateAndTime.getDateAndTime().plusMonths(monthToAdd).withTimeAtStartOfDay();
            duration = new Duration(history, todayE);
            millis = Math.abs(duration.getMillis());
            if (!dateAndTime.getPreviousDependency().equals("")) {
                recCount = sentenceTense.equals("PAST") ? calculateRecurrentCount(dateAndTime.getTmpStartTime(), dateAndTime.getDateAndTime().withTimeAtStartOfDay(), nthMonthOfYear, 0, true) : calculateRecurrentCount(dateAndTime.getDateAndTime().withTimeAtStartOfDay(), dateAndTime.getTmpEndTime(), nthMonthOfYear, 0, true);
            }

        } else {
            int monthToAdd = sentenceTense.equals("PAST") ? -monthSpan : monthSpan;
            //	history = dateAndTime.getDateAndTime().withTimeAtStartOfDay();  // Technically, the call to withTimeAtStartOfDay is not necessary here as Joda-Time defaults to that for parsing a date-only string. But the call is a good habit and makes clear out intention.
            todayB = dateAndTime.getDateAndTime().plusMonths(monthToAdd).withTimeAtStartOfDay();
            duration = new Duration(history, todayB);
            millis = Math.abs(duration.getMillis());
            if (!dateAndTime.getPreviousDependency().equals("")) {
                recCount = sentenceTense.equals("PAST") ?
                        calculateRecurrentCount(dateAndTime.getTmpEndTime(), dateAndTime.getDateAndTime().withTimeAtStartOfDay(), 0, nthDayOfMonth, false) :
                        (isDatePresent) ? calculateRecurrentCount(dateAndTime.getDateAndTime().withTimeAtStartOfDay(), dateAndTime.getTmpEndTime(), 0, nthDayOfMonth, false) :
                                calculateRecurrentCount(dateAndTime.getDateAndTime().withTimeAtStartOfDay(), dateAndTime.getTmpEndTime(), 0, dateAndTime.getDateAndTime().getDayOfMonth(), false);
            }
        }
        dateAndTime.setMonthRecurrentPeriod(millis);
        if (recCount != -1) {
            dateAndTime.setMonthRecurrentCount(recCount);
        }
    }

    private int calculateRecurrentCount(DateTime start, DateTime end, int monthOfYear, int dayOfMonth, boolean monthOrDay) {
        int count = 0;
        while (start.isBefore(end)) {
            start = start.plusMonths(1);
            if (monthOrDay && start.getMonthOfYear() == monthOfYear) {
                count++;
            } else if (start.getDayOfMonth() == dayOfMonth) {
                count++;
            }
        }
        return count;
    }

    private void exactSpan(int yearsToAdd, int monthOfYear) {
        dateAndTime.setDateAndTime(DateTimeManipulation.setMonth(dateAndTime.getDateAndTime(), yearsToAdd, monthOfYear));
        exactSpan();
    }

    private void exactSpan(String tense) {
        if (tense.equals("PAST")) {
            dateAndTime.setDateAndTime(DateTimeManipulation.recentPastMonth(dateAndTime.getDateAndTime(), nthMonthOfYear));
        } else {
            dateAndTime.setDateAndTime(DateTimeManipulation.recentFutureMonth(dateAndTime.getDateAndTime(), nthMonthOfYear));
        }
        exactSpan();
    }

    @Override
    public void past() {
        if (isExactTimeSpan) {
            if (tenseIndicator.equals("")) {
                exactSpan("PAST"); //No I18N
            } else {
                exactSpan(-1, nthMonthOfYear);
            }
        } else {
            dateAndTime.setDateAndTime(DateTimeManipulation.addMonths(dateAndTime.getDateAndTime(), 0, -monthSpan));
            DateTimeManipulation.setMonthSpanStartAndEndTime(dateAndTime, 0, monthSpan, isImmediate);
        }

    }

    @Override
    public void present() {

        if (isExactTimeSpan) {
            exactSpan("PRESENT"); //No I18N
        } else {
            dateAndTime.setDateAndTime(DateTimeManipulation.addMonths(dateAndTime.getDateAndTime(), 0, implicitPrefix.equals("of") ? monthSpan - 1 : monthSpan));
            DateTimeManipulation.setMonthSpanStartAndEndTime(dateAndTime, -monthSpan, 0, isImmediate);
        }

    }

    @Override
    public void future() {

        if (isExactTimeSpan) {
            if (tenseIndicator.equals("")) {
                exactSpan("FUTURE");  //No I18N
            } else {
                exactSpan(1, nthMonthOfYear);
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
        }
        if (isNumberPresent) {
            if (sentenceTense.equals("PAST")) {
                dateAndTime.setDateAndTime(DateTimeManipulation.addMonths(dateAndTime.getDateAndTime(), 0, -(monthSpan - 1)));
                DateTimeManipulation.setMonthStartAndEndTime(dateAndTime, 0, (monthSpan - 1), 1, 2);
            } else {
                dateAndTime.setDateAndTime(DateTimeManipulation.addMonths(dateAndTime.getDateAndTime(), 0, (monthSpan - 1)));
                DateTimeManipulation.setMonthStartAndEndTime(dateAndTime, -(monthSpan - 1), 0, 1, 2);
            }
        } else {
            monthSpan = monthSpan - 1;
            present();
        }

    }


    @Override
    public void remainder() {

        DateTimeManipulation.setMonthStartAndEndTime(dateAndTime, 0, 0, 0, 2);

    }


    @Override
    public void nthSpan() {
        dateAndTime.setDateAndTime(DateTimeManipulation.setMonth(dateAndTime.getDateAndTime(), 0, nthMonthOfYear));
        DateTimeManipulation.setMonthStartAndEndTime(dateAndTime, 0, 0, 1, 2);
    }


    @Override
    public void setPreviousDependency() {
        if (isDatePresent) {
            exactSpan();
        }

        if (isSet) {
            calculateRecurrentPeriod();
        }


        dateAndTime.setPreviousDependency(Constants.MONTH_SPAN_TAG);

    }


}
