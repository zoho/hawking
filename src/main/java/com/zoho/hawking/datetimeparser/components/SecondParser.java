//$Id$
package com.zoho.hawking.datetimeparser.components;

import com.zoho.hawking.datetimeparser.DateAndTime;
import com.zoho.hawking.datetimeparser.constants.ConfigurationConstants;
import com.zoho.hawking.language.AbstractLanguage;
import com.zoho.hawking.datetimeparser.utils.DateTimeManipulation;
import com.zoho.hawking.utils.Constants;

public class SecondParser extends DateTimeComponent {

    public SecondParser(String sentenceToParse, String sentenceTense, DateAndTime dateAndTime, AbstractLanguage abstractLanguage) {
        super(sentenceToParse, dateAndTime, sentenceTense, abstractLanguage);
        extractComponentsTags();
        computeNumber();
        findSpanRange();
    }

    @Override
    void extractComponentsTags() {
        if (getTagMap().containsKey(Constants.SECOND_SPAN_TAG)) {
            timeSpan = getTagMap().get(Constants.SECOND_SPAN_TAG);
        }
    }

    @Override
    void computeNumber() {
        if (getTagMap().containsKey(Constants.SECOND_SPAN_TAG)) {
            timeSpan = getTagMap().get(Constants.SECOND_SPAN_TAG);
        }
    }

    @Override
    public void exactSpan() {
        if (!isNumberPresent) {
            if (super.abstractLanguage.secondWords.contains(timeSpan)) {
                number = ConfigurationConstants.getConfiguration().getRangeDefault().getSecond();
            } else if (super.abstractLanguage.secondsWords.contains(timeSpan)) {
                number = ConfigurationConstants.getConfiguration().getRangeDefault().getSeconds();
            }
        }

    }

    @Override
    public void nthSpan() {


    }

    @Override
    public void past() {
        dateAndTime.setDateAndTime(DateTimeManipulation.addSeconds(dateAndTime.getDateAndTime(), -number));
        DateTimeManipulation.setSecondSpanStartAndEnd(dateAndTime, 0, number, isImmediate);

    }

    @Override
    public void present() {
        dateAndTime.setDateAndTime(DateTimeManipulation.addSeconds(dateAndTime.getDateAndTime(), number));
        DateTimeManipulation.setSecondSpanStartAndEnd(dateAndTime, -number, 0, isImmediate);

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

        DateTimeManipulation.setSecondStartAndEndTime(dateAndTime, 0, 0, 1, 2);

    }

    @Override
    public void setPreviousDependency() {
        dateAndTime.setPreviousDependency(Constants.SECOND_SPAN_TAG);
    }

}
