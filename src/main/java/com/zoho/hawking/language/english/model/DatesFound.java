//$Id$
package com.zoho.hawking.language.english.model;

import java.util.List;

public class DatesFound {

    private List<DateGroup> dateGroups;

    private List<ParserOutput> parserOutputs;

    /**
     * @return the dateGroups
     */
    public List<DateGroup> getDateGroups() {
        return dateGroups;
    }

    /**
     * @param dateGroups the dateGroups to set
     */
    public void setDateGroups(List<DateGroup> dateGroups) {
        this.dateGroups = dateGroups;
    }

    /**
     * @return the parsedDates
     */
    public List<ParserOutput> getParserOutputs() {
        return parserOutputs;
    }

    /**
     * @param parserOutputs the parsedDates to set
     */
    public void setParserOutputs(List<ParserOutput> parserOutputs) {
        this.parserOutputs = parserOutputs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (parserOutputs != null && dateGroups != null) {
            for (ParserOutput parserOutput : parserOutputs) {
                sb.append("Label : ").append(parserOutput.getParserLabel()).append("\n"); //No I18N
                sb.append("Text : ").append(parserOutput.getText()).append("\n"); //No I18N
                sb.append("IsTimeZonePresent : ").append(parserOutput.getIsTimeZonePresent()).append("\n"); //No I18N
                sb.append("Time Zone : ").append(parserOutput.getTimezoneOffset()).append("\n"); //No I18N
                sb.append("IsExactTimePresent : ").append(parserOutput.getIsExactTimePresent()).append("\n"); //No I18N
                sb.append("Id : ").append(parserOutput.getId()).append("\n"); //No I18N
                sb.append("End Index : ").append(parserOutput.getParserEndIndex()).append("\n"); //No I18N
                sb.append("Start Index : ").append(parserOutput.getParserStartIndex()).append("\n"); //No I18N
                sb.append("Match Type : ").append(parserOutput.getDateRange().getMatchType()).append("\n"); //No I18N
                sb.append("Start : ").append(parserOutput.getDateRange().getStart()).append("\n"); //No I18N
                sb.append("End : ").append(parserOutput.getDateRange().getEnd()).append("\n"); //No I18N
            }

            for (DateGroup dateGroup : dateGroups) {
                sb.append("Sequence :").append(dateGroup.getSequenceType()).append("\n");//No I18N
                sb.append("Expression :").append(dateGroup.getExpression()).append("\n");//No I18N
            }
        }

        return sb.toString();
    }
}
