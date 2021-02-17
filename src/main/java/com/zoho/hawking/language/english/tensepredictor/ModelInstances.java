//$Id$
package com.zoho.hawking.language.english.tensepredictor;


import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class ModelInstances {

  private static MaxentTagger tagger;

  static {
    String taggerPath = "tense/english-left3words-distsim.tagger"; //No I18N
    tagger = new MaxentTagger(taggerPath);
  }

  public static MaxentTagger getMaxentTaggerInstance() {
    return tagger;
  }

}
