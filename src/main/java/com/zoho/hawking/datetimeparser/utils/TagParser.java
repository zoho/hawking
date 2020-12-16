//$Id$
package com.zoho.hawking.datetimeparser.utils;

import com.zoho.hawking.utils.Constants;

import java.util.HashMap;

public class TagParser {

    /*
     * Parse all the tags present in a sentence
     *
     *  @param sentenceToParseTag
     *  			sentence with the tag <implict_prefix>,<exact_number> etc
     *
     *
     *  @return A {@link HashMap} with the all possible tags
     *  */
    public static HashMap<String, String> tagParser(String sentenceToParseTag) {
        HashMap<String, String> mapOfTags = new HashMap<>();
        for (String tag : Constants.TAGS_TO_PARSE) {
            if (sentenceToParseTag.contains(tag)) {
                String openTag = Constants.START_OPENING_TAG + tag + Constants.END_OPENING_TAG;
                String closeTag = Constants.START_CLOSING_TAG + tag + Constants.END_CLOSING_TAG;
                String tagValue = sentenceToParseTag.substring(
                        sentenceToParseTag.indexOf(openTag) + openTag.length(), sentenceToParseTag.indexOf(closeTag));
                mapOfTags.put(tag, tagValue);
            }
        }
        return mapOfTags;
    }
}
