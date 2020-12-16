//$Id$
package com.zoho.hawking.language.english.tensepredictor;

public class Tense {
    private String tense;
    private String tenseClass = "";
    private String timeParserClass = "";

    public Tense(String tense) {
        this.tense = tense;
    }

    public Tense(String tense, String tenseClass, String timeParserClass) {
        this.tense = tense;
        this.tenseClass = tenseClass;
        this.timeParserClass = timeParserClass;
    }

    public String getTenseClass() {
        return tenseClass;
    }

    public void setTenseClass(String tenseClass) {
        this.tenseClass = tenseClass;
    }

    public String getTimeParserClass() {
        return this.timeParserClass;
    }

    public void setTimeParserClass(String timeParserClass) {
        this.timeParserClass = timeParserClass;
    }
}
