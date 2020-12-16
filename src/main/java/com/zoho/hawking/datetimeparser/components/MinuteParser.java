//$Id$
package com.zoho.hawking.datetimeparser.components;

import com.zoho.hawking.datetimeparser.DateAndTime;
import com.zoho.hawking.datetimeparser.constants.ConfigurationConstants;
import com.zoho.hawking.language.AbstractLanguage;
import com.zoho.hawking.datetimeparser.utils.DateTimeManipulation;
import com.zoho.hawking.utils.Constants;

public class MinuteParser extends DateTimeComponent {

    public MinuteParser(String sentenceToParse, String sentenceTense, DateAndTime dateAndTime, AbstractLanguage abstractLanguage) {
        super(sentenceToParse, dateAndTime, sentenceTense, abstractLanguage);
        extractComponentsTags();
        computeNumber();
        findSpanRange();
    }

    @Override
    void extractComponentsTags() {
        if (getTagMap().containsKey(Constants.MINUTE_SPAN_TAG)) {
            timeSpan = getTagMap().get(Constants.MINUTE_SPAN_TAG);
        }
    }

    @Override
    void computeNumber() {
        if (!isNumberPresent) {
            if (super.abstractLanguage.minuteWords.contains(timeSpan)) {
                number = ConfigurationConstants.getConfiguration().getRangeDefault().getMinute();
            } else if (super.abstractLanguage.minutesWords.contains(timeSpan)) {
                number = ConfigurationConstants.getConfiguration().getRangeDefault().getMinutes();
            }
        }

    }

    @Override
    public void exactSpan() {

    }

    @Override
    public void nthSpan() {


    }

    @Override
    public void past() {

        dateAndTime.setDateAndTime(DateTimeManipulation.addMinutes(dateAndTime.getDateAndTime(), -number));
        DateTimeManipulation.setMinuteSpanStartAndEnd(dateAndTime, 0, number, isImmediate);

    }

    @Override
    public void present() {

        dateAndTime.setDateAndTime(DateTimeManipulation.addMinutes(dateAndTime.getDateAndTime(), number));
        DateTimeManipulation.setMinuteSpanStartAndEnd(dateAndTime, -number, 0, isImmediate);

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

        DateTimeManipulation.setMinuteStartAndEndTime(dateAndTime, 0, 0, 1, 2);

    }

    @Override
    public void setPreviousDependency() {
        dateAndTime.setPreviousDependency(Constants.MINUTE_SPAN_TAG);
    }

}
