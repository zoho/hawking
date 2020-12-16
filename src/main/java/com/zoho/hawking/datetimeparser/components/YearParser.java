//$Id$
package com.zoho.hawking.datetimeparser.components;

import com.zoho.hawking.datetimeparser.DateAndTime;
import com.zoho.hawking.datetimeparser.constants.ConfigurationConstants;
import com.zoho.hawking.language.AbstractLanguage;
import com.zoho.hawking.datetimeparser.utils.DateTimeManipulation;
import com.zoho.hawking.utils.Constants;
import org.joda.time.DateTime;
import org.joda.time.Duration;

public class YearParser extends DateTimeComponent {

    public YearParser(String sentenceToParse, String sentenceTense, DateAndTime dateAndTime, AbstractLanguage abstractLanguage) {
        super(sentenceToParse, dateAndTime, sentenceTense, abstractLanguage);
        extractComponentsTags();
        computeNumber();
        findSpanRange();
    }

    @Override
    void extractComponentsTags() {
        if (getTagMap().containsKey(Constants.YEAR_SPAN_TAG)) {
            timeSpan = getTagMap().get(Constants.YEAR_SPAN_TAG);
        } else if (getTagMap().containsKey(Constants.EXACT_YEAR_TAG)) {
            timeSpan = getTagMap().get(Constants.EXACT_YEAR_TAG);
            timeSpanValue = Integer.parseInt(timeSpan);
            isExactTimeSpan = true;
        } else if (getTagMap().containsKey(Constants.SET_YEAR_TAG)) {
            timeSpan = getTagMap().get(Constants.SET_YEAR_TAG);
            isSet = true;
        }
    }

    @Override
    void computeNumber() {
        if (!isNumberPresent) {
            if (super.abstractLanguage.yearWords.contains(timeSpan)) {
                number = ConfigurationConstants.getConfiguration().getRangeDefault().getYear();
            } else if (super.abstractLanguage.yearsWords.contains(timeSpan)) {
                number = ConfigurationConstants.getConfiguration().getRangeDefault().getYears();
            }
        }

    }


    @Override
    public void exactSpan() {

        dateAndTime.setDateAndTime(DateTimeManipulation.setYear(dateAndTime.getDateAndTime(), timeSpanValue));
        DateTimeManipulation.setYearStartAndEndTime(dateAndTime, 0, 0, 1, 2);

    }


    @Override
    public void past() {
        if (isExactTimeSpan) {
            exactSpan();
        } else {

            dateAndTime.setDateAndTime(DateTimeManipulation.addYears(dateAndTime.getDateAndTime(), -number));

            if (isSet) {
                calculateRecurrentPeriod(+number);
            }
            DateTimeManipulation.setYearSpanStartAndEndTime(dateAndTime, 0, number, isImmediate);
        }

    }

    @Override
    public void present() {
        if (isExactTimeSpan) {
            exactSpan();
        } else {

            dateAndTime.setDateAndTime(DateTimeManipulation.addYears(dateAndTime.getDateAndTime(), number));
            if (isSet) {
                calculateRecurrentPeriod(+number);
            }
            DateTimeManipulation.setYearSpanStartAndEndTime(dateAndTime, -number, 0, isImmediate);
        }


    }

    @Override
    public void future() {

        if (isExactTimeSpan) {
            exactSpan();
        } else {
            present();
        }

    }

    @Override
    public void immediateFuture() {

        future();

    }

    @Override
    public void immediate() {
        isImmediate = true;
        number = number - 1;
        present();

    }

    @Override
    public void remainder() {

        DateTimeManipulation.setYearStartAndEndTime(dateAndTime, 0, 0, 0, 2);

    }

    @Override
    public void nthSpan() {


    }

    @Override
    public void setPreviousDependency() {
        dateAndTime.setPreviousDependency(Constants.YEAR_SPAN_TAG);
    }

    @Override
    public void immediatePast() {

        past();

    }

    /*
     * Calculate the Recurrent Period for the Year*/
    private void calculateRecurrentPeriod(int yearsToAdd) {
        DateTime history = dateAndTime.getDateAndTime().withTimeAtStartOfDay();  // Technically, the call to withTimeAtStartOfDay is not necessary here as Joda-Time defaults to that for parsing a date-only string. But the call is a good habit and makes clear out intention.
        DateTime today = dateAndTime.getDateAndTime().plusYears(yearsToAdd).withTimeAtStartOfDay();

        Duration duration = new Duration(history, today);
        long millis = Math.abs(duration.getMillis());
        dateAndTime.setYearRecurrentPeriod(millis);
    }


}
