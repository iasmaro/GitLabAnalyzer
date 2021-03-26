package com.haumea.gitanalyzer.dto;

public class LineChangeDTO {
    private String line;
    private double lineScore;

    public LineChangeDTO(String line, double lineScore) {
        this.line = line;
        this.lineScore = lineScore;
    }

    public String getLine() {
        return line;
    }

    public double getLineScore() {
        return lineScore;
    }
}
