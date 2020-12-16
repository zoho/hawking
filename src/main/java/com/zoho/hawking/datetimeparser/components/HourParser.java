//$Id$
package com.zoho.hawking.datetimeparser.components;

import com.zoho.hawking.datetimeparser.DateAndTime;
import com.zoho.hawking.datetimeparser.constants.ConfigurationConstants;
import com.zoho.hawking.language.AbstractLanguage;
import com.zoho.hawking.language.english.DateTimeWordProperties;
import com.zoho.hawking.datetimeparser.utils.DateTimeManipulation;
import com.zoho.hawking.utils.Constants;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalTime;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HourParser extends DateTimeComponent {
    private Pair<Integer, Integer> hourPair;
    private DateTime startTime;
    private DateTime endTime;
    private boolean isExactTime;

    public HourParser(String sentenceToParse, String sentenceTense, DateAndTime dateAndTime, AbstractLanguage abstractLanguage) {
        super(sentenceToParse, dateAndTime, sentenceTense, abstractLanguage);
        extractComponentsTags();
        computeNumber();
        findSpanRange();
    }

    @Override
    void extractComponentsTags() {
        if (getTagMap().containsKey(Constants.HOUR_SPAN_TAG)) {
            timeSpan = getTagMap().get(Constants.HOUR_SPAN_TAG);
        } else if (getTagMap().containsKey(Constants.PART_OF_DAY_TAG)) {
            timeSpan = getTagMap().get(Constants.PART_OF_DAY_TAG);
            hourPair = super.abstractLanguage.partsOfDay.get(timeSpan);
            isExactTimeSpan = true;
        } else if (getTagMap().containsKey(Constants.SET_HOUR_TAG)) {
            timeSpan = getTagMap().get(Constants.SET_HOUR_TAG);
            isSet = true;
        }

        if (getTagMap().containsKey(Constants.EXACT_TIME_TAG) ||
                (isExactTimeSpan && isNumberPresent)) {
            isExactTime = true;
        }

    }

    private int findTimeConvention(String partOfDay, int hourOfDay) {
        int localHourOfDay = hourOfDay;
        List<String> postMeridian = super.abstractLanguage.partsOfDay.entrySet().stream()
                .filter(x -> x.getValue().getLeft() > 12)
                .map(Map.Entry::getKey).collect(Collectors.toList());

        if (hourOfDay <= 12 && hourOfDay >= 1) {
            if (postMeridian.contains(partOfDay)) {
                localHourOfDay += 12;
            }
        }
        return localHourOfDay % 24;
    }

    @Override
    void computeNumber() {
        if (!isNumberPresent) {
            if (super.abstractLanguage.hourWords.contains(timeSpan)) {
                number = ConfigurationConstants.getConfiguration().getRangeDefault().getHour();
            } else if (super.abstractLanguage.hoursWords.contains(timeSpan)) {
                number = ConfigurationConstants.getConfiguration().getRangeDefault().getHours();
            }
        }

    }

    @Override
    public void exactSpan() {

        if (isExactTime) {
            String localTimeValue = (isNumberPresent) ? Integer.toString(number) : getTagMap().get(Constants.EXACT_TIME_TAG);
            LocalTime localTime = (LocalTime) DateTimeManipulation.findExactSpan(localTimeValue,
                    DateTimeWordProperties.TIME_NORMALIZATION_REGEX,
                    DateTimeWordProperties.TIME_FORMATS, Constants.EXACT_TIME_TAG);
            if (!localTimeValue.contains("am") || !localTimeValue.contains("pm")) {
                localTime = localTime.hourOfDay().setCopy(findTimeConvention(timeSpan, localTime.getHourOfDay()));
            }
            dateAndTime.setDateAndTime(dateAndTime.getDateAndTime().withTime(localTime));
            dateAndTime.setStart(dateAndTime.getDateAndTime());
            dateAndTime.setEnd(dateAndTime.getDateAndTime());
        } else {
            startTime = dateAndTime.getDateAndTime();
            endTime = DateTimeManipulation.nthHourDay(startTime, 0, hourPair.getRight());
            endTime = (hourPair.getLeft() == hourPair.getRight()) ? endTime : endTime.minusHours(1);
            DateTimeManipulation.setHourStartAndEndTime(dateAndTime, startTime, endTime);
        }

    }

    private void exactSpan(int hoursAdd, int nthHourOfDay) {
        dateAndTime.setDateAndTime(DateTimeManipulation.nthHourDay(dateAndTime.getDateAndTime(), hoursAdd, nthHourOfDay));
        exactSpan();
    }

    private void exactSpan(String tense) {
        if (tense.equals("PAST")) {
            dateAndTime.setDateAndTime(DateTimeManipulation.recentPastHour(dateAndTime.getDateAndTime(), hourPair.getLeft()));
        } else {
            dateAndTime.setDateAndTime(DateTimeManipulation.recentFutureHour(dateAndTime.getDateAndTime(), hourPair.getLeft()));
        }
        exactSpan();
    }

    @Override
    public void nthSpan() {
        dateAndTime.setDateAndTime(DateTimeManipulation.nthHourDay(dateAndTime.getDateAndTime(), 0, number));
        DateTimeManipulation.setHourStartAndEndTime(dateAndTime, 0, 0, 1, 2);

    }

    @Override
    public void past() {
        if (isExactTimeSpan) {
            if (tenseIndicator.equals("")) {
                exactSpan("PAST"); //No I18N
            } else {
                exactSpan(-24, hourPair.getLeft());
            }
        } else {
            dateAndTime.setDateAndTime(DateTimeManipulation.addHours(dateAndTime.getDateAndTime(), -number));
            DateTimeManipulation.setHourSpanStartAndEndTime(dateAndTime, 0, number, isImmediate);
        }

    }

    @Override
    public void present() {

        if (isExactTimeSpan) {
            if (tenseIndicator.equals("")) {
                exactSpan("PRESENT"); //No I18N
            } else {
                exactSpan(24, hourPair.getLeft());
            }
        } else {
            dateAndTime.setDateAndTime(DateTimeManipulation.addHours(dateAndTime.getDateAndTime(), number));
            DateTimeManipulation.setHourSpanStartAndEndTime(dateAndTime, -number, 0, isImmediate);
        }

    }

    @Override
    public void future() {

        present();

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
    public void immediateFuture() {

        if (isExactTimeSpan) {
            exactSpan("FUTURE"); //No I18N
        } else {
            future();
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
        if (isExactTimeSpan) {
            startTime = dateAndTime.getDateAndTime();
            endTime = DateTimeManipulation.nthHourDay(startTime, 0, hourPair.getRight());
            endTime = (hourPair.getLeft() == hourPair.getRight()) ? endTime : endTime.minusHours(1);
            DateTimeManipulation.setHourStartAndEndTime(dateAndTime, startTime, endTime);
        } else {
            DateTimeManipulation.setHourStartAndEndTime(dateAndTime, 0, 0, 0, 2);
        }

    }

    private void calculateRecurrentPeriod() {
        DateTime history;
        DateTime today;
        Duration duration;
        long millis;
        long recCount = -1;
        history = dateAndTime.getDateAndTime().hourOfDay().roundFloorCopy();

        today = isExactTimeSpan ? dateAndTime.getDateAndTime().plusHours(24).withTimeAtStartOfDay() :
                dateAndTime.getDateAndTime().plusHours(1).hourOfDay().roundFloorCopy();
        duration = new Duration(history, today);
        millis = Math.abs(duration.getMillis());
        if (!dateAndTime.getPreviousDependency().equals("")) {
            if (isExactTimeSpan) {
                recCount = sentenceTense.equals("PAST") ? calculateRecurrentCount(dateAndTime.getTmpStartTime(), dateAndTime.getDateAndTime().withTimeAtStartOfDay(), dateAndTime.getStart().getHourOfDay())
                        : calculateRecurrentCount(dateAndTime.getDateAndTime().withTimeAtStartOfDay(), dateAndTime.getTmpEndTime(), dateAndTime.getStart().getHourOfDay());
            } else {
                recCount = 1;
            }
        }
        dateAndTime.setHourRecurrentPeriod(millis);
        if (recCount != -1) {
            dateAndTime.setHourRecurrentCount(recCount);
        }
    }

    private int calculateRecurrentCount(DateTime start, DateTime end, int dayOfSpan) {
        int count = 0;
        while (start.isBefore(end)) {
            start = start.plusHours(1);
            if (start.getHourOfDay() == dayOfSpan) {
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
        dateAndTime.setPreviousDependency(Constants.HOUR_SPAN_TAG);
    }

}
