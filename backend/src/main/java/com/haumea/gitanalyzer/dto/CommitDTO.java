package com.haumea.gitanalyzer.dto;

import java.util.Date;

public class CommitDTO {
    private String commitId;
    private Date commitDate;
    private String commitAuthor;
    private Integer commitScore;

    public CommitDTO(String commitId, Date commitDate, String commitAuthor, Integer commitScore) {
        this.commitId = commitId;
        this.commitDate = commitDate;
        this.commitAuthor = commitAuthor;
        this.commitScore = commitScore;
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
}
