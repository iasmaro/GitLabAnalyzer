package com.haumea.gitanalyzer.dto;

public class DiffDTO {

    private String oldPath;
    private String newPath;
    private String extension;
    private String codeDiff;

    private int linesAdded;
    private int linesRemoved;
    private int linesMoved;
    private int spaceLinesAdded;
    private int syntaxLinesAdded;

    private double diffScore;

    private ScoreDTO scoreDTO;

    public DiffDTO(String oldPath, String newPath, String extension, String codeDiff, ScoreDTO scoreDTO) {
        this.oldPath = oldPath;
        this.newPath = newPath;
        this.extension = extension;
        this.codeDiff = codeDiff;
        this.linesAdded = scoreDTO.getLinesAdded();
        this.linesRemoved = scoreDTO.getLinesRemoved();
        this.linesMoved = scoreDTO.getLinesMoved();
        this.spaceLinesAdded = scoreDTO.getSpaceLinesAdded();
        this.syntaxLinesAdded = scoreDTO.getSyntaxLinesAdded();
        this.diffScore = scoreDTO.getScore();

        this.scoreDTO = scoreDTO;
    }

    public String getOldPath() {
        return oldPath;
    }

    public String getNewPath() {
        return newPath;
    }

    public String getExtension() {
        return extension;
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

    public int getLinesMoved() {
        return linesMoved;
    }

    public int getSpaceLinesAdded() {
        return spaceLinesAdded;
    }

    public int getSyntaxLinesAdded() {
        return syntaxLinesAdded;
    }

    public double getDiffScore() {
        return diffScore;
    }

}
