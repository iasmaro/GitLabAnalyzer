package com.haumea.gitanalyzer.dto;

import java.util.Map;

public class ScoreDTO {

    private int linesAdded;
    private int linesRemoved;
    private double diffScore;
    private Map<String, Double> scoreByFileTypes;

    public ScoreDTO(int linesAdded, int linesRemoved, double diffScore) {
        this.linesAdded = linesAdded;
        this.linesRemoved = linesRemoved;
        this.diffScore = diffScore;
    }

    public int getLinesAdded() {
        return linesAdded;
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }

    public double getDiffScore() {
        return diffScore;
    }

    public Map<String, Double> getScoreByFileTypes() {
        return scoreByFileTypes;
    }

    public void setScoreByFileTypes(Map<String, Double> scoreByFileTypes) {
        this.scoreByFileTypes = scoreByFileTypes;
    }

}
