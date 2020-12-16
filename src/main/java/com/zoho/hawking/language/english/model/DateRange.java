//$Id$
package com.zoho.hawking.language.english.model;


import org.joda.time.DateTime;

public class DateRange {

    private String matchType;
    private DateTime start;
    private DateTime end;
    private String startDateFormat;
    private String endDateFormat;

    public DateRange(String matchType, DateTime start, DateTime end, String startDateFormat, String endDateFormat) {
        this.matchType = matchType;
        this.start = start;
        this.end = end;
        this.startDateFormat = startDateFormat;
        this.endDateFormat = endDateFormat;
    }

    /**
     * @return the startDateFormat
     */

    public String getStartDateFormat() {
        return startDateFormat;
    }

    /**
     * @param startDateFormat the matchType to set
     */

    public void setStartDateFormat(String startDateFormat) {
        this.startDateFormat = startDateFormat;
    }

    /**
     * @return the endDateFormat
     */
    public String getEndDateFormat() {
        return endDateFormat;
    }

    /**
     * @param endDateFormat the matchType to set
     */
    public void setEndDateFormat(String endDateFormat) {
        this.endDateFormat = endDateFormat;
    }

    /**
     * @return the matchType
     */
    public String getMatchType() {
        return matchType;
    }

    /**
     * @param matchType the matchType to set
     */
    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    /**
     * @return the start
     */
    public DateTime getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(DateTime start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public DateTime getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(DateTime end) {
        this.end = end;
    }
}
