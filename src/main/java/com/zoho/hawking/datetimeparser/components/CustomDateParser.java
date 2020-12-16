//$Id$
package com.zoho.hawking.datetimeparser.components;

import com.zoho.hawking.datetimeparser.DateAndTime;
import com.zoho.hawking.datetimeparser.constants.ConfigurationConstants;
import com.zoho.hawking.language.AbstractLanguage;
import com.zoho.hawking.datetimeparser.utils.DateTimeManipulation;
import com.zoho.hawking.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Months;

import java.util.HashMap;
import java.util.Map.Entry;

public class CustomDateParser extends DateTimeComponent {

    private String currentCustomDate = "";
    private boolean isCurrent = false;
    private boolean isExactCustom = false;
    private boolean isYear = false;
    /*
     * whatWeDeal -
     * 1 - Quarter
     * 2 - Half*/
    private int whatWeDeal = 0;
    private int noOfMonthsInCustom = 0;
    private HashMap<String, Pair<Integer, Integer>> customDateMonths;

    public CustomDateParser(String sentenceToParse, String sentenceTense, DateAndTime dateAndTime, AbstractLanguage abstractLanguage) {
        super(sentenceToParse, dateAndTime, sentenceTense, abstractLanguage);
        extractComponentsTags();
        computeNumber();
        computeCurrent();
        findSpanRange();
    }

    @Override
    void extractComponentsTags() {

        if (getTagMap().containsKey(Constants.QUARTEROFYEAR)) {
            timeSpan = getTagMap().get(Constants.QUARTEROFYEAR);
            whatWeDeal = 1;
            noOfMonthsInCustom = 3;
        } else if (getTagMap().containsKey(Constants.HALFOFYEAR)) {
            timeSpan = getTagMap().get(Constants.HALFOFYEAR);
            whatWeDeal = 2;
            noOfMonthsInCustom = 6;
        } else if (getTagMap().containsKey(Constants.SET_QUARTEROFYEAR)) {
            timeSpan = getTagMap().get(Constants.SET_QUARTEROFYEAR);
            whatWeDeal = 1;
            noOfMonthsInCustom = 3;
            isSet = true;
            number = 1;
            isNumberPresent = true;
        } else if (getTagMap().containsKey(Constants.CUSTOMYEAR)) {
            timeSpan = getTagMap().get(Constants.CUSTOMYEAR);
            whatWeDeal = 3;
            noOfMonthsInCustom = 12;
            isExactCustom = true;
            isYear = true;
        }

        if (StringUtils.containsAny(timeSpan, "0123456789")) {
            isExactCustom = true;
            number = 1;
            isNumberPresent = true;
        }

    }

    void computeCurrent() {
        switch (whatWeDeal) {
            case 1:
                customDateMonths = ConfigurationConstants.getConfiguration().getCustomDate().getQuarter();
                break;
            case 2:
                customDateMonths = ConfigurationConstants.getConfiguration().getCustomDate().getHalf();
                break;
            case 3:
                customDateMonths = ConfigurationConstants.getConfiguration().getCustomDate().getAnnualYears();
                break;
        }
        int currentMonth = dateAndTime.getDateAndTime().getMonthOfYear();
        for (Entry<String, Pair<Integer, Integer>> customDate : customDateMonths.entrySet()) {
            if (currentMonth <= customDate.getValue().getValue() && currentMonth >= customDate.getValue().getKey()) {
                currentCustomDate = customDate.getKey();
            }
        }
        if (isYear) {
            currentCustomDate = timeSpan;
        }
    }

    @Override
    void computeNumber() {
        if (!isNumberPresent) {
            isNumberPresent = true;
            if (super.abstractLanguage.customDate.contains(timeSpan)) {

                number = ConfigurationConstants.getConfiguration().getRangeDefault().getCustomDate();
            } else if (super.abstractLanguage.customDates.contains(timeSpan)) {
                number = ConfigurationConstants.getConfiguration().getRangeDefault().getCustomDates();
            }
        }
    }

    @Override
    public void exactSpan() {
    }

    private int yearsToAdd(int startMonth) {
        int yearsToAdd;
        if (isYear) {
            yearsToAdd = sentenceTense.equals("PAST") ? -number : number; //No I18N
            yearsToAdd = yearsToAdd == 1 ? yearsToAdd : yearsToAdd - 1;
        } else {
            int currentMonthOfYear = dateAndTime.getDateAndTime().getMonthOfYear();
            yearsToAdd = (ConfigurationConstants.getConfiguration().getCustomDate().getFiscalYearStart() < startMonth && currentMonthOfYear > startMonth) ? 1 : 0;
        }
        return yearsToAdd;
    }


    @Override
    public void nthSpan() {
        String customSpan = currentCustomDate.charAt(0) + "" + number;
        Pair<Integer, Integer> months = customDateMonths.get(customSpan);
        int startMonth = months.getKey();
        int yearsToAdd = yearsToAdd(startMonth);
        dateAndTime.setDateAndTime(DateTimeManipulation.setMonth(dateAndTime.getDateAndTime(), yearsToAdd, startMonth));
        DateTimeManipulation.setMonthStartAndEndTime(dateAndTime, 0, noOfMonthsInCustom - 1, 1, 2);
    }

    @Override
    public void past() {
        String customSpan;
        if (isExactCustom) {
            customSpan = timeSpan;
        } else {
            int modVal = (12 / noOfMonthsInCustom);
            int customSpanNo = ((currentCustomDate.charAt(1) - 1) + modVal) % modVal;
            customSpan = (isCurrent || isExactCustom) ? currentCustomDate : currentCustomDate.charAt(0) + "" + (customSpanNo);
        }
        Pair<Integer, Integer> months = customDateMonths.get(customSpan);
        int endMonth = months.getValue();
        int monthsToAdd = (isExactCustom ? (noOfMonthsInCustom + (12 * (number - 1))) : (noOfMonthsInCustom * number)) - 1;
        int yearsToAdd = yearsToAdd(endMonth);
        if (isExactCustom) {
            if (isYear) {
                dateAndTime.setDateAndTime(DateTimeManipulation.recentPastMonth(dateAndTime.getDateAndTime(), endMonth));
            } else {
                dateAndTime.setDateAndTime(DateTimeManipulation.setMonth(dateAndTime.getDateAndTime(), -yearsToAdd, endMonth));
            }
        } else {
            dateAndTime.setDateAndTime(DateTimeManipulation.recentPastMonth(dateAndTime.getDateAndTime(), endMonth));
        }
        DateTimeManipulation.setMonthStartAndEndTime(dateAndTime, -monthsToAdd, 0, 1, 2);
    }

    @Override
    public void present() {
        String customSpan;
        if (isExactCustom) {
            customSpan = timeSpan;
        } else {
            int modVal = (12 / noOfMonthsInCustom);
            int customSpanNo = ((currentCustomDate.charAt(1) + 1) + modVal) % modVal;
            customSpan = (isCurrent || isExactCustom) ? currentCustomDate : currentCustomDate.charAt(0) + "" + (customSpanNo);
        }
        Pair<Integer, Integer> months = customDateMonths.get(customSpan);
        int startMonth = months.getKey();
        int yearsToAdd = yearsToAdd(startMonth);
        int monthsToAdd = (isExactCustom ? (noOfMonthsInCustom + (12 * (number - 1))) : (noOfMonthsInCustom * number)) - 1;
        if (isExactCustom) {
            dateAndTime.setDateAndTime(DateTimeManipulation.setMonth(dateAndTime.getDateAndTime(), yearsToAdd, startMonth));
        } else {
            dateAndTime.setDateAndTime(DateTimeManipulation.recentFutureMonth(dateAndTime.getDateAndTime(), startMonth));
        }
        DateTimeManipulation.setMonthStartAndEndTime(dateAndTime, 0, monthsToAdd, 1, 2);

    }

    @Override
    public void future() {
        present();
    }

    @Override
    public void immediatePast() {
        past();
    }

    @Override
    public void immediateFuture() {
        present();
    }

    @Override
    public void immediate() {
        isCurrent = true;
        String customSpan;
        if (isExactCustom) {
            customSpan = timeSpan;
        } else {
            customSpan = currentCustomDate;
        }
        Pair<Integer, Integer> months = customDateMonths.get(customSpan);
        int startMonth = months.getKey();
        int endMonth = months.getValue();
        int yearsToAdd = (isYear) ? 0 : yearsToAdd(startMonth);
        int monthsToAdd = (isExactCustom ? (noOfMonthsInCustom + (12 * (number - 1))) : (noOfMonthsInCustom * number)) - 1;


        if (sentenceTense.equals("PAST")) {
            dateAndTime.setDateAndTime(DateTimeManipulation.setMonth(dateAndTime.getDateAndTime(), -yearsToAdd, endMonth));
            DateTimeManipulation.setMonthStartAndEndTime(dateAndTime, -monthsToAdd, 0, 1, 2);
        } else {
            dateAndTime.setDateAndTime(DateTimeManipulation.setMonth(dateAndTime.getDateAndTime(), yearsToAdd, startMonth));
            DateTimeManipulation.setMonthStartAndEndTime(dateAndTime, 0, monthsToAdd, 1, 2);
        }

    }

    @Override
    public void remainder() {
        DateTime startOfCustom = dateAndTime.getDateAndTime();
        int endMonth = customDateMonths.get(currentCustomDate).getValue();
        int yearsToAdd = yearsToAdd(endMonth);
        DateTime endOfCustom = dateAndTime.getDateAndTime().plusYears(yearsToAdd).monthOfYear().setCopy(endMonth);
        DateTimeManipulation.setMonthStartAndEndTime(dateAndTime, startOfCustom, endOfCustom);
    }

    private void calculateRecurrentPeriod() {
        DateTime history;
        DateTime todayE;
        DateTime todayB;
        Duration duration;
        long millis;
        long recCount = -1;
        history = dateAndTime.getDateAndTime().withTimeAtStartOfDay();  // Technically, the call to withTimeAtStartOfDay is not necessary here as Joda-Time defaults to that for parsing a date-only string. But the call is a good habit and makes clear out intention.
        if (isExactCustom) {
            int monthToAdd = sentenceTense.equals("PAST") ? -12 : 12;
            todayE = dateAndTime.getDateAndTime().plusMonths(monthToAdd).withTimeAtStartOfDay();
            duration = new Duration(history, todayE);
            millis = Math.abs(duration.getMillis());
            if (!dateAndTime.getPreviousDependency().equals("")) {
                recCount = sentenceTense.equals("PAST") ?
                        calculateRecurrentCount(dateAndTime.getTmpStartTime(),
                                dateAndTime.getDateAndTime().withTimeAtStartOfDay(), dateAndTime.getDateAndTime().getMonthOfYear()) :
                        calculateRecurrentCount(dateAndTime.getDateAndTime().withTimeAtStartOfDay(),
                                dateAndTime.getTmpEndTime(), dateAndTime.getDateAndTime().getMonthOfYear());
            }

        } else {
            int monthToAdd = sentenceTense.equals("PAST") ? -noOfMonthsInCustom : noOfMonthsInCustom;
            todayB = dateAndTime.getDateAndTime().plusMonths(monthToAdd).withTimeAtStartOfDay();
            duration = new Duration(history, todayB);
            millis = Math.abs(duration.getMillis());
            if (!dateAndTime.getPreviousDependency().equals("")) {
                int remainingMonth = Months.monthsBetween(dateAndTime.getStart(), dateAndTime.getTmpEndTime()).getMonths();
                recCount = remainingMonth / noOfMonthsInCustom;
            }
        }
        dateAndTime.setCustomRecurrentPeriod(millis);
        if (recCount != -1 && recCount != 0) {
            dateAndTime.setCustomRecurrentCount(recCount);
        }
    }

    private int calculateRecurrentCount(DateTime start, DateTime end, int monthOfYear) {
        int count = 0;
        while (start.isBefore(end)) {
            start = start.plusMonths(1);
            if (start.getMonthOfYear() == monthOfYear) {
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
        dateAndTime.setPreviousDependency("CUSTOM_DATE"); //No I18N
    }

}
