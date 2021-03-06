package com.haumea.gitanalyzer.dto;

import java.util.Map;

public class ScoreDTO {

    private int linesAdded;
    private int meaningFullLinesAdded;
    private int meaningFullLinesRemoved;
    private int linesRemoved;
    private int linesMoved;
    private int spaceLinesAdded;
    private int spaceLinesRemoved;
    private int syntaxLinesAdded;
    private int syntaxLinesRemoved;
    private int commentLinesAdded;
    private int commentLinesRemoved;


    private double score;
    private double modifiedScore;
    private Map<String, Double> scoreByFileTypes;

    public ScoreDTO(int linesAdded,
                    int linesRemoved,
                    int meaningFullLinesAdded,
                    int meaningFullLinesRemoved,
                    int syntaxLinesAdded,
                    int syntaxLinesRemoved,
                    int spaceLinesAdded,
                    int spaceLinesRemoved,
                    int linesMoved,
                    int commentLinesAdded,
                    int commentLinesRemoved,
                    double score) {

        this.linesAdded = linesAdded;
        this.meaningFullLinesRemoved = meaningFullLinesRemoved;
        this.meaningFullLinesAdded = meaningFullLinesAdded;
        this.linesRemoved = linesRemoved;
        this.spaceLinesRemoved = spaceLinesRemoved;
        this.syntaxLinesRemoved = syntaxLinesRemoved;
        this.score = score;
        this.modifiedScore = -1;
        this.linesMoved = linesMoved;
        this.spaceLinesAdded = spaceLinesAdded;
        this.syntaxLinesAdded = syntaxLinesAdded;
        this.commentLinesAdded = commentLinesAdded;
        this.commentLinesRemoved = commentLinesRemoved;
    }

    public int getLinesAdded() {
        return linesAdded;
    }

    public int getMeaningFullLinesAdded() {
        return meaningFullLinesAdded;
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

    public int getSyntaxLinesAdded() {
        return syntaxLinesAdded;
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

    public int getMeaningFullLinesRemoved() {
        return meaningFullLinesRemoved;
    }


    public int getCommentLinesAdded() {
        return commentLinesAdded;
    }

    public int getSpaceLinesRemoved() {
        return spaceLinesRemoved;
    }

    public int getSyntaxLinesRemoved() {
        return syntaxLinesRemoved;
    }

    public int getCommentLinesRemoved() {
        return commentLinesRemoved;
    }

    public double getModifiedScore() {
        return modifiedScore;
    }

}
