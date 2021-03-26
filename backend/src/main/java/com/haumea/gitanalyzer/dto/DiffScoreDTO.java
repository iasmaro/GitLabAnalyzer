package com.haumea.gitanalyzer.dto;

import java.util.List;

public class DiffScoreDTO {

    private int linesAdded;
    private int linesRemoved;
    private int linesMoved;
    private int spaceLinesAdded;
    private double diffScore;

    public DiffScoreDTO(int linesAdded, int linesRemoved, double diffScore, int linesMoved, int spaceLinesAdded) {
        this.linesAdded = linesAdded;
        this.linesRemoved = linesRemoved;
        this.diffScore = diffScore;
        this.linesMoved = linesMoved;
        this.spaceLinesAdded = spaceLinesAdded;

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

    public int getLinesMoved() {
        return linesMoved;
    }

    public int getSpaceLinesAdded() {
        return spaceLinesAdded;
    }
}
