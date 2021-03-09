package com.haumea.gitanalyzer.gitlab;

public class CommentType {
    private String startType;
    private String endType;

    public CommentType(String startType, String endType) {
        this.startType = startType;
        this.endType = endType;
    }

    public String getStartType() {
        return startType;
    }

    public void setStartType(String startType) {
        this.startType = startType;
    }

    public String getEndType() {
        return endType;
    }

    public void setEndType(String endType) {
        this.endType = endType;
    }
}
