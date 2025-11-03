package com.zoho.hawking.utils;

import java.util.regex.*;
import java.util.stream.*;

public class CustomDateFormatter {

    private static final Pattern DATE_RANGE_PATTERN = Pattern.compile(
            "(\\d{2}/\\d{4})\\s*(-|to)?\\s*(\\d{2}/\\d{4}|Present|PRESENT|present|Current|CURRENT|current)|" + // Matches "MM/YYYY MM/YYYY" or "MM/YYYY - MM/YYYY" //No I18N
                    "(\\d{4}-\\d{2})\\s*(-|to)?\\s*(\\d{4}-\\d{2}|Present|PRESENT|present|Current|CURRENT|current)|" + //No I18N
                    "(\\d{4})[-\\s]+(\\d{2}|\\d{4})|" + //No I18N
                    "(\\d{4})[-\\s]+(Present|PRESENT|present|Current|CURRENT|current)",// Matches "YYYY YYYY" or "YYYY-YY" //No I18N
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern REMOVE_DELIMITERS_PATTERN = Pattern.compile(
            // Handles cases like "Mon, Sep 8 - 06:00" or "Sep 8 - 06:00"
            "(?i)\\b([A-Za-z]{3,9})[.,]?\\s*(\\d{1,2})(?:\\s*[-–]\\s*(\\d{1,2}:\\d{2}(?:\\s*[APap][Mm])?))?" +
                    // OR existing full date patterns like "09-April-24 05:01"
                    "|\\b(\\d{1,2})\\s*[-.,]?\\s*" +
                    "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec|" +
                    "January|February|March|April|May|June|July|August|September|October|November|December)" +
                    "\\s*[-.,]?\\s*(\\d{2,4})" +
                    "(?:\\s*[;,.]?\\s*(\\d{1,2}:\\d{2}(?:\\s*[APap][Mm])?))?",
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern ADD_SPACE_BETWEEN_TEXT_AND_NUMBERS_PATTERN = Pattern.compile(
            "(?i)(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec|January|February|March|April" +//No I18N
                    "|May|June|July|August|September|October|November|December)(\\d+)|(\\d+)(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct" + //No I18N
                    "|Nov|Dec|January|February|March|April|May|June|July|August|September|October|November|December)",//No I18N
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern TWO_MONTHS_WITH_YEAR_PATTERN = Pattern.compile(
            "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec|January|February|March|April|May|June|July|August|September|October|November|December)\\s*" + //No I18N
                    "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec|January|February|March|April|May|June|July|August|September|October|November|December)\\s*" + //No I18N
                    "(\\d{2,4})", //No I18N
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern ADD_SPACES_AROUND_HYPHENS_PATTERN = Pattern.compile(
            "(?i)(Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Oct(?:ober)?" + //No I18N
                    "|Nov(?:ember)?|Dec(?:ember)?|Current|Present)(\\s?\\'?\\s?(\\d{2,4})?)\\s?-\\s?(Jan(?:uary)?|Feb(?:ruary)?|" + //No I18N
                    "Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?" + //No I18N
                    "|Current|Present)(\\s?\\'?\\s?(\\d{2,4})?)", //No I18N
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern REPLACE_SPACE_WITH_TO_PATTERN = Pattern.compile(
            "(?i)((Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec|January|February|March|April|May|June|July|August|September|October" + //No I18N
                    "|November|December)\\s*(\\d{2,4}|'\\s*?\\d{2,4})?)\\s+((Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec|January|February|March|April" + //No I18N
                    "|May|June|July|August|September|October|November|December|Current|Present)(\\s*(\\d{2,4}|'\\d{2,4})?)?)",//No I18N
            Pattern.CASE_INSENSITIVE
    );

    public static String formatAndNormalizeDateRange(String input) {
        // Regular expression to match and normalize both types of date ranges
        Matcher matcher = DATE_RANGE_PATTERN.matcher(input.trim());

        if (matcher.matches()) {
            // Handle "MM/YYYY MM/YYYY" or "MM/YYYY - MM/YYYY"
            if (matcher.group(1) != null && matcher.group(3) != null) {
                return matcher.group(1) + " to " + matcher.group(3); //No I18N
            }

            // Handle "YYYY-MM YYYY-MM" or variations like "YYYY-MM to YYYY-MM"
            if (matcher.group(4) != null && matcher.group(6) != null) {
                return matcher.group(4) + " to " + matcher.group(6); //No I18N
            }

            // Handle "YYYY YYYY" or "YYYY-MM"
            if (matcher.group(7) != null && matcher.group(8) != null) {
                String startYear = matcher.group(7);
                String endYear = matcher.group(8);

                // If the end year is two digits, expand it based on the start year
                if (endYear.length() == 2) {
                    int startYearInt = Integer.parseInt(startYear);
                    int endYearInt = Integer.parseInt(endYear);
                    if (endYearInt <= 99) {
                        if (endYearInt < (startYearInt % 100)) {
                            endYear = String.valueOf((startYearInt / 100 + 1) * 100 + endYearInt);
                        } else {
                            endYear = String.valueOf((startYearInt / 100) * 100 + endYearInt);
                        }
                    }
                }
                return startYear + " to " + endYear; //No I18N
            }
            if (matcher.group(9) != null && matcher.group(10) != null) {
                return matcher.group(9) + " to " + matcher.group(10); //No I18N
            }

        }

        return input;
    }

    public static String formatDateRange(String input) {

        //case 1 Remove the comma or hyphen or full stop like feb-2024 =feb 2024 , feb,2024=feb 2024, feb.2024=feb 2024
        Matcher matcher = REMOVE_DELIMITERS_PATTERN.matcher(input);
        StringBuffer result = new StringBuffer();
        // Process each match and replace as needed
        while (matcher.find()) {
            String replacement;

            // Case 1: "Mon, Sep 8 - 06:00"
            if (matcher.group(1) != null) {
                String month = matcher.group(1).trim();
                String day = matcher.group(2) != null ? matcher.group(2).trim() : "";
                String time = matcher.group(3) != null ? " " + matcher.group(3).trim() : "";
                replacement = month + " " + day + time;

                // Case 2: "09-April-24 05:01"
            } else {
                String day = matcher.group(4) != null ? matcher.group(4).trim() : "";
                String month = matcher.group(5) != null ? matcher.group(5).trim() : "";
                String year = matcher.group(6) != null ? matcher.group(6).trim() : "";
                String time = matcher.group(7) != null ? " " + matcher.group(7).trim() : "";
                replacement = (day + " " + month + " " + year + time).trim();
            }

            matcher.appendReplacement(result, replacement);
        }

        matcher.appendTail(result);
        String output = result.toString();
        // case 2: Add space if string and number are together

        matcher = ADD_SPACE_BETWEEN_TEXT_AND_NUMBERS_PATTERN.matcher(output);
        result = new StringBuffer();
        while (matcher.find()) {
            if (matcher.group(1) != null && matcher.group(2) != null) {
                // Handles pattern: Month followed by digits
                matcher.appendReplacement(result, matcher.group(1) + " " + matcher.group(2));
            } else if (matcher.group(3) != null && matcher.group(4) != null) {
                // Handles pattern: Digits followed by Month
                matcher.appendReplacement(result, matcher.group(3) + " " + matcher.group(4));
            }
        }
        matcher.appendTail(result);
        output = result.toString();

        //case 3: feb March 2021 =  feb 2021 to March 2021

        matcher = TWO_MONTHS_WITH_YEAR_PATTERN.matcher(output);
        if (matcher.find()) {
            String month1 = matcher.group(1);
            String month2 = matcher.group(2);
            String yearOrDay1 = matcher.group(3);

            // Check if the third group is a year (4 digits) or a day (2 digits)
            String formattedFirstDate = yearOrDay1.length() == 4 ? month1 + " " + yearOrDay1
                    : month1 + " " + yearOrDay1.substring(2);
            String formattedSecondDate =
                    month2 + " " + (yearOrDay1.length() == 4 ? yearOrDay1 : yearOrDay1.substring(2));
            output = formattedFirstDate + " to " + formattedSecondDate;//No I18N
            return output;
        }

        // Case 4: Add space around hyphens with strings and numbers (feb'23- apr'24 = feb'23 - apr'24 and April 2003 -May 2003 = April 2003 - May 2003)
        matcher = ADD_SPACES_AROUND_HYPHENS_PATTERN.matcher(output);
        if (matcher.find()) {
            output = output.replaceAll("\\s?-\\s?", " - ");//No I18N
            return output;
        }

        // case 5: Replace date ranges with "to" if space present(Feb 2003 May 2005= Feb 2003 to May 2005)
        matcher = REPLACE_SPACE_WITH_TO_PATTERN.matcher(output);
        result = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(result, matcher.group(1) + " to " + matcher.group(4));//No I18N
        }
        matcher.appendTail(result);
        output = result.toString();
        return output;
    }

}
