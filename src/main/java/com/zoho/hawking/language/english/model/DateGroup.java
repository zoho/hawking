//$Id$
package com.zoho.hawking.language.english.model;

public class DateGroup {

    private String sequenceType = "SINGLE"; //No I18N

    private RepeatCount repeatCount;

    private RepeatPeriod repeatPeriod;

    private String expression;

    public DateGroup() {

    }

    /**
     * @return the sequenceType
     */
    public String getSequenceType() {
        return sequenceType;
    }

    /**
     * @param sequenceType the sequenceType to set
     */
    public void setSequenceType(String sequenceType) {
        this.sequenceType = sequenceType;
    }

    /**
     * @return the recurrentCount
     */
    public RepeatCount getRecurrentCount() {
        return repeatCount;
    }

    /**
     * @param recurrentCount the recurrentCount to set
     */
    public void setRecurrentCount(RepeatCount recurrentCount) {
        this.repeatCount = recurrentCount;
    }

    /**
     * @return the recurrentPeriod
     */
    public RepeatPeriod getRecurrentPeriod() {
        return repeatPeriod;
    }

    /**
     * @param recurrentPeriod the recurrentPeriod to set
     */
    public void setRecurrentPeriod(RepeatPeriod recurrentPeriod) {
        this.repeatPeriod = recurrentPeriod;
    }

    /**
     * @return the expression
     */
    public String getExpression() {
        return expression;
    }

    /**
     * @param expression the expression to set
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }

}
