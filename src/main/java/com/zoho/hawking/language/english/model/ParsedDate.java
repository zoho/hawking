//$Id$
package com.zoho.hawking.language.english.model;

import edu.stanford.nlp.util.Triple;

import java.util.List;

public class ParsedDate {

    private List<Triple<String, Integer, Integer>> outputWithOffsets;

    private String taggedWithXML;
    
    public List<Triple<String, Integer, Integer>> getOutputWithOffsets() {
        return outputWithOffsets;
    }
    
    public void setOutputWithoffsets(List<Triple<String, Integer, Integer>> outputWithOffsets) {
        this.outputWithOffsets = outputWithOffsets;
    }

    
    public String getTaggedWithXML() {
        return taggedWithXML;
    }
    
    public void setTaggedWithXML(String taggedWithXML) {
        this.taggedWithXML = taggedWithXML;
    }
}
