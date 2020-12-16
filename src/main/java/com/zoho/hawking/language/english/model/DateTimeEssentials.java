//$Id$
package com.zoho.hawking.language.english.model;

import edu.stanford.nlp.util.Triple;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

public class DateTimeEssentials {
    private String paragraph;
    private String sentence;
    private String tense;
    private int id = -1;
    private List<Triple<String, Integer, Integer>> triples;
    private boolean isRelation;
    private DateTime referenceTime;
    private String timeZoneOffSet;


    public DateTimeEssentials() {
    }

    public DateTimeEssentials(String paragraph, String sentence, String tense, int id,
        List<Triple<String, Integer, Integer>> triples, boolean isRelation,
        DateTime referenceTime, String timeZoneOffSet) {
        this.paragraph = paragraph;
        this.sentence = sentence;
        this.tense = tense;
        this.id = id;
        this.triples = triples;
        this.isRelation = isRelation;
        this.referenceTime = referenceTime;
        this.timeZoneOffSet = timeZoneOffSet;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public int getId() {
        return id;
    }

    public void addId() {
        this.id++;
    }

    public List<Triple<String, Integer, Integer>> getTriples() {
        return triples;
    }

    public void setTriples(Pair<Boolean, List<Triple<String, Integer, Integer>>> triples) {
        this.isRelation = triples.getLeft();
        this.triples = triples.getRight();
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public boolean isRelation() {
        return isRelation;
    }

    public String getTense() {
        return tense;
    }

    public void setTense(String tense) {
        this.tense = tense;
    }

    public DateTime getReferenceTime() {
        return referenceTime;
    }

    public void setReferenceTime(Date referenceTime) {
        this.referenceTime = new DateTime(referenceTime);
    }

    public String getTimeZoneOffSet() {
        return timeZoneOffSet;
    }

    public void setTimeZoneOffSet(String timeZoneOffSet) {
        this.timeZoneOffSet = timeZoneOffSet;
    }
}
