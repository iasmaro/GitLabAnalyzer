package com.haumea.gitanalyzer.dto;

public class DiffDTO {

    private String oldPath;
    private String newPath;
    private String codeDiff;

    public DiffDTO(String oldPath, String newPath, String codeDiff) {
        this.oldPath = oldPath;
        this.newPath = newPath;
        this.codeDiff = codeDiff;
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

}
