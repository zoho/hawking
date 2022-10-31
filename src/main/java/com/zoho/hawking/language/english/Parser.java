//$Id$
package com.zoho.hawking.language.english;

import com.zoho.hawking.utils.Constants;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {

    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

    static AbstractSequenceClassifier<CoreLabel> crf = getCRFInstance();

    private static AbstractSequenceClassifier<CoreLabel> getCRFInstance() {
      try {
        InputStream parserModel = IOUtils.getInputStreamFromURLOrClasspathOrFileSystem(Constants.PARSERMODELPATH);
        return CRFClassifier.getClassifier(parserModel);
      } catch (ClassCastException | ClassNotFoundException | IOException e) {
        LOGGER.log(Level.SEVERE, "Parser :: Exception in parser class", e.getMessage());
        return null;
      }
    }
    public static List<Triple<String, Integer, Integer>> parse(String input) {
        input = input.replaceAll("http","----"); //No I18N
        input = input.replaceAll("www","---"); //No I18N
        return crf.classifyToCharacterOffsets(input);
    }

    public static String parseTest(String input) {
        return crf.classifyWithInlineXML(input);
    }

    private static String generateGazette(String dateGazettePath) {
        return dateGazettePath;
    }

}
