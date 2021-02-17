package com.zoho.hawking.utils;
import java.util.regex.Pattern;

public class RecognizerTagger {

  private final static Pattern implictPrefix = Pattern.compile(
      "^(beginning|upcoming|starting|previous|current|between|coming|within|ending|before|until|after|since|start|forty|this|next|last|past|from|till|rest|most|with|the|for|few|end|in|at|on|of|an|a)$");
  private final static Pattern implictPostfix = Pattern.compile("^(back|ago)$");

  private final static Pattern second_span = Pattern.compile("^(second|seconds|sec|secs)$");
  private final static Pattern minute_span = Pattern.compile("^(minute|minutes|min|mins)$");
  private final static Pattern hour_span = Pattern.compile("^(hour|hours|half-hour|hr|hrs)$");
  private final static Pattern day_span = Pattern.compile("^(day|days)$");
  private final static Pattern week_span = Pattern
      .compile("^(week|weeks|weekend|weekends|weekday|weekdays)$");
  private final static Pattern month_span = Pattern.compile("^(month|months)$");
  private final static Pattern year_span = Pattern.compile("^(year|years)$");

  private final static Pattern part_of_day = Pattern
      .compile("^(morning|dawn|noon|afternoon|evening|night|midnight|eve|sunrise|sunset|tonight)$");
  private final static Pattern current_day = Pattern.compile("^(tomorrow|yesterday|now|today)$");
  private final static Pattern day_of_week = Pattern.compile(
      "^(sundays|mondays|tuesdays|wednesdays|thursdays|fridays|saturdays|sunday|monday|tuesday|wednesday|thursday|friday|saturday|sun|mon|tue|tues|wed|thurs|thu|fri|sat)$");
  private final static Pattern month_of_year = Pattern.compile(
      "^(january|february|march|april|may|june|july|august|september|october|november|december|jan|feb|mar|apr|jun|jul|aug|sep|sept|oct|nov|dec)$");

  private final static Pattern exact_date_1 = Pattern
      .compile("(?:\\d{4}|\\d{1,2})[-./]\\d{1,2}[-./](?:\\d{4}|\\d{1,2})$");
  private final static Pattern exact_date_2 = Pattern.compile("^(\\d{1,2})[-/]\\d{1,2}$");

  private final static Pattern exact_time_1 = Pattern
      .compile("^(((0[0-9]|1[0-9]|2[0-3]|[0-9])([:.][0-5][0-9])?([:.][0-5][0-9])?)([AaPp][Mm]))$");
  private final static Pattern exact_time_2 = Pattern
      .compile("^(((0[0-9]|1[0-9]|2[0-3]|[0-9])([:.][0-5][0-9])([:.][0-5][0-9])?))$");
  private final static Pattern exact_time_3 = Pattern.compile("^([AaPp][Mm])$");

  private final static Pattern exact_year = Pattern.compile("^\\d{4}$");
  private final static Pattern exact_number_1 = Pattern.compile(
      "^(first|second|third|fourth|fifth|sixth|seventh|eighth|ninth|tenth|eleventh|twelfth|thirteenth|fourteenth|fifteenth|sixteenth|seventeenth|eightheenth|ninteenth|twentieth|twenty-first|twenty-second|twenty-third|twenty-fourth|twenty-fifth|twenty-sixth|twenty-seventh|twenty-eighth|twenty-ninth|thirtieth|thirty-first|one|two|three|four|five|six|seven|eight|nine|ten|eleven|twelve|thirteen|fourteen|fifteen|sixteen|seventeen|eighteen|nineteen|twenty|thirty|forty|fifty|st|nd|rd|th)$");
  private final static Pattern exact_number_2 = Pattern.compile("(\\d+)(st|nd|rd|th|ᵗʰ|ˢᵗ|ⁿᵈ|ʳᵈ)?");

  public static String getTagger(String word) {
    String tag = "";
    if (implictPrefix.matcher(word).find()) {
      tag = "implict_prefix";
    } else if (part_of_day.matcher(word).find()) {
      tag = "part_of_day";
    } else if (current_day.matcher(word).find()) {
      tag = "current_day";
    } else if (day_of_week.matcher(word).find()) {
      tag = "day_of_week";
    } else if (month_of_year.matcher(word).find()) {
      tag = "month_of_year";
    } else if (exact_date_1.matcher(word).find() || exact_date_2.matcher(word).find()) {
      tag = "exact_date";
    } else if (exact_time_1.matcher(word).find() || exact_time_2.matcher(word).find()
        || exact_time_3.matcher(word).find()) {
      tag = "exact_time";
    } else if (exact_year.matcher(word).find()) {
      tag = "exact_year";
    } else if (second_span.matcher(word).find()) {
      tag = "second_span";
    } else if (minute_span.matcher(word).find()) {
      tag = "minute_span";
    } else if (hour_span.matcher(word).find()) {
      tag = "hour_span";
    } else if (day_span.matcher(word).find()) {
      tag = "day_span";
    } else if (week_span.matcher(word).find()) {
      tag = "week_span";
    } else if (month_span.matcher(word).find()) {
      tag = "month_span";
    } else if (year_span.matcher(word).find()) {
      tag = "year_span";
    } else if (implictPostfix.matcher(word).find()) {
      tag = "implict_postfix";
    } else if (exact_number_1.matcher(word).find() || exact_number_2.matcher(word).find()) {
      tag = "exact_number";
    }

    return tag;
  }
}
