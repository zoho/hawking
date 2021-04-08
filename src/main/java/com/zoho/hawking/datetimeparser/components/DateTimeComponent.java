//$Id$
package com.zoho.hawking.datetimeparser.components;

import com.zoho.hawking.datetimeparser.DateAndTime;
import com.zoho.hawking.datetimeparser.utils.NumberParser;
import com.zoho.hawking.datetimeparser.utils.PrepositionParser;
import com.zoho.hawking.datetimeparser.utils.TagParser;
import com.zoho.hawking.language.AbstractLanguage;
import com.zoho.hawking.utils.Constants;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DateTimeComponent {

    private final static Logger LOGGER = Logger.getLogger(DateTimeComponent.class.getName());
    public String timeSpan = "";
    public String tenseIndicator = "";
    public String sentenceTense;
    public boolean isExactTimeSpan;
    public boolean isOrdinal;
    public boolean isImmediate;
    protected String implicitPrefix = "";
    DateAndTime dateAndTime;
    String exactNumber = "";
    String primaryPrefix = "";
    String secondaryPrefix = "";
    String sentenceToParse;
    boolean isNumberPresent = false;
    boolean isSet = false;
    Integer number;
    int timeSpanValue;
    private String implicitPostfix = "";
    private HashMap<String, String> mapTags;
    AbstractLanguage abstractLanguage;

    public  DateTimeComponent(String sentenceToParse, DateAndTime dateAndTime, String sentenceTense, AbstractLanguage abstractLanguage) {
        this.abstractLanguage = abstractLanguage;
        setTags(sentenceToParse);
        findPrefixAndTenseIndicator();
        prefixProcessing();

        this.dateAndTime = PrepositionParser.prePositionProcessing(dateAndTime, primaryPrefix, tenseIndicator, abstractLanguage);
        this.sentenceTense = sentenceTense;
        this.sentenceToParse = sentenceToParse;
        setReferenceDateTime();
    }

    private void setReferenceDateTime() {
        if (dateAndTime.getPreviousDependency() != null && dateAndTime.getStart() != null) {
            dateAndTime.setDateAndTime(dateAndTime.getStart());
        }
    }

    abstract void extractComponentsTags();

    abstract void computeNumber();

    public abstract void exactSpan();

    public abstract void nthSpan();

    public abstract void past();

    public abstract void present();

    public abstract void future();

    public abstract void immediatePast();

    public abstract void immediateFuture();

    public abstract void immediate();

    public abstract void remainder();

    public abstract void setPreviousDependency();

    private void setTags(String sentenceToParse) {
        mapTags = TagParser.tagParser(sentenceToParse);
        if (mapTags.containsKey(Constants.PREFIX_TAG)) {
            implicitPrefix = mapTags.get(Constants.PREFIX_TAG);
        }

        if (mapTags.containsKey(Constants.POSTFIX_TAG)) {
            implicitPostfix = mapTags.get(Constants.POSTFIX_TAG);
        }

        if (mapTags.containsKey(Constants.NUMBER_TAG)) {
            exactNumber = mapTags.get(Constants.NUMBER_TAG);
            parseNumber(exactNumber);
        }

        if (mapTags.containsKey(Constants.SET_PREFIX_TAG)) {
            isSet = true;
        }
    }


    private void parseNumber(String wordToNumber) {
        Pair<Integer, Boolean> numberPair;
        try {
            numberPair = NumberParser.numberParser(wordToNumber);
            number = numberPair.getLeft();
            isOrdinal = numberPair.getRight();
            isNumberPresent = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Recognizer :: Error While Loading model", e.getMessage()); //No I18N
        }

    }

    private void findPrefixAndTenseIndicator() {
        Triple<String, String, String> prefixAndTenseIndicator = PrepositionParser.prePositionParsing(implicitPrefix, implicitPostfix, this.abstractLanguage);
        primaryPrefix = prefixAndTenseIndicator.getLeft();
        secondaryPrefix = prefixAndTenseIndicator.getMiddle();
        tenseIndicator = prefixAndTenseIndicator.getRight();
    }


    private void prefixProcessing() {
        //TODO
//        if (PrepositionConstants.FEW.getWord().equals(secondaryPrefix)) {
//            number = ConfigurationConstants.getConfiguration().getRangeDefault().getFew();
//            isNumberPresent = true;
//        }
    }

    HashMap<String, String> getTagMap() {
        return mapTags;
    }

    void findSpanRange() {
//        if (!dateAndTime.getPreviousDependency().equals("")) {
//            sentenceTense = "PRESENT"; //No I18N
//            tenseIndicator = "";
//        }
    }


}
