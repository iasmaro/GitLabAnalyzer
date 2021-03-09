package com.haumea.gitanalyzer.dto;

import java.util.Date;
import java.util.List;

public class CommitDTO {
    private String commitMessage;
    private Date commitDate;
    private String commitAuthor;
    private Integer commitScore;
    private List<String> commitDiffs;
    private int linesAdded;
    private int linesRemoved;

    public CommitDTO(String commitMessage, Date commitDate, String commitAuthor, Integer commitScore, List<String> commitDiffs, int linesAdded, int linesRemoved) {
        this.commitMessage = commitMessage;
        this.commitDate = commitDate;
        this.commitAuthor = commitAuthor;
        this.commitScore = commitScore;
        this.commitDiffs = commitDiffs;
        this.linesAdded = linesAdded;
        this.linesRemoved = linesRemoved;
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

    public List<String> getCommitDiffs() {
        return commitDiffs;
    }

    public int getLinesAdded() {
        return linesAdded;
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }
}
