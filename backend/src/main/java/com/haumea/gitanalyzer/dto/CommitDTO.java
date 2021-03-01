package com.haumea.gitanalyzer.dto;

import java.util.Date;

public class CommitDTO {
    private String commitId;
    private Date commitDate;
    private String commitAuthor;
    private Integer commitScore;
    private Integer linesAdded;

    private Integer linesRemoved;

    public CommitDTO(String commitId, Date commitDate, String commitAuthor, Integer commitScore, Integer linesAdded, Integer linesRemoved) {
        this.commitId = commitId;
        this.commitDate = commitDate;
        this.commitAuthor = commitAuthor;
        this.commitScore = commitScore;
        this.linesAdded = linesAdded;
        this.linesRemoved = linesRemoved;
    }

    public String getCommitId() {
        return commitId;
    }

    public Date getCommitDate() {
        return commitDate;
    }

    public String getCommitAuthor() {
        return commitAuthor;
    }

    public Integer getCommitScore() {
        return commitScore;
    }

    public Integer getLinesAdded(){
        return linesAdded;
    }

    public Integer getLinesRemoved() {
        return linesRemoved;
    }
}
