package com.zoho.hawking.language.english.model;

import java.util.Date;

public class DateTimeOffsetReturn {

    private Date referenceDate;
    private String timeOffset;

    public DateTimeOffsetReturn() {
    }

    public DateTimeOffsetReturn(Date referenceDate, String timeOffset) {
        this.referenceDate = new Date(referenceDate.getTime());
        this.timeOffset = timeOffset;
    }

    public Date getReferenceDate() {
        return new Date(referenceDate.getTime());
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = new Date(referenceDate.getTime());
    }

    public String getTimeOffset() {
        return timeOffset;
    }

    public void setTimeOffset(String timeOffset) {
        this.timeOffset = timeOffset;
    }
}
