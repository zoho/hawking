package com.zoho.hawking.language.english.tagpredictor;

import com.zoho.hawking.utils.Constants;
import edu.stanford.nlp.util.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TagPredictor {

    public static int getIndexOfPattern(String matchedPattern, String inputTagPattern) {

        int indexOfPattern = inputTagPattern.indexOf(matchedPattern);
        if (indexOfPattern != -1 && indexOfPattern != 0) {
            return inputTagPattern
                    .substring(0, indexOfPattern)
                    .split(Constants.MULTIPLE_SPACE_STRING)
                    .length;
        }
        return 0;
    }

    public static ArrayList<String> getPattern(List<String[]> tagList) {
        ArrayList<String> result = new ArrayList<>();
        for (String[] tripletList : tagList) {
            StringBuilder fullTagPattern = new StringBuilder();
            fullTagPattern.append(tripletList[0]);
            for (int j = 1; j < tripletList.length; j++) {
                fullTagPattern.append(Constants.SPACE_STRING).append(tripletList[j]);
            }
            result.add(fullTagPattern.toString());
        }
        return result;
    }

    public static ArrayList<String> getInputTagList(List<Triple<String, Integer, Integer>> tagList) {
        ArrayList<String> inputTagList = new ArrayList<>();
        for (Triple<String, Integer, Integer> tagTriple : tagList) {
            String eachTag = tagTriple.first;
            inputTagList.add(eachTag);
        }
        return inputTagList;
    }

    public static String getInputTagPattern(ArrayList<String> inputTagList) {
        StringBuilder inputTagPattern = new StringBuilder();
        int tagSize = inputTagList.size();
        for (int i = 0; i < tagSize; i++) {
            String eachTag = inputTagList.get(i);
            if (i == 0) {
                inputTagPattern.append(eachTag);
            } else {
                inputTagPattern.append(Constants.SPACE_STRING).append(eachTag);
            }
        }
        return inputTagPattern.toString();
    }

    public static String getMatchedPattern(String inputTagPattern, List<String> patternList) {
        for (String pattern : patternList) {
            if (inputTagPattern.contains(pattern)) {
                return pattern;
            }
        }
        return null;
    }

    public static String getOutputString(String input, List<Triple<String, Integer, Integer>> tagList,
                                         String matchedPattern, int inputTagCountTillSubstring) {
        StringBuilder finalYearString = new StringBuilder();
        int matchedPatternTagCount = matchedPattern.split(Constants.MULTIPLE_SPACE_STRING).length;
        Triple<String, Integer, Integer> yearTriplet;
        int iterator = 0;
        while (iterator < matchedPatternTagCount) {
            yearTriplet = tagList.get(inputTagCountTillSubstring);
            String initialTag = Constants.START_OPENING_TAG + yearTriplet.first + Constants.END_OPENING_TAG;
            String totalWord = input.substring(yearTriplet.second, yearTriplet.third);
            String finalTag = Constants.START_CLOSING_TAG + yearTriplet.first + Constants.END_CLOSING_TAG;
            finalYearString.append(initialTag).append(totalWord).append(finalTag).append(Constants.SPACE_STRING);
            iterator++;
            inputTagCountTillSubstring++;
        }
        return finalYearString.toString().trim().toLowerCase(Locale.ENGLISH);
    }

    public static String customPredictor(String input, List<Triple<String, Integer, Integer>> tagList,
                                         ArrayList<String> inputTagList, String inputTagPattern) {

        ArrayList<String> customQuarterPattern = getPattern(TagConstants.CUSTOM_QUARTER_LIST);
        ArrayList<String> customHalfYearPattern = getPattern(TagConstants.CUSTOM_HALF_YEAR_LIST);
        ArrayList<String> customYearPattern = getPattern(TagConstants.CUSTOM_YEAR_LIST);


        String matchedPattern;
        if (inputTagList.contains(Constants.QUARTEROFYEAR)) {
            matchedPattern = getMatchedPattern(inputTagPattern, customQuarterPattern);
        } else if (inputTagList.contains(Constants.HALFOFYEAR)) {
            matchedPattern = getMatchedPattern(inputTagPattern, customHalfYearPattern);
        } else if (inputTagList.contains(Constants.CUSTOMYEAR)) {
            matchedPattern = getMatchedPattern(inputTagPattern, customYearPattern);
        } else {
            return null;
        }

        if (matchedPattern != null) {
            int inputTagCountTillSubstring = getIndexOfPattern(matchedPattern, inputTagPattern);
            return getOutputString(input, tagList, matchedPattern, inputTagCountTillSubstring);
        }
        return null;
    }


    public static String dayPredictor(String input, List<Triple<String, Integer, Integer>> tagList,
                                      ArrayList<String> inputTagList, String inputTagPattern) {

        ArrayList<String> daySpanTagPattern = getPattern(TagConstants.DAY_SPAN_LIST);
        ArrayList<String> dayWeekPattern = getPattern(TagConstants.DAY_WEEK_LIST);
        ArrayList<String> currentDayPattern = getPattern(TagConstants.CURRENT_DAY_LIST);
        String matchedPattern;

        if (inputTagList.contains(Constants.DAY_OF_WEEK_TAG)) {
            matchedPattern = getMatchedPattern(inputTagPattern, dayWeekPattern);
        } else if (inputTagList.contains(Constants.DAY_SPAN_TAG) || inputTagList.contains(Constants.SET_DAY_TAG)) {
            matchedPattern = getMatchedPattern(inputTagPattern, daySpanTagPattern);
        } else if (inputTagList.contains(Constants.CURRENT_DAY_TAG)) {
            matchedPattern = getMatchedPattern(inputTagPattern, currentDayPattern);
        } else {
            return null;
        }

        if (matchedPattern != null) {
            int inputTagCountTillSubstring = getIndexOfPattern(matchedPattern, inputTagPattern);
            return getOutputString(input, tagList, matchedPattern, inputTagCountTillSubstring);
        }
        return null;
    }

    public static String datePredictor(String input, List<Triple<String, Integer, Integer>> tagList,
                                       ArrayList<String> inputTagList, String inputTagPattern) {

        ArrayList<String> exactDatePattern = getPattern(TagConstants.EXACT_DATE_LIST);

        String matchedPattern;

        if (inputTagList.contains(Constants.EXACT_DATE_TAG)) {
            matchedPattern = getMatchedPattern(inputTagPattern, exactDatePattern);
        } else {
            return null;
        }

        if (matchedPattern != null) {
            int inputTagCountTillSubstring = getIndexOfPattern(matchedPattern, inputTagPattern);
            return getOutputString(input, tagList, matchedPattern, inputTagCountTillSubstring);
        }
        return null;
    }


    public static String exactTimePredictor(String input, List<Triple<String, Integer, Integer>> tagList,
                                            ArrayList<String> inputTagList, String inputTagPattern) {

        ArrayList<String> exactTimePattern = getPattern(TagConstants.EXACT_TIME_LIST);

        String matchedPattern;
        if (inputTagList.contains(Constants.EXACT_TIME_TAG)) {
            matchedPattern = getMatchedPattern(inputTagPattern, exactTimePattern);
        } else {
            return null;
        }

        if (matchedPattern != null) {
            int inputTagCountTillSubstring = getIndexOfPattern(matchedPattern, inputTagPattern);
            return getOutputString(input, tagList, matchedPattern, inputTagCountTillSubstring);
        }
        return null;
    }


    public static String hourPredictor(String input, List<Triple<String, Integer, Integer>> tagList,
                                       ArrayList<String> inputTagList, String inputTagPattern) {

        ArrayList<String> hourSpanPattern = getPattern(TagConstants.HOUR_SPAN_LIST);
        ArrayList<String> partOfDayPattern = getPattern(TagConstants.PART_OF_DAY_LIST);
        String matchedPattern;

        if (inputTagList.contains(Constants.HOUR_SPAN_TAG) || inputTagList.contains(Constants.SET_HOUR_TAG)) {
            matchedPattern = getMatchedPattern(inputTagPattern, hourSpanPattern);
        } else if (inputTagList.contains(Constants.PART_OF_DAY_TAG)) {
            matchedPattern = getMatchedPattern(inputTagPattern, partOfDayPattern);
        } else {
            return null;
        }

        if (matchedPattern != null) {
            int inputTagCountTillSubstring = getIndexOfPattern(matchedPattern, inputTagPattern);
            return getOutputString(input, tagList, matchedPattern, inputTagCountTillSubstring);
        }
        return null;
    }

    public static String minutePredictor(String input, List<Triple<String, Integer, Integer>> tagList,
                                         ArrayList<String> inputTagList, String inputTagPattern) {

        ArrayList<String> minutePatterns = getPattern(TagConstants.MINUTE_SPAN_LIST);
        String matchedPattern;

        if (inputTagList.contains(Constants.MINUTE_SPAN_TAG) || inputTagList.contains(Constants.SET_MINUTE_TAG)) {
            matchedPattern = getMatchedPattern(inputTagPattern, minutePatterns);
        } else {
            return null;
        }

        if (matchedPattern != null) {
            int inputTagCountTillSubstring = getIndexOfPattern(matchedPattern, inputTagPattern);
            return getOutputString(input, tagList, matchedPattern, inputTagCountTillSubstring);
        }
        return null;
    }

    public static String monthPredictor(String input, List<Triple<String, Integer, Integer>> tagList,
                                        ArrayList<String> inputTagList, String inputTagPattern) {

        ArrayList<String> monthOfYearSpanPatterns = getPattern(TagConstants.MONTH_OF_YEAR_SPAN_LIST);
        ArrayList<String> monthSpanPatterns = getPattern(TagConstants.MONTH_SPAN_LIST);

        String matchedPattern;

        if (inputTagList.contains(Constants.MONTH_OF_YEAR_TAG)) {
            matchedPattern = getMatchedPattern(inputTagPattern, monthOfYearSpanPatterns);
        } else if (inputTagList.contains(Constants.MONTH_SPAN_TAG) ||
                inputTagList.contains(Constants.SET_MONTH_TAG)) {
            matchedPattern = getMatchedPattern(inputTagPattern, monthSpanPatterns);
        } else {
            return null;
        }

        if (matchedPattern != null) {
            int inputTagCountTillSubstring = getIndexOfPattern(matchedPattern, inputTagPattern);
            return getOutputString(input, tagList, matchedPattern, inputTagCountTillSubstring);
        }
        return null;
    }

    public static String secondPredictor(String input, List<Triple<String, Integer, Integer>> tagList,
                                         ArrayList<String> inputTagList, String inputTagPattern) {

        ArrayList<String> secondPatterns = getPattern(TagConstants.SECOND_SPAN_LIST);

        String matchedPattern;

        if (inputTagList.contains(Constants.SECOND_SPAN_TAG) || inputTagList.contains(Constants.SET_SECOND_TAG)) {
            matchedPattern = getMatchedPattern(inputTagPattern, secondPatterns);
        } else {
            return null;
        }

        if (matchedPattern != null) {
            int inputTagCountTillSubstring = getIndexOfPattern(matchedPattern, inputTagPattern);
            return getOutputString(input, tagList, matchedPattern, inputTagCountTillSubstring);
        }
        return null;
    }

    public static String weekPredictor(String input, List<Triple<String, Integer, Integer>> tagList,
                                       ArrayList<String> inputTagList, String inputTagPattern) {
        ArrayList<String> weekPatterns = getPattern(TagConstants.WEEK_SPAN_LIST);
        String matchedPattern;

        if (inputTagList.contains(Constants.WEEK_SPAN_TAG) || inputTagList.contains(Constants.SET_WEEK_TAG)) {
            matchedPattern = getMatchedPattern(inputTagPattern, weekPatterns);
        } else {
            return null;
        }

        if (matchedPattern != null) {
            int inputTagCountTillSubstring = getIndexOfPattern(matchedPattern, inputTagPattern);
            return getOutputString(input, tagList, matchedPattern, inputTagCountTillSubstring);
        }
        return null;
    }

    public static String yearPredictor(String input, List<Triple<String, Integer, Integer>> tagList,
                                       ArrayList<String> inputTagList, String inputTagPattern) {
        ArrayList<String> exactYearPatterns = getPattern(TagConstants.EXACT_YEAR_SPAN_LIST);
        ArrayList<String> yearSpanPatterns = getPattern(TagConstants.YEAR_SPAN_LIST);

        String matchedPattern;

        if (inputTagList.contains(Constants.EXACT_YEAR_TAG)) {
            matchedPattern = getMatchedPattern(inputTagPattern, exactYearPatterns);
        } else if (inputTagList.contains(Constants.YEAR_SPAN_TAG) ||
                inputTagList.contains(Constants.SET_YEAR_TAG)) {
            matchedPattern = getMatchedPattern(inputTagPattern, yearSpanPatterns);
        } else {
            return null;
        }

        if (matchedPattern != null) {
            int inputTagCountTillSubstring = getIndexOfPattern(matchedPattern, inputTagPattern);
            return getOutputString(input, tagList, matchedPattern, inputTagCountTillSubstring);
        }
        return null;
    }
}
