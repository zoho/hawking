//$Id$
package com.zoho.hawking.datetimeparser.utils;

import com.zoho.hawking.utils.Constants;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;

public class DateUtil {

    public static Date stringToDate(String input, DateTime referenceTime, String userDateFormat) throws Exception {
        Date date;
        input = input.replaceAll("on ", ""); //NO I18n
        Pair<String, Boolean> dateFormatAndIsDM = getDateFormat(input);
        userDateFormat = userDateFormat.replaceAll("y", "Y"); //NO I18n
        userDateFormat = userDateFormat.replaceAll("D", "d"); //NO I18n
        userDateFormat = userDateFormat.replaceAll("m", "M"); //NO I18n
        String dateFormat = (userDateFormat.length() > 3
                && !userDateFormat.equals("dd/MM/YYYY")
                && !(dateFormatAndIsDM != null
                && dateFormatAndIsDM.getKey().contains("MMM"))) ? userDateFormat.replaceAll("[./-]", "{sep}") : dateFormatAndIsDM.getKey(); //NO I18n
        boolean isDM = (userDateFormat.length() > 3 && !userDateFormat.equals("dd/MM/YYYY")) //NO I18n
                ? false : dateFormatAndIsDM.getRight();
        if (dateFormat == null) {
            throw new IllegalArgumentException("Date is not in an accepted format " + input); //No I18N
        }
        Calendar c = Calendar.getInstance();
        for (String sep : Constants.DATE_SEPARATORS) {
            String actualDateFormat = patternForSeparator(dateFormat, sep);
            for (String time : Constants.TIME_FORMATS) {
                date = tryParse(input, actualDateFormat + " " + time);
                if (date != null) {
                    return date;
                }
            }

            Matcher match = Constants.DATEPATTERN.matcher(input);
            Matcher match1 = Constants.DATEPATTERN2.matcher(input);
            Matcher match2 = Constants.ENDYEARPATTERN.matcher(actualDateFormat);
            Matcher match3 = Constants.STARTYEARPATTERN.matcher(actualDateFormat);
            if ((!match.find()) && !(match1.find())) {
                Date sam = new Date();
                int year = sam.getYear() + 1900;
                if (match2.find()) {
                    input = input.replaceAll("[./-]", "/") + "/" + year;  //NO I18n
                } else if (match3.find()) {
                    input = year + "/" + input.replaceAll("[./-]", "/");  //NO I18n
                }
            }
            date = tryParse(input, actualDateFormat);
            if (date != null) {
                if (isDM) {
                    c.setTime(date);
                    c.set(Calendar.YEAR, referenceTime.getYear());
                    date = c.getTime();
                }
                return date;
            }
        }
        throw new Exception("Exact date is not in Format");
    }

    private static Pair<String, Boolean> getDateFormat(String date) {

        for (String sep : Constants.DATE_SEPARATORS) {
            String ymdPattern = patternForSeparator(Constants.YMD_TEMPLATE, sep);
            String dmyPattern = patternForSeparator(Constants.DMY_TEMPLATE, sep);
            String dmyPattern2 = patternForSeparator(Constants.DMY_TEMPLATE2, sep);
            String mdyPattern = patternForSeparator(Constants.MDY_TEMPLATE, sep);
            String ymdPattern2 = patternForSeparator(Constants.YMD_TEMPLATE2, sep);
            if (date.matches(ymdPattern)) {
                return Pair.of(Constants.YMD_FORMAT, false);
            } else if (date.matches(dmyPattern)) {
                return Pair.of(Constants.DMY_FORMAT, false);
            } else if (date.matches(ymdPattern2)) {
                return Pair.of(Constants.YMD_FORMAT2, false);
            } else if (date.matches(dmyPattern2)) {
                return Pair.of(Constants.DMY_FORMAT2, false);
            } else if (date.matches(mdyPattern)) {
                return Pair.of(Constants.MDY_FORMAT, false);
            }
        }
        return null;
    }

    private static String patternForSeparator(String template, String sep) {
        return template.replace("{sep}", sep); //No I18N
    }

    private static Date tryParse(String input, String pattern) {
        try {
            DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
            return dtf.parseDateTime(input).toDate();
        } catch (Exception ignored) {
        }
        return null;
    }

}
