//$Id$
package com.zoho.hawking.datetimeparser.utils;

import edu.stanford.nlp.util.Triple;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NumberParser {
    public static final Map<String, String> CARDINAL_NUMBERS;
    private static final Logger LOGGER = Logger.getLogger(NumberParser.class.getName());
    private static HashMap<String, String> magnitude;
    private static HashMap<String, Integer> cardinal;
    private static HashMap<String, Integer> ordinal;

    static {
        magnitude = new HashMap<>();
        magnitude.put("thousand", "1000");
        magnitude.put("million", "1000000");
        magnitude.put("billion", "1000000000");
        magnitude.put("hundredth", "100");

        cardinal = new HashMap<>();
        cardinal.put("zero", 0);
        cardinal.put("one", 1);
        cardinal.put("two", 2);
        cardinal.put("three", 3);
        cardinal.put("four", 4);
        cardinal.put("five", 5);
        cardinal.put("six", 6);
        cardinal.put("seven", 7);
        cardinal.put("eight", 8);
        cardinal.put("nine", 9);
        cardinal.put("ten", 10);
        cardinal.put("eleven", 11);
        cardinal.put("twelve", 12);
        cardinal.put("thirteen", 13);
        cardinal.put("fourteen", 14);
        cardinal.put("fifteen", 15);
        cardinal.put("sixteen", 16);
        cardinal.put("seventeen", 17);
        cardinal.put("eighteen", 18);
        cardinal.put("nineteen", 19);
        cardinal.put("twenty", 20);
        cardinal.put("thirty", 30);
        cardinal.put("forty", 40);
        cardinal.put("fifty", 50);
        cardinal.put("sixty", 60);
        cardinal.put("seventy", 70);
        cardinal.put("eighty", 80);
        cardinal.put("ninety", 90);

        ordinal = new HashMap<>();
        ordinal.put("first", 1);
        ordinal.put("second", 2);
        ordinal.put("third", 3);
        ordinal.put("fourth", 4);
        ordinal.put("fifth", 5);
        ordinal.put("sixth", 6);
        ordinal.put("seventh", 7);
        ordinal.put("eighth", 8);
        ordinal.put("ninth", 9);
        ordinal.put("tenth", 10);
        ordinal.put("eleventh", 11);
        ordinal.put("twelfth", 12);
        ordinal.put("thirteenth", 13);
        ordinal.put("fourteenth", 14);
        ordinal.put("fifteenth", 15);
        ordinal.put("sixteenth", 16);
        ordinal.put("seventeenth", 17);
        ordinal.put("eighteenth", 18);
        ordinal.put("nineteenth", 19);
        ordinal.put("twentieth", 20);
        ordinal.put("thirtieth", 30);
        ordinal.put("fourtieth", 40);
        ordinal.put("fiftieth", 50);
        ordinal.put("sixtieth", 60);
        ordinal.put("seventieth", 70);
        ordinal.put("eightieth", 80);
        ordinal.put("ninetieth", 90);
        ordinal.put("one hundredth", 100);


        Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        map.put("First", "1st"); //No I18N
        map.put("Second", "2nd"); //No I18N
        map.put("Third", "3rd"); //No I18N
        map.put("Fourth", "4th"); //No I18N
        map.put("Fifth", "5th"); //No I18N
        map.put("Sixth", "6th"); //No I18N
        map.put("Seventh", "7th"); //No I18N
        map.put("Eighth", "8th"); //No I18N
        map.put("Ninth", "9th"); //No I18N
        map.put("Tenth", "10th"); //No I18N
        map.put("Eleventh", "11th"); //No I18N
        map.put("Twelfth", "12th"); //No I18N
        map.put("Thirteenth", "13th"); //No I18N
        map.put("Fourteenth", "14th"); //No I18N
        map.put("Fifteenth", "15th"); //No I18N
        map.put("Sixteenth", "16th"); //No I18N
        map.put("Seventeenth", "17th"); //No I18N
        map.put("Eighteenth", "18th"); //No I18N
        map.put("Nineteenth", "19th"); //No I18N
        map.put("Twentieth", "20th"); //No I18N
        map.put("Twenty-first", "21st"); //No I18N
        map.put("Twenty-second", "22nd"); //No I18N
        map.put("Twenty-third", "23rd"); //No I18N
        map.put("Twenty-fourth", "24th"); //No I18N
        map.put("Twenty-fifth", "25th"); //No I18N
        map.put("Twenty-sixth", "26th"); //No I18N
        map.put("Twenty-seventh", "27th"); //No I18N
        map.put("Twenty-eighth", "28th"); //No I18N
        map.put("Twenty-ninth", "29th"); //No I18N
        map.put("Thirtieth", "30th"); //No I18N
        map.put("Thirty-first", "31st"); //No I18N
        map.put("Twenty first", "21st"); //No I18N
        map.put("Twenty second", "22nd"); //No I18N
        map.put("Twenty third", "23rd"); //No I18N
        map.put("Twenty fourth", "24th"); //No I18N
        map.put("Twenty fifth", "25th"); //No I18N
        map.put("Twenty sixth", "26th"); //No I18N
        map.put("Twenty seventh", "27th"); //No I18N
        map.put("Twenty eighth", "28th"); //No I18N
        map.put("Twenty ninth", "29th"); //No I18N
        map.put("Thirty first", "31st"); //No I18N
        CARDINAL_NUMBERS = Collections.unmodifiableMap(map);
    }

    public NumberParser() {
    }

    public static Pair<Integer, Boolean> numberParser(String wordToNumber) {
        int number;
        boolean isOrdinal = false;
        Pattern pattern = Pattern.compile("(\\d+)(\\s*)(st|nd|rd|th|ᵗʰ|ˢᵗ|ⁿᵈ|ʳᵈ)");
        Matcher matcher = pattern.matcher(wordToNumber);
        while (matcher.find()) {
            wordToNumber = matcher.group(1);
            isOrdinal = true;
        }
        if (StringUtils.isNumeric(wordToNumber)) {
            number = Integer.parseInt(wordToNumber);
        } else {
            Pair<Integer, Boolean> text2Num = text2Num(wordToNumber);
            number = text2Num.getLeft();
            isOrdinal = text2Num.getRight();
        }

        return Pair.of(number, isOrdinal);
    }

    private static Pair<Integer, Boolean> text2Num(String text) {
        String[] words = text.split("[\\s-]+");
        boolean isOrdinal = false;
        int n = 0;
        int g = 0;
        for (String word : words) {
            Triple<Integer, Integer, Boolean> feachVal = feach(word, isOrdinal, n, g);
            n = feachVal.first;
            g = feachVal.second;
            if (!isOrdinal) {
                isOrdinal = feachVal.third;
            }
        }
        return Pair.of(n + g, isOrdinal);
    }

    private static Triple<Integer, Integer, Boolean> feach(String word, boolean isOrdinal, int n, int g) {
        int x = -1;
        try {
            if (cardinal.containsKey(word)) {
                x = cardinal.get(word);
            } else if (ordinal.containsKey(word)) {
                x = ordinal.get(word);
                isOrdinal = true;
            }
        } catch (NullPointerException nullPointerException) {
            LOGGER.log(Level.SEVERE, "Bad number put into wordToNumber", new StringBuffer());
        }

        if (x != -1) {
            g = g + x;
        } else if (word.equals("hundred")) { //No I18N
            g = g * 100;
        } else {
            if (magnitude.containsKey(word)) {
                n = n + g * x;
                g = 0;
            } else {
                try {
                    throw new Exception("Unknown number: " + word);
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Recognizer :: Error While Loading model", e.getMessage()); //No I18N
                }
            }
        }

        return Triple.makeTriple(n, g, isOrdinal);
    }
}
