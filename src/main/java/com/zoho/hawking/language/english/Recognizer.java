//$Id$
package com.zoho.hawking.language.english;

import com.zoho.hawking.language.english.model.ParsedDate;
import com.zoho.hawking.language.english.tagpredictor.TagPredictor;
import com.zoho.hawking.language.english.tagpredictor.TagUtils;
import com.zoho.hawking.utils.CommonUtils;
import com.zoho.hawking.utils.Constants;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.sequences.SeqClassifierFlags;
import edu.stanford.nlp.util.Triple;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Recognizer {
    private static final Logger LOGGER = Logger.getLogger(Recognizer.class.getName());

    static CRFClassifier<CoreLabel> crf = null;

    public static synchronized CRFClassifier<CoreLabel> getCrfClassifier() {
        if (crf == null) {
            crf = getCRFInstance();
        }
        return crf;
    }


    private static CRFClassifier<CoreLabel> getCRFInstance() {
        Properties props = new Properties();
        try {
            props.load(CommonUtils.readIsFromClasspath(Constants.RECOGIZERPROPSPATH));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Recognizer :: Error While Loading model", e.getMessage()); //No I18N
        }
        SeqClassifierFlags flags = new SeqClassifierFlags(props);
        CRFClassifier<CoreLabel> crf = new CRFClassifier<>(flags);
        try {
            InputStream recognizerModel = IOUtils.getInputStreamFromURLOrClasspathOrFileSystem(Constants.RECOGNIZERMODELPATH);
            LOGGER.info("Recognizer :: Loading Recognizer Model"); //No I18N
            crf.loadClassifier(recognizerModel);
            LOGGER.info("Recognizer :: Recognizer Model Loaded"); //No I18N
        } catch (ClassCastException | ClassNotFoundException | IOException e) {
            LOGGER.log(Level.SEVERE, "Recognizer :: Error While Loading model", e.getMessage()); //No I18N
        }
        return crf;
    }

    public static ParsedDate recognize(String input) {
        String taggedWithXML = getCrfClassifier().classifyWithInlineXML(input);
        List<Triple<String, Integer, Integer>> outputWithOffsets = getCrfClassifier().classifyToCharacterOffsets(input);
        ParsedDate parsedDate = new ParsedDate();
        parsedDate.setOutputWithoffsets(outputWithOffsets);
        parsedDate.setTaggedWithXML(taggedWithXML);
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
}

