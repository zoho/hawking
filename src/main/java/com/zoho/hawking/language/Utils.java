package com.zoho.hawking.language;

import com.zoho.hawking.datetimeparser.WordProperty;
import com.zoho.hawking.datetimeparser.constants.DateTimeSpan;
import com.zoho.hawking.datetimeparser.constants.Tense;
import com.zoho.hawking.datetimeparser.constants.WordImplication;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class Utils {

    public static List<String> createDateWithoutAnyOneOfTheRange(WordImplication wordImplication, WordProperty[] allWords) {
        ArrayList<String> wordList = new ArrayList<>();
        for (WordProperty word : allWords) {
            if (word.getWordImplication() != null && word.getWordImplication().getValue() <= wordImplication.getValue() && word.getWordImplication().getValue() >= wordImplication.getValue() && word.isAnyRangeNotApplicable()) {
                wordList.add(word.getWord());
            }
        }
        return Collections.unmodifiableList(wordList);
    }


    public static List<String> createTimeSpanArray(DateTimeSpan start, DateTimeSpan end, WordProperty[] allWords) {
        ArrayList<String> wordList = new ArrayList<>();
        for (WordProperty word : allWords) {
            if (word.getDateTimeSpan() != null && word.getDateTimeSpan().getValue() <= end.getValue() && word.getDateTimeSpan().getValue() >= start.getValue()) {
                wordList.add(word.getWord());
            }
        }
        return Collections.unmodifiableList(wordList);
    }

    public static List<String> createPrefixArray(WordImplication start, WordImplication end, WordProperty[] allWords) {
        ArrayList<String> wordList = new ArrayList<>();
        for (WordProperty word : allWords) {
            if (word.getWordImplication() != null && word.getWordImplication().getValue() <= end.getValue() && word.getWordImplication().getValue() >= start.getValue()) {
                wordList.add(word.getWord());
            }
        }
        return Collections.unmodifiableList(wordList);
    }

    public static List<String> createTenseArray(Tense start, Tense end, WordProperty[] allWords) {
        ArrayList<String> wordList = new ArrayList<>();
        for (WordProperty word : allWords) {
            if (word.getTense() != null && word.getTense().getValue() <= end.getValue() && word.getTense().getValue() >= start.getValue()) {
                wordList.add(word.getWord());
            }
        }
        return Collections.unmodifiableList(wordList);
    }

    public static Map<String, Integer> createMonthOfYear(WordProperty[] allWords){
        HashMap<String, Integer> monthOfYear = new HashMap<>();
        for (WordProperty word : allWords) {
            if (word.getMonthsOfYear() != null) {
                monthOfYear.put(word.getWord(), word.getMonthsOfYear().getValue());
                for(String variation: word.getVariations()){
                    monthOfYear.put(variation, word.getMonthsOfYear().getValue());
                }
            }
        }
        return Collections.unmodifiableMap(monthOfYear);
    }


    public static Map<String, Integer> createDaysOfWeek(WordProperty[] allWords){
        HashMap<String, Integer> monthOfYear = new HashMap<>();
        for (WordProperty word : allWords) {
            if (word.getDaysOfWeek() != null) {
                monthOfYear.put(word.getWord(), word.getDaysOfWeek().getValue());
                for(String variation: word.getVariations()){
                    monthOfYear.put(variation, word.getDaysOfWeek().getValue());
                }
            }
        }
        return Collections.unmodifiableMap(monthOfYear);
    }

    public static Map<String, Pair<Integer, Integer>> createPartOfDay(WordProperty[] allWords) {
        HashMap<String, Pair<Integer, Integer>> partOfDay = new HashMap<>();
        for (WordProperty word : allWords) {
            if (word.getPairOfHoursOfDay() != null) {
                partOfDay.put(word.getWord(), Pair.of(word.getPairOfHoursOfDay().getLeft().getValue(), word.getPairOfHoursOfDay().getRight().getValue()));
                for(String variation: word.getVariations()){
                    partOfDay.put(variation, Pair.of(word.getPairOfHoursOfDay().getLeft().getValue(), word.getPairOfHoursOfDay().getRight().getValue()));
                }
            }
        }
        return Collections.unmodifiableMap(partOfDay);
    }
}
