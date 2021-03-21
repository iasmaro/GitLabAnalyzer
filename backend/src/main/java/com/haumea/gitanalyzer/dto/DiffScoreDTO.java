package com.haumea.gitanalyzer.dto;

import java.util.List;

public class DiffScoreDTO {

    private int linesAdded;
    private int linesRemoved;
    private double diffScore;
    private List<LineChangeDTO> lineChangeDTOS;

    public DiffScoreDTO(int linesAdded, int linesRemoved, double diffScore, List<LineChangeDTO> lineChangeDTOS) {
        this.linesAdded = linesAdded;
        this.linesRemoved = linesRemoved;
        this.diffScore = diffScore;

        this.lineChangeDTOS = lineChangeDTOS;
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
