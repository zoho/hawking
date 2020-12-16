//$Id$
package com.zoho.hawking.datetimeparser.constants;

import com.zoho.hawking.datetimeparser.WordProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrepositionConstants {

    /*
     *
     * Prefix used define the property of the word
     * 	start - 0
     * 	end - 1
     * 	secondary - 3
     * nothing - -1
     *
     * TenseIndicator used define property of the word
     * 	past - 0
     * 	present - 1
     * 	future - 2
     * 	immediate_future - 3
     * 	Immediate - 4
     * 	nothing - -1
     * */

    /*Primary Prefix Start Range*/
    public static final WordProperty BEGINNING = new WordProperty("beginning", 0, -1); //No I18N
    public static final WordProperty STARTING = new WordProperty("starting", 0, -1); //No I18N
    public static final WordProperty AFTER = new WordProperty("after", 0, -1, 2, 10); //No I18N
    public static final WordProperty FROM = new WordProperty("from", 0, -1, 2, 10); //No I18N
    /*Primary Prefix Start Range And Tense*/
    public static final WordProperty SINCE = new WordProperty("since", 0, 0); //No I18N
    public static final WordProperty BACK = new WordProperty("back", 0, 0); //No I18N
    public static final WordProperty AGO = new WordProperty("ago", 0, 0); //No I18N
    /*Primary Prefix End Range*/
    public static final WordProperty TILL = new WordProperty("till", 1, -1, 2, 10); //No I18N
    public static final WordProperty UNTIL = new WordProperty("until", 1, -1, 2, 10); //No I18N
    public static final WordProperty WITHIN = new WordProperty("within", 1, -1); //No I18N
    public static final WordProperty ENDING = new WordProperty("ending", 1, -1); //No I18N
    public static final WordProperty BEFORE = new WordProperty("before", 1, -1, 2); //No I18N
    /*Tense Indicator PAST*/
    public static final WordProperty LAST = new WordProperty("last", -1, 0); //No I18N
    public static final WordProperty PREVIOUS = new WordProperty("previous", -1, 0); //No I18N
    /*Tense Indicator FUTURE*/
    public static final WordProperty NEXT = new WordProperty("next", -1, 2); //No I18N
    public static final WordProperty LATER = new WordProperty("later", -1, 2); //No I18N
    /*Tense Indicator PRESENT*/
    public static final WordProperty NOW = new WordProperty("now", -1, 1); //No I18N
    /*Tense Indicator IMMEDIATE FUTURE*/
    public static final WordProperty UPCOMING = new WordProperty("upcoming", -1, 3); //No I18N
    public static final WordProperty COMING = new WordProperty("coming", -1, 3); //No I18N
    /*Tense Indicator IMMEDIATE*/
    public static final WordProperty THIS = new WordProperty("this", -1, 4); //No I18N
    public static final WordProperty THESE = new WordProperty("these", -1, 4); //No I18N
    public static final WordProperty CURRENT = new WordProperty("current", -1, 4); //No I18N
    /*Tense Indicator REMAINDER*/
    public static final WordProperty REST = new WordProperty("rest", -1, 5); //No I18N
    public static final WordProperty REMAIN = new WordProperty("remain", -1, 5); //Stem of the word can also be used (Remainder,Remaining) //No I18N
    /*Tense Indicator Immediate PAST*/
    public static final WordProperty PAST = new WordProperty("past", -1, 6); //No I18N

    /*Secondary Prefix Start Range*/
    public static final WordProperty AT = new WordProperty("at", 2, -1); //No I18N
    public static final WordProperty ON = new WordProperty("on", 2, -1); //No I18N
    public static final WordProperty IN_AN = new WordProperty("in an", 2, -1); //No I18N
    public static final WordProperty IN_THE = new WordProperty("in the", 2, -1); //No I18N
    public static final WordProperty IN_A = new WordProperty("in a", 2, -1); //No I18N
    public static final WordProperty IN = new WordProperty("in", 2, -1); //No I18N
    public static final WordProperty FOR_AN = new WordProperty("for an", 2, -1); //No I18N
    public static final WordProperty FOR = new WordProperty("for", 2, -1); //No I18N
    public static final WordProperty OF_THE = new WordProperty("of the", 2, -1); //No I18N
    public static final WordProperty OF = new WordProperty("of", 2, -1); //No I18N
    public static final WordProperty FEW = new WordProperty("few", 2, -1); //No I18N


    /*Set Prefix */
    public static final WordProperty EVERY = new WordProperty("every", 3, -1); //No I18N
    public static final WordProperty EACH = new WordProperty("each", 3, -1); //No I18N
    public static final WordProperty REGULARLY = new WordProperty("regularly", 3, -1); //No I18N

    /*Relationship prefix
     *
     * 1) range relation (from to)
     * 2) conditional (30 days from today)*/

    public static final WordProperty TO = new WordProperty("to", 10); //No I18N
    public static final WordProperty BETWEEN = new WordProperty("between", 10); //No I18N
    public static final WordProperty INBETWEEN = new WordProperty("inbetween", 10); //No I18N
    public static final WordProperty DASH = new WordProperty("-", 10); //No I18N

    public static final WordProperty OR = new WordProperty("or", 11); //No I18N
    public static final WordProperty ASWELLAS = new WordProperty("aswellas", 11); //No I18N
    public static final WordProperty AND = new WordProperty("and", 11); //No I18N
    public static final WordProperty COMMA = new WordProperty(",", 11);


    static final WordProperty[] ALL_WORDS = {
            BEGINNING, STARTING, AFTER, FROM, SINCE, BACK, AGO, TILL, UNTIL, WITHIN, ENDING, BEFORE, PAST,
            LAST, PREVIOUS, NEXT, LATER, CURRENT, NOW, REST, UPCOMING, COMING, THIS, THESE, AT, ON, IN_AN,
            IN_THE, IN_A, IN, FOR_AN, FOR, OF_THE, OF, FEW, EVERY, EACH, REGULARLY, TO, OR, ASWELLAS, BETWEEN,
            INBETWEEN, DASH, AND, COMMA
    };

    public static final List<String> PRIMARY_PREFIX = createPrefixArray(0, 1);

    public static final List<String> SECONDARY_PREFIX = createPrefixArray(2, 2);

    public static final List<String> START_RANGE = createPrefixArray(0, 0);

    public static final List<String> END_RANGE = createPrefixArray(1, 1);

    public static final List<String> TENSE_INDICATORS = createTenseArray(0, 6);

    // words that directly express date and time
    public static final List<String> PAST_WORDS = createTenseArray(0, 0);

    public static final List<String> FUTURE_WORDS = createTenseArray(2, 2);

    public static final List<String> PRESENT_WORDS = createTenseArray(1, 1);

    // expressing the time frame
    public static final List<String> IMMEDIATE_PAST = createTenseArray(6, 6);

    public static final List<String> IMMEDIATE_FUTURE = createTenseArray(3, 3);

    public static final List<String> IMMEDIATE = createTenseArray(4, 4);

    public static final List<String> REMAINDER = createTenseArray(5, 5);

    public static final List<String> RELATIONSHIP_RANGE = createRelationshipArray(0);

    public static final List<String> RELATIONSHIP_SET = createRelationshipArray(1);

    public static List<String> createPrefixArray(int start, int end) {
        ArrayList<String> wordList = new ArrayList<>();
        for (WordProperty word : ALL_WORDS) {
            if (word.getPrefix() <= end && word.getPrefix() >= start) {
                wordList.add(word.getWord());
            }
        }
        return Collections.unmodifiableList(wordList);
    }

    public static List<String> createTenseArray(int start, int end) {
        ArrayList<String> wordList = new ArrayList<>();
        for (WordProperty word : ALL_WORDS) {
            if (word.getTenseIndicator() <= end && word.getTenseIndicator() >= start) {
                wordList.add(word.getWord());
            }
        }
        return Collections.unmodifiableList(wordList);
    }


    public static List<String> createRelationshipArray(int type) {
        ArrayList<String> wordList = new ArrayList<>();
        for (WordProperty word : ALL_WORDS) {
            if (word.getRelationType() == type) {
                wordList.add(word.getWord());
            }
        }
        return Collections.unmodifiableList(wordList);
    }
}
