//$Id$
package com.zoho.hawking.utils;


import com.zoho.hawking.language.english.tensepredictor.ModelInstances;
import com.zoho.hawking.language.english.tensepredictor.Tense;
import com.zoho.hawking.language.english.tensepredictor.TenseClass;
import com.zoho.hawking.language.english.tensepredictor.Tenses;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordToSentenceProcessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreNlpUtils {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(CoreNlpUtils.class);

  private static final Tense PRESENT_SIMPLE =
      new Tense(Tenses.PRESENT_SIMPLE.name(), TenseClass.PRESENT.name(), TenseClass.PRESENT.name());
  private static final Tense PRESENT_CONTINUOUS =
      new Tense(Tenses.PRESENT_CONTINUOUS.name(), TenseClass.PRESENT.name(),
          TenseClass.PRESENT.name());
  private static final Tense PRESENT_PERFECT =
      new Tense(Tenses.PRESENT_PERFECT.name(), TenseClass.PRESENT.name(), TenseClass.PAST.name());
  private static final Tense PRESENT_PERFECT_CONTINUOUS =
      new Tense(Tenses.PRESENT_PERFECT_CONTINUOUS.name(), TenseClass.PRESENT.name(),
          TenseClass.PAST.name());
  private static final Tense PAST_SIMPLE =
      new Tense(Tenses.PAST_SIMPLE.name(), TenseClass.PAST.name(), TenseClass.PAST.name());
  private static final Tense PAST_CONTINUOUS =
      new Tense(Tenses.PAST_CONTINUOUS.name(), TenseClass.PAST.name(), TenseClass.PAST.name());
  private static final Tense PAST_PERFECT =
      new Tense(Tenses.PAST_PERFECT.name(), TenseClass.PAST.name(), TenseClass.PAST.name());
  private static final Tense PAST_PERFECT_CONTINUOUS =
      new Tense(Tenses.PAST_PERFECT_CONTINUOUS.name(), TenseClass.PAST.name(),
          TenseClass.PAST.name());
  private static final Tense FUTURE_SIMPLE =
      new Tense(Tenses.FUTURE_SIMPLE.name(), TenseClass.FUTURE.name(), TenseClass.FUTURE.name());
  private static final Tense FUTURE_CONTINUOUS =
      new Tense(Tenses.FUTURE_CONTINUOUS.name(), TenseClass.FUTURE.name(),
          TenseClass.FUTURE.name());
  private static final Tense FUTURE_PERFECT =
      new Tense(Tenses.FUTURE_PERFECT.name(), TenseClass.FUTURE.name(), TenseClass.FUTURE.name());
  private static final Tense FUTURE_PERFECT_CONTINUOUS =
      new Tense(Tenses.FUTURE_PERFECT_CONTINUOUS.name(), TenseClass.FUTURE.name(),
          TenseClass.FUTURE.name());
  private static final Tense DEFAULT_TENSE = new Tense("");

  private static MaxentTagger tagger = ModelInstances.getMaxentTaggerInstance();

  public static List<String> sentenceTokenize(String inputPara) {
    List<CoreLabel> tokens = new ArrayList<>();
    PTBTokenizer<CoreLabel> tokenizer = new PTBTokenizer<>(new StringReader(inputPara),
        new CoreLabelTokenFactory(), "");
    while (tokenizer.hasNext()) {
      tokens.add(tokenizer.next());
    }
    //// Split sentences from tokens
    List<List<CoreLabel>> sentences = new WordToSentenceProcessor<CoreLabel>().process(tokens);
    return listOfCoreLabelToString(inputPara, sentences);
  }

  public static List<String> listOfCoreLabelToString(String inputPara,
      List<List<CoreLabel>> sentences) {
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

  public static Tense getParsedDependency(String sentence) {
    Pair<String, String> pos_tags = null;
    Pair<String, String> pos_words = null;
    DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(sentence));
    List<HasWord> document = tokenizer.iterator().next();
    List<TaggedWord> tagged = tagger.tagSentence(document);
    for (int i = 0; i < tagged.size(); i++) {
      String pos = tagged.get(i).tag();
      String word = tagged.get(i).word();
      if (Arrays.asList(Constants.ALL_VERB_POS).contains(pos)) {
        if (i != 0) {
          pos_tags = Pair.of(tagged.get(i - 1).tag(), pos);
          pos_words = Pair.of(tagged.get(i - 1).word(), word);
        } else {
          pos_tags = Pair.of("", pos);
          pos_words = Pair.of("", word);
        }
      }
    }
    Tense tense =
        (pos_tags != null && pos_words != null) ? getTense(pos_tags, pos_words) : DEFAULT_TENSE;
    return tense;
  }

  private static Tense getTense(Pair<String, String> aux, Pair<String, String> words) {
    Tense tense = DEFAULT_TENSE;
    String right = aux.getRight();
    String left = aux.getLeft();
    String rightWord = words.getRight();
    String leftWord = words.getLeft();
    switch (right) {
      case "VB":
        switch (left) {
          case "VBP":
          case "VBZ":
            tense = PRESENT_SIMPLE;
            break;
          case "VBD":
            tense = PAST_SIMPLE;
            break;
          case "MD":
            if (Arrays.asList(Constants.MODAL_PRESENT).contains(leftWord)) {
              tense = PRESENT_SIMPLE;
            } else {
              tense = FUTURE_SIMPLE;
            }
            break;
          default:
            tense = PRESENT_SIMPLE;
        }
        break;
      case "VBN":
        switch (left) {
          case "VBP":
          case "VBZ":
            tense = PRESENT_PERFECT;
            break;
          case "VBD":
            tense = PAST_PERFECT;
            break;
          default:
            tense = PAST_SIMPLE;
        }
        break;
      case "VBD":
        if ("VBD".equals(left)) {
          tense = PAST_PERFECT;
        } else {
          tense = PAST_SIMPLE;
        }
        break;
      case "VBG":
        switch (left) {
          case "VBP":
          case "VBZ":
            tense = PRESENT_CONTINUOUS;
            break;
          case "VBD":
            tense = PAST_CONTINUOUS;
            break;
          default:
            tense = PRESENT_CONTINUOUS;
        }
        break;
    }

    return tense;
  }
}
