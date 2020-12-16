package com.zoho.hawking.language;

import edu.stanford.nlp.util.Triple;

import java.util.HashMap;
import java.util.List;

public class ModelPrediction {

    private String id;
    private boolean isRelationPresent;
    private List<ModelPrediction> modelPredictions;
    private HashMap<String, String> tokenTagMapper;

    public HashMap<String, String> getTokenTagMapper() {
        return tokenTagMapper;
    }

    public void setTokenTagMapper(HashMap<String, String> tokenTagMapper) {
        this.tokenTagMapper = tokenTagMapper;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ModelPrediction> getModelPredictions() {
        return modelPredictions;
    }

    public void setModelPredictions(List<ModelPrediction> modelPredictions) {
        this.modelPredictions = modelPredictions;
    }

    public boolean isRelationPresent() {
        return isRelationPresent;
    }

    public void setRelationPresent(boolean relationPresent) {
        isRelationPresent = relationPresent;
    }
}
