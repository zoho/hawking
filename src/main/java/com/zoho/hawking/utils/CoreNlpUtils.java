//$Id$
package com.zoho.hawking.utils;

import com.zoho.hawking.language.english.tensepredictor.ModelInstances;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordToSentenceProcessor;
import edu.stanford.nlp.trees.GrammaticalStructure;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CoreNlpUtils {

    public static List<String> sentenceTokenize(String inputPara) {
        List<CoreLabel> tokens = new ArrayList<>();
        PTBTokenizer<CoreLabel> tokenizer = new PTBTokenizer<>(new StringReader(inputPara), new CoreLabelTokenFactory(), "");
        while (tokenizer.hasNext()) {
            tokens.add(tokenizer.next());
        }
        //// Split sentences from tokens
        List<List<CoreLabel>> sentences = new WordToSentenceProcessor<CoreLabel>().process(tokens);
        return listOfCoreLabelToString(inputPara, sentences);
    }

    public static List<String> listOfCoreLabelToString(String inputPara, List<List<CoreLabel>> sentences) {
        int senEnd;
        int senStart = 0;
        List<String> sentenceList = new ArrayList<>();
        for (List<CoreLabel> sentence : sentences) {
            senEnd = sentence.get(sentence.size() - 1).endPosition();
            sentenceList.add(inputPara.substring(senStart, senEnd).trim());
            senStart = senEnd;
        }
        return sentenceList;
    }

    public static ArrayList<GrammaticalStructure> getParsedDependency(String sentence) {
        ArrayList<GrammaticalStructure> grammaticalStructures = new ArrayList<>();
        DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(sentence));
        for (List<HasWord> document : tokenizer) {
            List<TaggedWord> tagged = ModelInstances.getMaxentTaggerInstance().tagSentence(document);
            grammaticalStructures.add(ModelInstances.getDependencyParserInstance().predict(tagged));
        }
        return grammaticalStructures;
    }
}
