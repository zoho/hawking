package com.zoho.hawking.language.english.tagpredictor;

import com.zoho.hawking.utils.Constants;
import edu.stanford.nlp.util.Triple;

import java.util.List;
import java.util.Map;

public class TagUtils {

    public static Map<String, String> tagRegulator(String input, List<Triple<String, Integer, Integer>> tagList,
                                                   Map<String, String> tagsEach) {
        int tagSize = tagList.size();
        for (int i = 1; i < tagSize; i++) {
            Triple<String, Integer, Integer> currentTag = tagList.get(i);
            Triple<String, Integer, Integer> prevTag = tagList.get(i - 1);
            Triple<String, Integer, Integer> nextTag;
            if (i < tagList.size() - 1) {
                nextTag = tagList.get(i + 1);
            } else {
                return tagsEach;
            }
            String eachTag = currentTag.first;
            String prevEachTag = prevTag.first;
            String nextEachTag = nextTag.first;

            boolean currentNumber = eachTag.equals(Constants.NUMBER_TAG)
                    && prevEachTag.equals(Constants.MONTH_OF_YEAR_TAG);
            if (currentNumber && (nextEachTag.equals(Constants.DAY_SPAN_TAG)
                    || nextEachTag.equals(Constants.WEEK_SPAN_TAG))) {
                String number = input.substring(currentTag.second, currentTag.third);
                String numberTag = Constants.START_OPENING_TAG + Constants.NUMBER_TAG + Constants.END_OPENING_TAG +
                        number + Constants.START_CLOSING_TAG + Constants.NUMBER_TAG + Constants.END_CLOSING_TAG;
                String monthTagList = tagsEach.get("month"); // no I18N
                String updateMonthList = monthTagList.replace(numberTag, ""); // no I18N
                tagsEach.replace("month", updateMonthList); // no I18N
                break;
            } else if (currentNumber && nextEachTag.equals(Constants.EXACT_TIME_TAG)) {
                String number = input.substring(currentTag.second, currentTag.third);
                String numberTag = Constants.START_OPENING_TAG + Constants.NUMBER_TAG + Constants.END_OPENING_TAG +
                        number + Constants.START_CLOSING_TAG + Constants.NUMBER_TAG + Constants.END_CLOSING_TAG;
                if (i > 1) {
                    Triple<String, Integer, Integer> tagTriplePrevp = tagList.get(i - 2);
                    String prevEachTagg = tagTriplePrevp.first;
                    if (prevEachTagg.equals(Constants.NUMBER_TAG)) {
                        String monthTagList = tagsEach.get("month"); // no I18N
                        String updateMonthList = monthTagList.replace(numberTag, ""); // no I18N
                        tagsEach.replace("month", updateMonthList); // no I18N
                        break;
                    } else {
                        String timeTagList = tagsEach.get("time");  // no I18N
                        String updateTimeList = timeTagList.replace(numberTag, ""); // no I18N
                        tagsEach.replace("time", updateTimeList); // no I18N
                    }
                } else {
                    String timeTagList = tagsEach.get("time");  // no I18N
                    String updateTimeList = timeTagList.replace(numberTag, ""); // no I18N
                    tagsEach.replace("time", updateTimeList); // no I18N
                }
            }
        }
        return tagsEach;
    }
}
