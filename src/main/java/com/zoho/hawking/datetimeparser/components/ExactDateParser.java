//$Id$
package com.zoho.hawking.datetimeparser.components;

import com.zoho.hawking.datetimeparser.DateAndTime;
import com.zoho.hawking.datetimeparser.constants.ConfigurationConstants;
import com.zoho.hawking.datetimeparser.utils.DateUtil;
import com.zoho.hawking.language.AbstractLanguage;
import com.zoho.hawking.utils.Constants;
import org.joda.time.DateTime;

public class ExactDateParser extends DateTimeComponent {
    public ExactDateParser(String sentenceToParse, String sentenceTense, DateAndTime dateAndTime, AbstractLanguage abstractLanguage) {
        super(sentenceToParse, dateAndTime, sentenceTense, abstractLanguage);
        extractComponentsTags();
        computeNumber();
        findSpanRange();
    }

    @Override
    void extractComponentsTags() {
        if (getTagMap().containsKey(Constants.EXACT_DATE_TAG)) {
            timeSpan = getTagMap().get(Constants.EXACT_DATE_TAG);
        }

    }

    @Override
    void computeNumber() {

    }

    @Override
    public void exactSpan() {
        DateTime localDateTime;
        try {
            String userDateFormat = ConfigurationConstants.configuration.getCustomDate().getDateFormat();
            localDateTime = new DateTime(DateUtil.stringToDate(timeSpan, dateAndTime.getReferenceTime(), userDateFormat));
            dateAndTime.setStart(localDateTime.withTimeAtStartOfDay());
            dateAndTime.setEnd(localDateTime.millisOfDay().withMaximumValue());
        } catch (Exception ignored) {

        }
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
        dateAndTime.setPreviousDependency(Constants.EXACT_DATE_TAG);
    }
}
