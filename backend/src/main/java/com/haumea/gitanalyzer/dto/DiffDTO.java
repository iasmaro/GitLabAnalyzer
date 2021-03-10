package com.haumea.gitanalyzer.dto;

public class DiffDTO {

    private String oldPath;
    private String newPath;
    private String codeDiff;

    private int linesAdded;
    private int linesRemoved;
    private double diffScore;

    public DiffDTO(String oldPath, String newPath, String codeDiff, DiffScoreDTO scoreDTO) {
        this.oldPath = oldPath;
        this.newPath = newPath;
        this.codeDiff = codeDiff;
        this.linesAdded = scoreDTO.getLinesAdded();
        this.linesRemoved = scoreDTO.getLinesRemoved();
        this.diffScore = scoreDTO.getDiffScore();
    }

    public String getOldPath() {
        return oldPath;
    }

    public String getNewPath() {
        return newPath;
    }

    public String getCodeDiff() {
        return codeDiff;
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
