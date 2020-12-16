//$Id$
package com.zoho.hawking.datetimeparser.utils;

import com.zoho.hawking.datetimeparser.DateAndTime;
import com.zoho.hawking.language.AbstractLanguage;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrepositionParser {

    /**
     * Used to determine the start and end range of the Time Range
     */
    public static DateAndTime prePositionProcessing(DateAndTime dateAndTime, String primaryPrefix, String tenseIndicator, AbstractLanguage abstractLanguage) {
        if (abstractLanguage.startRange.contains(primaryPrefix) ||
                abstractLanguage.startRange.contains(tenseIndicator)) {
            dateAndTime.setIsEnd(false);
        } else if (abstractLanguage.endRange.contains(primaryPrefix) ||
                abstractLanguage.endRange.contains(tenseIndicator)) {
            dateAndTime.setIsStart(false);
        }

        if (abstractLanguage.wordsWithoutEndRange.contains(primaryPrefix)) {
            dateAndTime.setIsAfter(true);
        }

        if (abstractLanguage.wordsWithoutStartRange.contains(primaryPrefix)) {
            dateAndTime.setIsBefore(true);
        }

        return dateAndTime;
    }

    /*
     * Used to process implicit prefix and postfix
     * it finds fields
     * primaryPrefix - determines the time range
     * secondaryPrefix - prefix that does not affect the time in any way except few
     * tenseIndicator - these words directly influence the tense and the time range
     *
     * */
    public static Triple<String, String, String> prePositionParsing(String implicitPrefix, String implicitPostFix, AbstractLanguage abstractLanguage) {

        List<String> implicitPrefixList = (!implicitPrefix.equals("")) ? Arrays.asList(implicitPrefix.split("\\s+")) : Arrays.asList(implicitPostFix.split("\\s+"));
        String primaryPrefix = findSimilarTokens(abstractLanguage.primaryPrefix, implicitPrefixList);
        //for secondary prefix more than one word is possible so bi gram is done,
        //but in future if more than two word occur trigram and so on need to be done
        List<String> nGram = new ArrayList<>(implicitPrefixList);
        nGram.addAll(nGram(implicitPrefixList, 2));
        String secondaryPrefix = "";//findSimilarTokens(EnglishWordConfiguration.SECONDARY_PREFIX, nGram); //TODO
        String tenseIndicator = findSimilarTokens(abstractLanguage.tenseIndicators, implicitPrefixList);
        if (tenseIndicator.equals("")) {
            if (abstractLanguage.tenseIndicators.contains(primaryPrefix)) {
                tenseIndicator = primaryPrefix;
            }
        }
        return Triple.of(primaryPrefix, secondaryPrefix, tenseIndicator);

    }

    private static String findSimilarTokens(List<String> listElements, List<String> elementsToCompare) {
        String matchedStr = "";
        List<String> comparedList = listElements.stream().filter(elementsToCompare::contains).collect(Collectors.toList());
        if (comparedList.size() != 0) {
            matchedStr = comparedList.get(0);
        }
        return matchedStr;
    }

    /*
     * Gives n gram for all Primitive Data Type
     * 	list - list of primitive data from which nGram needs to computed
     * 	n - to indicate token
     *
     * */
    private static <T> List<List<T>> nGrams(List<T> list, int n) {
        return IntStream.range(0, list.size() - n + 1)
                .mapToObj(i -> new ArrayList<>(list.subList(i, i + n)))
                .collect(Collectors.toList());
    }

    private static List<String> nGram(List<String> list, int i) {
        List<String> strList = new ArrayList<>();
        for (; i > 1; i--) {
            List<List<String>> nGramList = nGrams(list, i);
            for (List<String> li : nGramList) {
                strList.add(String.join(" ", li));
            }
        }
        return strList;
    }
}
