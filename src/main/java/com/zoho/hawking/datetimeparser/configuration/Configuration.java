//$Id$
package com.zoho.hawking.datetimeparser.configuration;

import com.zoho.hawking.utils.CommonUtils;

public class Configuration {
    private RangeDefault rangeDefault;
    private SpanDefault spanDefault;
    private WeekDayAndEnd weekDayAndEnd;
    private CustomDate customDate;

    public Configuration(HawkingConfiguration hawkingConfiguration) {
        RangeDefault rangeDefault = new RangeDefault();
        CommonUtils.copy(hawkingConfiguration, rangeDefault);
        SpanDefault spanDefault = new SpanDefault();
        CommonUtils.copy(hawkingConfiguration, spanDefault);
        WeekDayAndEnd weekDayAndEnd = new WeekDayAndEnd();
        CommonUtils.copy(hawkingConfiguration, weekDayAndEnd);
        CustomDate customDate = new CustomDate();
        CommonUtils.copy(hawkingConfiguration, customDate);
        this.setRangeDefault(rangeDefault);
        this.setSpanDefault(spanDefault);
        this.setWeekDayAndEnd(weekDayAndEnd);
        this.setCustomDate(customDate);
    }

    public RangeDefault getRangeDefault() {
        return rangeDefault;
    }

    public void setRangeDefault(RangeDefault rangeDefault) {
        this.rangeDefault = rangeDefault;
    }

    public SpanDefault getSpanDefault() {
        return spanDefault;
    }

    public void setSpanDefault(SpanDefault spanDefault) {
        this.spanDefault = spanDefault;
    }

    public WeekDayAndEnd getWeekDayAndEnd() {
        return weekDayAndEnd;
    }

    public void setWeekDayAndEnd(WeekDayAndEnd weekDayAndEnd) {
        this.weekDayAndEnd = weekDayAndEnd;
    }

    public CustomDate getCustomDate() {
        return customDate;
    }

    public void setCustomDate(CustomDate customDate) {
        this.customDate = customDate;
    }
}
