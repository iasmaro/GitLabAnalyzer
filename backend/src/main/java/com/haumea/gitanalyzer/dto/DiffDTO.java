package com.haumea.gitanalyzer.dto;

public class DiffDTO {

    private String oldPath;
    private String newPath;
    private String extension;
    private String codeDiff;

    private ScoreDTO scoreDTO;

    public DiffDTO(String oldPath, String newPath, String extension, String codeDiff, ScoreDTO scoreDTO) {
        this.oldPath = oldPath;
        this.newPath = newPath;
        this.extension = extension;
        this.codeDiff = codeDiff;

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

    public ScoreDTO getScoreDTO() {
        return scoreDTO;
    }

}
