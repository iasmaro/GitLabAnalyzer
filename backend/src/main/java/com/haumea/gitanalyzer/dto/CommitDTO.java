package com.haumea.gitanalyzer.dto;

import java.util.Date;
import java.util.List;

public class CommitDTO {
    private String commitMessage;
    private Date commitDate;
    private String commitAuthor;
    private Integer commitScore;
    private List<String> commitDiffs;

    public CommitDTO(String commitMessage, Date commitDate, String commitAuthor, Integer commitScore, List<String> commitDiffs) {
        this.commitMessage = commitMessage;
        this.commitDate = commitDate;
        this.commitAuthor = commitAuthor;
        this.commitScore = commitScore;
        this.commitDiffs = commitDiffs;
    }

    public String getCommitMessage() {
        return commitMessage;
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

    public List<String> getCommitDiffs(){
        return commitDiffs;
    }
}
