//$Id$
package com.zoho.hawking.datetimeparser.configuration;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;

public class CustomDate {

    private int fiscalYearStart;
    private int fiscalYearEnd;
    private String dateFormat;

    private static HashMap<String, Pair<Integer, Integer>> createCustomDate(int customDateMonthStart, int monthIncrement, String symbol) {
        int noOfCustomDates = 12 / monthIncrement;
        HashMap<String, Pair<Integer, Integer>> customDateMap = new HashMap<>();
        for (int i = 0; i < noOfCustomDates; i++) {
            int endMonth = (customDateMonthStart + (monthIncrement - 1)) % 12;
            if (endMonth == 0) {
                endMonth = 12;
            }
            customDateMap.put(symbol + (i + 1), Pair.of(customDateMonthStart, endMonth));
            customDateMonthStart = (endMonth + 1) % 12;
            if (customDateMonthStart == 0) {
                customDateMonthStart = 12;
            }
        }
        return customDateMap;
    }

    public int getFiscalYearStart() {
        return fiscalYearStart;
    }

    public void setFiscalYearStart(int fiscalYearStart) {
        this.fiscalYearStart = fiscalYearStart;
    }

    public int getFiscalYearEnd() {
        return fiscalYearEnd;
    }

    public void setFiscalYearEnd(int fiscalYearEnd) {
        this.fiscalYearEnd = fiscalYearEnd;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public HashMap<String, Pair<Integer, Integer>> getQuarter() {
        return createCustomDate(fiscalYearStart, 3, "q"); //No I18N
    }

    public HashMap<String, Pair<Integer, Integer>> getHalf() {
        return createCustomDate(fiscalYearStart, 6, "h"); //No I18N
    }

    public HashMap<String, Pair<Integer, Integer>> getAnnualYears() {
        HashMap<String, Pair<Integer, Integer>> annualYears = new HashMap<>();
        annualYears.put("annualyear", Pair.of(fiscalYearStart, fiscalYearEnd));
        annualYears.put("fiscalyear", Pair.of(fiscalYearStart, fiscalYearEnd));
        annualYears.put("annualyears", Pair.of(fiscalYearStart, fiscalYearEnd));
        annualYears.put("fiscalyears", Pair.of(fiscalYearStart, fiscalYearEnd));
        return annualYears;
    }
}
