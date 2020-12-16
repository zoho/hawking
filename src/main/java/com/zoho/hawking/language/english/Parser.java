//$Id$
package com.zoho.hawking.language.english;

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
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {

    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

    static CRFClassifier<CoreLabel> crf = getCRFInstance();

    private static CRFClassifier<CoreLabel> getCRFInstance() {
        Properties props = new Properties();
        try {
            props.load(CommonUtils.readIsFromClasspath(Constants.PARSERPROPSPATH));
            File dateGazetteFile = CommonUtils.readFileFromClasspath(props.getProperty("distSimLexicon")); //No I18N
            assert dateGazetteFile != null;
            props.setProperty("distSimLexicon", dateGazetteFile.getAbsolutePath()); //No I18N
            String gazette = generateGazette(dateGazetteFile.getAbsolutePath());
            props.setProperty("gazette", gazette); //No I18N
            SeqClassifierFlags flags = new SeqClassifierFlags(props);
            CRFClassifier<CoreLabel> crf = new CRFClassifier<CoreLabel>(flags);
            InputStream parserModel = IOUtils.getInputStreamFromURLOrClasspathOrFileSystem(Constants.PARSERMODELPATH);
            LOGGER.info("Loading Parser Model"); //No I18N
            crf.loadClassifier(parserModel);
            LOGGER.info("Parser Model Loaded"); //No I18N
            return crf;
        } catch (ClassCastException | ClassNotFoundException | IOException e) {
            LOGGER.log(Level.SEVERE, "Parser :: Exception in parser class", e.getMessage());
            return null;
        }
    }

    public static List<Triple<String, Integer, Integer>> parse(String input) {
        LOGGER.info(crf.classifyWithInlineXML(input));
        return crf.classifyToCharacterOffsets(input);
    }

    public static String parseTest(String input) {
        return crf.classifyWithInlineXML(input);
    }

    private static String generateGazette(String dateGazettePath) {
        return dateGazettePath;
    }

}
