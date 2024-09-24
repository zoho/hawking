//$Id$
package com.zoho.hawking.language.english;

import com.zoho.hawking.language.english.model.ParsedDate;
import com.zoho.hawking.language.english.tagpredictor.TagPredictor;
import com.zoho.hawking.language.english.tagpredictor.TagUtils;

import com.zoho.hawking.utils.RecognizerTagger;
import edu.stanford.nlp.util.Triple;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Recognizer {
    private static final Logger LOGGER = Logger.getLogger(Recognizer.class.getName());

    private final static Pattern TIMEFORMATREGEX = Pattern
        .compile("^(((0[0-9]|1[0-9]|2[0-3]|[0-9])([:.][0-5][0-9])?([:.][0-5][0-9])?)([AaPp][Mm]))$");
    private final static Pattern TIMEFORMATREGEXHMS = Pattern
        .compile("^(((0[0-9]|1[0-9]|2[0-3]|[0-9])([:.][0-5][0-9])([:.][0-5][0-9])?))$");

    public static ParsedDate recognize(String input) {
        input = input.toLowerCase();
        StringBuilder taggedWithXML = new StringBuilder();
        ParsedDate parsedDate = new ParsedDate();
        String[] tokens = input.split("\\s+");
        List<Triple<String, Integer, Integer>> dateList = new ArrayList<>();
        int start = 0;
        int end = 0;
        for (String word : tokens) {
            String tag = RecognizerTagger.getTagger(word);
            end = start + word.length();
            if(tag.equals("")){
                LOGGER.info("Word not Found in Recoginzer::::"+ word+":::::");
                start = end+1;
                continue;
            }

            Triple<String, Integer, Integer> tagReturn = Triple.makeTriple(tag, start, end);
            taggedWithXML.append("<" + tag + ">" + word.trim() + "</" + tag + "> ");
            dateList.add(tagReturn);
            start = end+1;
        }
        parsedDate.setOutputWithoffsets(dateList);
        parsedDate.setTaggedWithXML(taggedWithXML.toString().trim());
        parsedDate = tagAlternator(input, parsedDate);
        parsedDate = tagShrinker(input, parsedDate);
        LOGGER.info("Recoginzer Regex Tagged Sequence::::"+ parsedDate.getTaggedWithXML()+":::::");
        return parsedDate;
    }

    public static Map<String, String> tagPredictor(String input, List<Triple<String, Integer, Integer>> tagList) {
        Map<String, String> tagsEach = new HashMap<>();
        ArrayList<String> inputTagList = TagPredictor.getInputTagList(tagList);
        String inputTagPattern = TagPredictor.getInputTagPattern(inputTagList);

        tagsEach.put("year", TagPredictor.yearPredictor(input, tagList, inputTagList, inputTagPattern));
        tagsEach.put("month", TagPredictor.monthPredictor(input, tagList, inputTagList, inputTagPattern));
        String customDate = TagPredictor.customPredictor(input, tagList, inputTagList, inputTagPattern);
        if (customDate != null) {
            if (customDate.contains("fiscal year")) {
                customDate = customDate.replace("fiscal year", "fiscalyear"); //No I18N
            } else if (customDate.contains("annual year")) {
                customDate = customDate.replace("annual year", "annualyear"); //No I18N
            }
        }
        tagsEach.put("custom_date", customDate);
        tagsEach.put("week", TagPredictor.weekPredictor(input, tagList, inputTagList, inputTagPattern));
        tagsEach.put("day", TagPredictor.dayPredictor(input, tagList, inputTagList, inputTagPattern));
        String hourTime = TagPredictor.hourPredictor(input, tagList, inputTagList, inputTagPattern);
        if ((hourTime != null) && (hourTime.contains("exact_time"))) {
            tagsEach.put("time", null);
        } else {
            tagsEach.put("time", TagPredictor.exactTimePredictor(input, tagList, inputTagList, inputTagPattern));
        }
        tagsEach.put("hour", TagPredictor.hourPredictor(input, tagList, inputTagList, inputTagPattern));
        tagsEach.put("minute", TagPredictor.minutePredictor(input, tagList, inputTagList, inputTagPattern));
        tagsEach.put("second", TagPredictor.secondPredictor(input, tagList, inputTagList, inputTagPattern));

        tagsEach.put("date", TagPredictor.datePredictor(input, tagList, inputTagList, inputTagPattern));

        return TagUtils.tagRegulator(input, tagList, tagsEach);
    }
    private static ParsedDate tagShrinker(String parseText, ParsedDate parserDateCurrent) {
        List<Triple<String, Integer, Integer>> triples = parserDateCurrent.getOutputWithOffsets();
        for (int i = 0; i < triples.size(); i++) {
            Triple<String, Integer, Integer> triple = triples.get(i);
            String tag = triple.first();
            String textOne = parseText.substring(triple.second(), triple.third());

            Triple<String, Integer, Integer> triplee = i != (triples.size() - 1) ? triples.get(i + 1) : null;
            String textTwo = triplee != null ? parseText.substring(triplee.second(), triplee.third()) : null;
            String tagg = triplee != null ? triplee.first() : " "; //NO I18n

            if (tag.equals("exact_time") && tagg.equals("exact_time")) {
                Triple<String, Integer, Integer> tripleLocal = new Triple<>("exact_time", triple.second(), triplee.third());  //NO I18n
                triples.remove(i + 1);
                triples.set(i, tripleLocal);
                parserDateCurrent.setOutputWithoffsets(triples);
                parserDateCurrent.setTaggedWithXML(parserDateCurrent.getTaggedWithXML().replace("<exact_time>" + textOne + "</exact_time> " + "<exact_time> " + textTwo + "</exact_time>", "<exact_time>" + textOne + textTwo + "</exact_time>"));  //NO I18n
            }
            if (tag.equals("exact_number") && tagg.equals("exact_number")) {
                Triple<String, Integer, Integer> tripleLocal = new Triple<>("exact_number", triple.second(), triplee.third());  //NO I18n
                triples.remove(i + 1);
                triples.set(i, tripleLocal);
                parserDateCurrent.setOutputWithoffsets(triples);
                parserDateCurrent.setTaggedWithXML(parserDateCurrent.getTaggedWithXML().replace("<exact_number>" + textOne + "</exact_number> " + "<exact_number> " + textTwo + "</exact_number>", "<exact_number>" + textOne + textTwo + "</exact_number>"));  //NO I18n
            }
            Triple<String, Integer, Integer> triplePrev = i > 0 ? triples.get(i - 1) : null;
            String tagPrev = triplePrev != null ? triplePrev.first() : "";

            if (tag.equals("exact_number") && tagg.equals("exact_time") && !((tagPrev.equals("month_of_year")) && (TIMEFORMATREGEX.matcher(textTwo).find() || TIMEFORMATREGEXHMS.matcher(textTwo).find()))) {
                Triple<String, Integer, Integer> tripleLocal = new Triple<>("exact_time", triple.second(), triplee.third());  //NO I18n
                triples.remove(i + 1);
                triples.set(i, tripleLocal);
                parserDateCurrent.setOutputWithoffsets(triples);
                parserDateCurrent.setTaggedWithXML(parserDateCurrent.getTaggedWithXML().replace("<exact_number>" + textOne + "</exact_number> " + "<exact_time>" + textTwo + "</exact_time>", "<exact_time>" + textOne + " " + textTwo + "</exact_time>"));  //NO I18n
                parseText = parseText.replace(textOne + " " + textTwo, textOne + ":" + textTwo);  //NO I18n
                parseText = parseText.replace("to:", "to ");//NO I18n
            }
        }
        return parserDateCurrent;
    }

    private static ParsedDate tagAlternator(String parseText, ParsedDate parserDateCurrent) {
        List<Triple<String, Integer, Integer>> triples = parserDateCurrent.getOutputWithOffsets();
        String tag_xml = parserDateCurrent.getTaggedWithXML();
        if ((tag_xml.contains("day_of_week") || tag_xml.contains("current_day")) &&
            (tag_xml.contains("month_of_year") || tag_xml.contains("month_span")) &&
            tag_xml.contains("exact_number") &&
            !tag_xml.contains("</day_of_week> <implict_prefix>of</implict_prefix>")) {
            List<Triple<String, Integer, Integer>> triple = parserDateCurrent.getOutputWithOffsets();
            String date_xml = parserDateCurrent.getTaggedWithXML();
            for (int i = 0; i < triple.size(); i++) {
                Triple<String, Integer, Integer> triplet = triples.get(i);
                String tag = triplet.first();
                if (tag.equals("day_of_week") || tag.equals("current_day")) {
                    String text = parseText.substring(triplet.second(), triplet.third());
                    triple.remove(i);
                    parserDateCurrent.setOutputWithoffsets(triple);
                    parserDateCurrent.setTaggedWithXML(date_xml.replace("<day_of_week>" + text + "</day_of_week>", "")); //No I18N
                }
            }
        }
        return parserDateCurrent;
    }


}

