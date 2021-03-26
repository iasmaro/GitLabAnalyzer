package com.haumea.gitanalyzer.dto;

import java.util.Map;

public class ScoreDTO {

    private int linesAdded;
    private int linesRemoved;
    private int linesMoved;
    private int spaceLinesAdded;
    private double score;
    private Map<String, Double> scoreByFileTypes;

    public ScoreDTO(int linesAdded, int linesRemoved, double score, int linesMoved, int spaceLinesAdded) {
        this.linesAdded = linesAdded;
        this.linesRemoved = linesRemoved;
        this.score = score;
        this.linesMoved = linesMoved;
        this.spaceLinesAdded = spaceLinesAdded;

    }

    public int getLinesAdded() {
        return linesAdded;
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }

    public int getLinesMoved() {
        return linesMoved;
    }

    public int getSpaceLinesAdded() {
        return spaceLinesAdded;
    }

    public double getScore() {
        return score;
    }

    public Map<String, Double> getScoreByFileTypes() {
        return scoreByFileTypes;
    }

    public void setScoreByFileTypes(Map<String, Double> scoreByFileTypes) {
        this.scoreByFileTypes = scoreByFileTypes;
    }

}
