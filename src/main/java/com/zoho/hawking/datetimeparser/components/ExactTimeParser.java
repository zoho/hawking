//$Id$
package com.zoho.hawking.datetimeparser.components;

import com.zoho.hawking.datetimeparser.DateAndTime;
import com.zoho.hawking.language.AbstractLanguage;
import com.zoho.hawking.language.english.DateTimeWordProperties;
import com.zoho.hawking.datetimeparser.utils.DateTimeManipulation;
import com.zoho.hawking.utils.Constants;
import org.joda.time.LocalTime;

public class ExactTimeParser extends DateTimeComponent {
    public ExactTimeParser(String sentenceToParse, String sentenceTense, DateAndTime dateAndTime, AbstractLanguage abstractLanguage) {
        super(sentenceToParse, dateAndTime, sentenceTense, abstractLanguage);
        extractComponentsTags();
        computeNumber();
        findSpanRange();
    }

    private void findExactTime() {
        LocalTime localTime = (LocalTime) DateTimeManipulation.findExactSpan(timeSpan, DateTimeWordProperties.TIME_NORMALIZATION_REGEX, DateTimeWordProperties.TIME_FORMATS, Constants.EXACT_TIME_TAG);
        dateAndTime.setDateAndTime(dateAndTime.getDateAndTime().withTime(localTime));
    }

    @Override
    void extractComponentsTags() {
        if (isNumberPresent && getTagMap().containsKey(Constants.EXACT_TIME_TAG)) {
            timeSpan = number + getTagMap().get(Constants.EXACT_TIME_TAG);
        } else if (getTagMap().containsKey(Constants.EXACT_TIME_TAG)) {
            timeSpan = getTagMap().get(Constants.EXACT_TIME_TAG);
        }
    }

    @Override
    void computeNumber() {

    }

    @Override
    public void exactSpan() {
        findExactTime();
        if (!(dateAndTime.getPreviousDependency().equals(""))) {
            if (sentenceTense.equals("PAST")) {
                dateAndTime.setDateAndTime(DateTimeManipulation.recentPastHour(dateAndTime.getDateAndTime(), dateAndTime.getDateAndTime().getHourOfDay()));

            } else if (sentenceTense.equals("PRESENT") || sentenceTense.equals("FUTURE")) {
                dateAndTime.setDateAndTime(DateTimeManipulation.recentFutureHour(dateAndTime.getDateAndTime(), dateAndTime.getDateAndTime().getHourOfDay()));

            }
        } else {
            if (sentenceTense.equals("PAST")) {
                dateAndTime.setDateAndTime(DateTimeManipulation.exactPastTime(dateAndTime.getDateAndTime(), dateAndTime.getReferenceTime()));

            } else if ((sentenceTense.equals("PRESENT")) || (sentenceTense.equals("FUTURE"))) {
                dateAndTime.setDateAndTime(DateTimeManipulation.exactFutureTime(dateAndTime.getDateAndTime(), dateAndTime.getReferenceTime()));

            }
        }
        dateAndTime.setStart(dateAndTime.getDateAndTime());
        dateAndTime.setEnd(dateAndTime.getDateAndTime());
    }

    @Override
    public void nthSpan() {
    }

    @Override
    public void past() {
        exactSpan();
    }

    @Override
    public void present() {

        exactSpan();

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

        present();

    }

    @Override
    public void remainder() {

        present();

    }

    @Override
    public void setPreviousDependency() {

        dateAndTime.setPreviousDependency(Constants.EXACT_TIME_TAG);
    }

}
