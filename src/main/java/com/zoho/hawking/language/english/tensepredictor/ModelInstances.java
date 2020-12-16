//$Id$
package com.zoho.hawking.language.english.tensepredictor;

import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class ModelInstances {
    private static MaxentTagger tagger;
    private static DependencyParser parser;

    static {
        String modelPath = "tense/english_SD.gz"; //No I18N
        String taggerPath = "tense/english-left3words-distsim.tagger"; //No I18N
        tagger = new MaxentTagger(taggerPath);
        parser = DependencyParser.loadFromModelFile(modelPath);
    }

    public static MaxentTagger getMaxentTaggerInstance() {
        return tagger;
    }

    public static DependencyParser getDependencyParserInstance() {
        return parser;
    }
}
