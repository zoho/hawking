//$Id$
package com.zoho.hawking.utils;

import org.apache.commons.lang3.ArrayUtils;

import java.util.regex.Pattern;

public class Constants {

    public static final String PARSERMODELPATH = "parser/parser.crf.ser.gz";   //No I18N

    public static final String PARSERPROPSPATH = "parser/parser.config.props";   //No I18N

    public static final String RECOGNIZERMODELPATH = "recognizer/recognizer.crf.ser.gz";   //No I18N

    public static final String RECOGIZERPROPSPATH = "recognizer/recognizer.config.props";   //No I18N

    public static final String HAWKINGPROPSPATH = "/conf/hawking.props";   //No I18N

    public static final String NEW_LINE = "\n";  //No I18N

    public static final String SPACE_STRING = " ";  //No I18N

    public static final String MULTIPLE_SPACE_STRING = "\\s+"; //No I18N

    public static final String START_OPENING_TAG = "<";   //No I18N

    public static final String END_OPENING_TAG = ">";   //No I18N

    public static final String START_CLOSING_TAG = "</";    //No I18N

    public static final String END_CLOSING_TAG = ">";   //No I18N

    public static final String QUOTATION_STRING = "\"";   //No I18N

    public static final String OPEN_PARENTHESIS = "("; //NO I18N

    public static final String CLOSE_PARENTHESIS = ")"; //NO I18N


    public static final String EXACT_YEAR_TAG = "exact_year";      //No I18N

    public static final String YEAR_SPAN_TAG = "year_span";  //No I18N

    public static final String MONTH_SPAN_TAG = "month_span"; //No I18N

    public static final String MONTH_OF_YEAR_TAG = "month_of_year"; //No I18N

    public static final String WEEK_SPAN_TAG = "week_span"; //No I18N

    public static final String DAY_SPAN_TAG = "day_span"; //No I18N

    public static final String CURRENT_DAY_TAG = "current_day"; // No I18N

    public static final String DAY_OF_WEEK_TAG = "day_of_week"; // No I18N

    public static final String HOUR_SPAN_TAG = "hour_span"; // No I18N

    public static final String PART_OF_DAY_TAG = "part_of_day"; //No I18N

    public static final String MINUTE_SPAN_TAG = "minute_span"; // No I18N

    public static final String SECOND_SPAN_TAG = "second_span"; // No I18N

    public static final String EXACT_TIME_TAG = "exact_time"; // No I18N

    public static final String EXACT_DATE_TAG = "exact_date"; // No I18N


    public static final String NUMBER_TAG = "exact_number";  //No I18N

    public static final String PREFIX_TAG = "implict_prefix";  //No I18N

    public static final String POSTFIX_TAG = "implict_postfix";  //No I18N

    public static final String SET_PREFIX_TAG = "set_prefix"; //No I18N

    public static final String SET_YEAR_TAG = "set_year"; //No I18N

    public static final String SET_MONTH_TAG = "set_month"; //No I18N

    public static final String SET_WEEK_TAG = "set_week"; //No I18N

    public static final String SET_DAY_TAG = "set_day"; //No I18N

    public static final String SET_HOUR_TAG = "set_hour"; //No I18N

    public static final String SET_MINUTE_TAG = "set_minute"; //No I18N

    public static final String SET_SECOND_TAG = "set_second"; //No I18N

    public static final String QUARTEROFYEAR = "quarterofyear"; //No I18N

    public static final String HALFOFYEAR = "halfofyear"; //No I18N

    public static final String SET_QUARTEROFYEAR = "set_quarterofyear"; //No I18N

    public static final String CUSTOMYEAR = "custom_year"; //No I18N

    private static final String DATEFORMATYEAR = "((\\-|\\.|\\/)(\\d{4}))|((\\d{4})(\\-|\\/|\\.))";  //NO I18n

    private static final String DATEFORMATYEAR2 = "((\\-|\\.|\\/)(\\d{2}))|((\\d{2})(\\-|\\/|\\.))";  //NO I18n

    private static final String ENDYEARFORMAT = "[(\\-|\\.|\\/](Y+)";  //NO I18n

    private static final String STARTYEARFORMAT = "((Y+)[\\-|\\/|\\.])";  //NO I18n

    public static final Pattern DATEPATTERN = Pattern.compile(DATEFORMATYEAR);

    public static final Pattern DATEPATTERN2 = Pattern.compile(DATEFORMATYEAR2);

    public static final Pattern ENDYEARPATTERN = Pattern.compile(ENDYEARFORMAT);

    public static final Pattern STARTYEARPATTERN = Pattern.compile(STARTYEARFORMAT);

    public static final String[] TIME_FORMATS = {"HH:mm:ss", "HH:mm", "HH.mm.ss", "HH.mm", "h:mma", "ha", "h:mm a", "h a", "h.mma", "h.mm a"}; //NO I18n

    public static final String[] DATE_SEPARATORS = {"/", "-", " ", "."}; //NO I18n

    public static final String DMY_FORMAT = "dd{sep}MM{sep}yyyy"; //NO I18n

    public static final String DMY_FORMAT2 = "dd{sep}MMM{sep}yyyy"; //NO I18n

    public static final String MDY_FORMAT = "MMM{sep}dd{sep}yyyy"; //NO I18n

    public static final String YMD_FORMAT = "yyyy{sep}MM{sep}dd"; //NO I18n

    public static final String YMD_FORMAT2 = "yyyy{sep}MMM{sep}dd"; //NO I18n

    public static final String YMD_TEMPLATE = "\\d{4}{sep}\\d{1,2}{sep}\\d{1,2}.*"; //NO I18n

    public static final String YMD_TEMPLATE2 = "\\d{4}{sep}\\w+{sep}\\d{1,2}.*"; //NO I18n

    public static final String MDY_TEMPLATE = "\\w+{sep}\\d{1,2}{sep}\\d{4}.*"; //NO I18n

    public static final String DMY_TEMPLATE = "\\d{1,2}{sep}\\d{1,2}{sep}\\d{4}.*";//NO I18n

    public static final String DMY_TEMPLATE2 = "\\d{1,2}{sep}\\w+{sep}\\d{4}.*";//NO I18n

    public static final String[] TAGS_TO_PARSE = {EXACT_YEAR_TAG, YEAR_SPAN_TAG, MONTH_SPAN_TAG, MONTH_OF_YEAR_TAG, WEEK_SPAN_TAG,
            DAY_SPAN_TAG, CURRENT_DAY_TAG, DAY_OF_WEEK_TAG, HOUR_SPAN_TAG, PART_OF_DAY_TAG, MINUTE_SPAN_TAG,
            SECOND_SPAN_TAG, EXACT_TIME_TAG, EXACT_DATE_TAG, NUMBER_TAG, PREFIX_TAG, POSTFIX_TAG,
            SET_PREFIX_TAG, SET_YEAR_TAG, SET_MONTH_TAG, SET_WEEK_TAG, SET_DAY_TAG,
            SET_HOUR_TAG, SET_MINUTE_TAG, SET_SECOND_TAG, QUARTEROFYEAR, HALFOFYEAR,
            SET_QUARTEROFYEAR, CUSTOMYEAR};


    public static final String[] EXACT_YEAR_PATTERN_ONE = {POSTFIX_TAG, PREFIX_TAG, EXACT_YEAR_TAG};

    public static final String[] EXACT_YEAR_PATTERN_TWO = {PREFIX_TAG, EXACT_YEAR_TAG};

    public static final String[] EXACT_YEAR_PATTERN_THREE = {EXACT_YEAR_TAG};


    public static final String[] YEAR_SPAN_PATTERN_ONE = {PREFIX_TAG, NUMBER_TAG, YEAR_SPAN_TAG};

    public static final String[] YEAR_SPAN_PATTERN_TWO = {SET_PREFIX_TAG, NUMBER_TAG, YEAR_SPAN_TAG};

    public static final String[] YEAR_SPAN_PATTERN_THREE = {NUMBER_TAG, YEAR_SPAN_TAG, POSTFIX_TAG};

    public static final String[] YEAR_SPAN_PATTERN_FOUR = {PREFIX_TAG, YEAR_SPAN_TAG};

    public static final String[] YEAR_SPAN_PATTERN_FIVE = {SET_PREFIX_TAG, YEAR_SPAN_TAG};

    public static final String[] YEAR_SPAN_PATTERN_SIX = {PREFIX_TAG, SET_YEAR_TAG};

    public static final String[] YEAR_SPAN_PATTERN_SEVEN = {YEAR_SPAN_TAG, POSTFIX_TAG};

    public static final String[] YEAR_SPAN_PATTERN_EIGHT = {NUMBER_TAG, YEAR_SPAN_TAG};

    public static final String[] YEAR_SPAN_PATTERN_NINE = {YEAR_SPAN_TAG};

    public static final String[] YEAR_SPAN_PATTERN_TEN = {SET_YEAR_TAG};

    public static final String[] MONTH_SPAN_PATTERN_TWO = {NUMBER_TAG, PREFIX_TAG, MONTH_SPAN_TAG};

    public static final String[] MONTH_SPAN_PATTERN_THREE = {PREFIX_TAG, MONTH_SPAN_TAG, NUMBER_TAG};

    public static final String[] MONTH_SPAN_PATTERN_FOUR = {PREFIX_TAG, NUMBER_TAG, MONTH_SPAN_TAG};

    public static final String[] MONTH_SPAN_PATTERN_FIVE = {SET_PREFIX_TAG, NUMBER_TAG, MONTH_SPAN_TAG};

    public static final String[] MONTH_SPAN_PATTERN_SIX = {NUMBER_TAG, MONTH_SPAN_TAG, POSTFIX_TAG};

    public static final String[] MONTH_SPAN_PATTERN_SEVEN = {NUMBER_TAG, MONTH_SPAN_TAG, PREFIX_TAG};

    public static final String[] MONTH_SPAN_PATTERN_EIGHT = {PREFIX_TAG, MONTH_SPAN_TAG};

    public static final String[] MONTH_SPAN_PATTERN_NINE = {PREFIX_TAG, SET_MONTH_TAG};

    public static final String[] MONTH_SPAN_PATTERN_TEN = {SET_PREFIX_TAG, MONTH_SPAN_TAG};

    public static final String[] MONTH_SPAN_PATTERN_ELEVEN = {MONTH_SPAN_TAG, POSTFIX_TAG};

    public static final String[] MONTH_SPAN_PATTERN_TWELVE = {NUMBER_TAG, MONTH_SPAN_TAG};

    public static final String[] MONTH_SPAN_PATTERN_THIRTEEN = {MONTH_SPAN_TAG};

    public static final String[] MONTH_SPAN_PATTERN_FOURTEEN = {SET_MONTH_TAG};


    public static final String[] MONTH_OF_YEAR_PATTERN_ONE = {POSTFIX_TAG, PREFIX_TAG, MONTH_OF_YEAR_TAG, NUMBER_TAG};

    public static final String[] MONTH_OF_YEAR_PATTERN_TWO = {POSTFIX_TAG, PREFIX_TAG, MONTH_OF_YEAR_TAG};

    public static final String[] MONTH_OF_YEAR_PATTERN_THREE = {PREFIX_TAG, MONTH_OF_YEAR_TAG, NUMBER_TAG};

    public static final String[] MONTH_OF_YEAR_PATTERN_FOUR = {SET_PREFIX_TAG, MONTH_OF_YEAR_TAG, NUMBER_TAG};

    public static final String[] MONTH_OF_YEAR_PATTERN_FIVE = {PREFIX_TAG, NUMBER_TAG, MONTH_OF_YEAR_TAG};

    public static final String[] MONTH_OF_YEAR_PATTERN_SIX = {SET_PREFIX_TAG, NUMBER_TAG, MONTH_OF_YEAR_TAG};

    public static final String[] MONTH_OF_YEAR_PATTERN_SEVEN = {NUMBER_TAG, PREFIX_TAG, MONTH_OF_YEAR_TAG};

    public static final String[] MONTH_OF_YEAR_PATTERN_EIGHT = {NUMBER_TAG, SET_PREFIX_TAG, MONTH_OF_YEAR_TAG};

    public static final String[] MONTH_OF_YEAR_PATTERN_NINE = {MONTH_OF_YEAR_TAG, NUMBER_TAG};

    public static final String[] MONTH_OF_YEAR_PATTERN_TEN = {NUMBER_TAG, MONTH_OF_YEAR_TAG};

    public static final String[] MONTH_OF_YEAR_PATTERN_ELEVEN = {PREFIX_TAG, MONTH_OF_YEAR_TAG};

    public static final String[] MONTH_OF_YEAR_PATTERN_TWELVE = {SET_PREFIX_TAG, MONTH_OF_YEAR_TAG};

    public static final String[] MONTH_OF_YEAR_PATTERN_THIRTEEN = {MONTH_OF_YEAR_TAG};


    public static final String[] WEEK_SPAN_PATTERN_ONE = {PREFIX_TAG, NUMBER_TAG, WEEK_SPAN_TAG};

    public static final String[] WEEK_SPAN_PATTERN_TWO = {SET_PREFIX_TAG, NUMBER_TAG, WEEK_SPAN_TAG};

    public static final String[] WEEK_SPAN_PATTERN_THREE = {NUMBER_TAG, WEEK_SPAN_TAG, POSTFIX_TAG};

    public static final String[] WEEK_SPAN_PATTERN_FOUR = {PREFIX_TAG, WEEK_SPAN_TAG, POSTFIX_TAG};

    public static final String[] WEEK_SPAN_PATTERN_FIVE = {POSTFIX_TAG, PREFIX_TAG, WEEK_SPAN_TAG};

    public static final String[] WEEK_SPAN_PATTERN_SIX = {PREFIX_TAG, WEEK_SPAN_TAG};

    public static final String[] WEEK_SPAN_PATTERN_SEVEN = {SET_PREFIX_TAG, WEEK_SPAN_TAG};

    public static final String[] WEEK_SPAN_PATTERN_EIGHT = {WEEK_SPAN_TAG, POSTFIX_TAG};

    public static final String[] WEEK_SPAN_PATTERN_NINE = {NUMBER_TAG, WEEK_SPAN_TAG};

    public static final String[] WEEK_SPAN_PATTERN_TEN = {WEEK_SPAN_TAG};

    public static final String[] WEEK_SPAN_PATTERN_ELEVEN = {SET_WEEK_TAG};


    public static final String[] DAY_SPAN_PATTERN_ONE = {PREFIX_TAG, NUMBER_TAG, DAY_SPAN_TAG};

    public static final String[] DAY_SPAN_PATTERN_TWO = {SET_PREFIX_TAG, NUMBER_TAG, DAY_SPAN_TAG};

    public static final String[] DAY_SPAN_PATTERN_THREE = {NUMBER_TAG, DAY_SPAN_TAG, POSTFIX_TAG};

    public static final String[] DAY_SPAN_PATTERN_FOUR = {DAY_SPAN_TAG, PREFIX_TAG, CURRENT_DAY_TAG};

    public static final String[] DAY_SPAN_PATTERN_FIVE = {PREFIX_TAG, DAY_SPAN_TAG};

    public static final String[] DAY_SPAN_PATTERN_SIX = {SET_PREFIX_TAG, DAY_SPAN_TAG};

    public static final String[] DAY_SPAN_PATTERN_SEVEN = {DAY_SPAN_TAG, POSTFIX_TAG};

    public static final String[] DAY_SPAN_PATTERN_EIGHT = {NUMBER_TAG, DAY_SPAN_TAG};

    public static final String[] DAY_SPAN_PATTERN_NINE = {DAY_SPAN_TAG};

    public static final String[] DAY_SPAN_PATTERN_TEN = {SET_DAY_TAG};


    public static final String[] DAY_OF_WEEK_PATTERN_ONE = {POSTFIX_TAG, PREFIX_TAG, DAY_OF_WEEK_TAG};

    public static final String[] DAY_OF_WEEK_PATTERN_TWO = {NUMBER_TAG, DAY_OF_WEEK_TAG};

    public static final String[] DAY_OF_WEEK_PATTERN_THREE = {PREFIX_TAG, DAY_OF_WEEK_TAG};

    public static final String[] DAY_OF_WEEK_PATTERN_FOUR = {SET_PREFIX_TAG, DAY_OF_WEEK_TAG};

    public static final String[] DAY_OF_WEEK_PATTERN_FIVE = {DAY_OF_WEEK_TAG};


    public static final String[] CURRENT_DAY_PATTERN_ONE = {PREFIX_TAG, CURRENT_DAY_TAG};

    public static final String[] CURRENT_DAY_PATTERN_TWO = {SET_PREFIX_TAG, CURRENT_DAY_TAG};

    public static final String[] CURRENT_DAY_PATTERN_THREE = {CURRENT_DAY_TAG};


    public static final String[] HOUR_SPAN_PATTERN_ONE = {PREFIX_TAG, NUMBER_TAG, HOUR_SPAN_TAG};

    public static final String[] HOUR_SPAN_PATTERN_TWO = {SET_PREFIX_TAG, NUMBER_TAG, HOUR_SPAN_TAG};

    public static final String[] HOUR_SPAN_PATTERN_THREE = {NUMBER_TAG, HOUR_SPAN_TAG, POSTFIX_TAG};

    public static final String[] HOUR_SPAN_PATTERN_FOUR = {PREFIX_TAG, HOUR_SPAN_TAG};

    public static final String[] HOUR_SPAN_PATTERN_FIVE = {SET_PREFIX_TAG, HOUR_SPAN_TAG};

    public static final String[] HOUR_SPAN_PATTERN_SIX = {HOUR_SPAN_TAG, POSTFIX_TAG};

    public static final String[] HOUR_SPAN_PATTERN_SEVEN = {NUMBER_TAG, HOUR_SPAN_TAG};

    public static final String[] HOUR_SPAN_PATTERN_EIGHT = {HOUR_SPAN_TAG};

    public static final String[] HOUR_SPAN_PATTERN_NINE = {SET_HOUR_TAG};


    public static final String[] PART_OF_DAY_PATTERN_ONE = {PREFIX_TAG, PART_OF_DAY_TAG, PREFIX_TAG, EXACT_TIME_TAG};

    public static final String[] PART_OF_DAY_PATTERN_TWO = {SET_PREFIX_TAG, PART_OF_DAY_TAG, PREFIX_TAG, EXACT_TIME_TAG};

    public static final String[] PART_OF_DAY_PATTERN_THREE = {PREFIX_TAG, PART_OF_DAY_TAG, PREFIX_TAG, NUMBER_TAG};

    public static final String[] PART_OF_DAY_PATTERN_FOUR = {SET_PREFIX_TAG, PART_OF_DAY_TAG, PREFIX_TAG, NUMBER_TAG};

    public static final String[] PART_OF_DAY_PATTERN_FIVE = {PREFIX_TAG, EXACT_TIME_TAG, PREFIX_TAG, PART_OF_DAY_TAG};

    public static final String[] PART_OF_DAY_PATTERN_SIX = {SET_PREFIX_TAG, EXACT_TIME_TAG, PREFIX_TAG, PART_OF_DAY_TAG};

    public static final String[] PART_OF_DAY_PATTERN_SEVEN = {PREFIX_TAG, NUMBER_TAG, PREFIX_TAG, PART_OF_DAY_TAG};

    public static final String[] PART_OF_DAY_PATTERN_EIGHT = {SET_PREFIX_TAG, NUMBER_TAG, PREFIX_TAG, PART_OF_DAY_TAG};

    public static final String[] PART_OF_DAY_PATTERN_NINE = {PREFIX_TAG, PART_OF_DAY_TAG, EXACT_TIME_TAG};

    public static final String[] PART_OF_DAY_PATTERN_TEN = {SET_PREFIX_TAG, PART_OF_DAY_TAG, EXACT_TIME_TAG};

    public static final String[] PART_OF_DAY_PATTERN_ELEVEN = {PREFIX_TAG, PART_OF_DAY_TAG, NUMBER_TAG};

    public static final String[] PART_OF_DAY_PATTERN_TWELVE = {SET_PREFIX_TAG, PART_OF_DAY_TAG, NUMBER_TAG};

    public static final String[] PART_OF_DAY_PATTERN_THIRTEEN = {EXACT_TIME_TAG, PREFIX_TAG, PART_OF_DAY_TAG};

    public static final String[] PART_OF_DAY_PATTERN_FOURTEEN = {EXACT_TIME_TAG, SET_PREFIX_TAG, PART_OF_DAY_TAG};

    public static final String[] PART_OF_DAY_PATTERN_FIFTEEN = {NUMBER_TAG, PREFIX_TAG, PART_OF_DAY_TAG};

    public static final String[] PART_OF_DAY_PATTERN_SIXTEEN = {NUMBER_TAG, SET_PREFIX_TAG, PART_OF_DAY_TAG};

    public static final String[] PART_OF_DAY_PATTERN_SEVENTEEN = {PREFIX_TAG, EXACT_TIME_TAG, PART_OF_DAY_TAG};

    public static final String[] PART_OF_DAY_PATTERN_EIGHTEEN = {SET_PREFIX_TAG, EXACT_TIME_TAG, PART_OF_DAY_TAG};

    public static final String[] PART_OF_DAY_PATTERN_NINETEEN = {PREFIX_TAG, NUMBER_TAG, PART_OF_DAY_TAG};

    public static final String[] PART_OF_DAY_PATTERN_TWENTY = {SET_PREFIX_TAG, NUMBER_TAG, PART_OF_DAY_TAG};

    public static final String[] PART_OF_DAY_PATTERN_TWENTYONE = {PART_OF_DAY_TAG, EXACT_TIME_TAG};

    public static final String[] PART_OF_DAY_PATTERN_TWENTYTWO = {EXACT_TIME_TAG, PART_OF_DAY_TAG};

    public static final String[] PART_OF_DAY_PATTERN_TWENTYTHREE = {PART_OF_DAY_TAG, NUMBER_TAG};

    public static final String[] PART_OF_DAY_PATTERN_TWENTYFOUR = {NUMBER_TAG, PART_OF_DAY_TAG};

    public static final String[] PART_OF_DAY_PATTERN_TWENTYFIVE = {PREFIX_TAG, PART_OF_DAY_TAG};

    public static final String[] PART_OF_DAY_PATTERN_TWENTYSIX = {SET_PREFIX_TAG, PART_OF_DAY_TAG};

    public static final String[] PART_OF_DAY_PATTERN_TWENTYSEVEN = {PART_OF_DAY_TAG};


    public static final String[] MINUTE_SPAN_PATTERN_ONE = {PREFIX_TAG, NUMBER_TAG, MINUTE_SPAN_TAG};

    public static final String[] MINUTE_SPAN_PATTERN_TWO = {SET_PREFIX_TAG, NUMBER_TAG, MINUTE_SPAN_TAG};

    public static final String[] MINUTE_SPAN_PATTERN_THREE = {NUMBER_TAG, MINUTE_SPAN_TAG, POSTFIX_TAG};

    public static final String[] MINUTE_SPAN_PATTERN_FOUR = {PREFIX_TAG, MINUTE_SPAN_TAG};

    public static final String[] MINUTE_SPAN_PATTERN_FIVE = {SET_PREFIX_TAG, MINUTE_SPAN_TAG};

    public static final String[] MINUTE_SPAN_PATTERN_SIX = {MINUTE_SPAN_TAG, POSTFIX_TAG};

    public static final String[] MINUTE_SPAN_PATTERN_SEVEN = {NUMBER_TAG, MINUTE_SPAN_TAG};

    public static final String[] MINUTE_SPAN_PATTERN_EIGHT = {MINUTE_SPAN_TAG};

    public static final String[] MINUTE_SPAN_PATTERN_NINE = {SET_MINUTE_TAG};


    public static final String[] SECOND_SPAN_PATTERN_ONE = {PREFIX_TAG, NUMBER_TAG, SECOND_SPAN_TAG};

    public static final String[] SECOND_SPAN_PATTERN_TWO = {SET_PREFIX_TAG, NUMBER_TAG, SECOND_SPAN_TAG};

    public static final String[] SECOND_SPAN_PATTERN_THREE = {NUMBER_TAG, SECOND_SPAN_TAG, POSTFIX_TAG};

    public static final String[] SECOND_SPAN_PATTERN_FOUR = {PREFIX_TAG, SECOND_SPAN_TAG};

    public static final String[] SECOND_SPAN_PATTERN_FIVE = {SET_PREFIX_TAG, SECOND_SPAN_TAG};

    public static final String[] SECOND_SPAN_PATTERN_SIX = {SECOND_SPAN_TAG, POSTFIX_TAG};

    public static final String[] SECOND_SPAN_PATTERN_SEVEN = {NUMBER_TAG, SECOND_SPAN_TAG};

    public static final String[] SECOND_SPAN_PATTERN_EIGHT = {SECOND_SPAN_TAG};

    public static final String[] SECOND_SPAN_PATTERN_NINE = {SET_SECOND_TAG};


    public static final String[] EXACT_TIME_PATTERN_ONE = {PREFIX_TAG, EXACT_TIME_TAG};

    public static final String[] EXACT_TIME_PATTERN_TWO = {SET_PREFIX_TAG, EXACT_TIME_TAG};

    public static final String[] EXACT_TIME_PATTERN_THREE = {PREFIX_TAG, NUMBER_TAG, EXACT_TIME_TAG};

    public static final String[] EXACT_TIME_PATTERN_FOUR = {SET_PREFIX_TAG, NUMBER_TAG, EXACT_TIME_TAG};

    public static final String[] EXACT_TIME_PATTERN_FIVE = {NUMBER_TAG, EXACT_TIME_TAG};

    public static final String[] EXACT_TIME_PATTERN_SIX = {EXACT_TIME_TAG};


    public static final String[] EXACT_DATE_PATTERN_ONE = {PREFIX_TAG, EXACT_DATE_TAG};

    public static final String[] EXACT_DATE_PATTERN_TWO = {EXACT_DATE_TAG};


    public static final String[] CUSTOM_QUARTER_PATTERN_ONE = {PREFIX_TAG, NUMBER_TAG, QUARTEROFYEAR};

    public static final String[] CUSTOM_QUARTER_PATTERN_TWO = {SET_PREFIX_TAG, NUMBER_TAG, QUARTEROFYEAR};

    public static final String[] CUSTOM_QUARTER_PATTERN_THREE = {NUMBER_TAG, QUARTEROFYEAR, PREFIX_TAG};

    public static final String[] CUSTOM_QUARTER_PATTERN_FOUR = {PREFIX_TAG, QUARTEROFYEAR};

    public static final String[] CUSTOM_QUARTER_PATTERN_FIVE = {SET_PREFIX_TAG, QUARTEROFYEAR};

    public static final String[] CUSTOM_QUARTER_PATTERN_SIX = {NUMBER_TAG, QUARTEROFYEAR};

    public static final String[] CUSTOM_QUARTER_PATTERN_SEVEN = {QUARTEROFYEAR};


    public static final String[] CUSTOM_HALF_PATTERN_ONE = {PREFIX_TAG, HALFOFYEAR};

    public static final String[] CUSTOM_HALF_PATTERN_TWO = {HALFOFYEAR};


    public static final String[] CUSTOM_YEAR_PATTERN_ONE = {PREFIX_TAG, CUSTOMYEAR};

    public static final String[] CUSTOM_YEAR_PATTERN_TWO = {CUSTOMYEAR};

    public static final String[] PRESENT_POS = {"VB", "VBP", "VBZ", "VBG"}; //No I18N

    public static final String[] PAST_POS = {"VBD", "VBN"}; //No I18N

    public static final String[] ALL_VERB_POS = ArrayUtils.addAll(PRESENT_POS, PAST_POS);

    public static final String[] MODAL_PRESENT = {"can", "may", "must", "ought", "could", "should", "might", "would"}; //No I18N

    public static final String[] MODAL_PAST = {"would have", "would always", "would never", "would often", "would occasionally", "would seldom", "could have", "should have", "might have", "must have", "cannot have", "can have"}; //No I18N

    public static final String[] MODAL_PAST_PERFECT = {"would have been", "should have been", "could have been", "might have been"}; //No I18N

}