package com.haumea.gitanalyzer.dto;

public class DiffScoreDTO {

    private int linesAdded;
    private int linesRemoved;
    private double diffScore;

    public DiffScoreDTO(int linesAdded, int linesRemoved, double diffScore) {
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
}
