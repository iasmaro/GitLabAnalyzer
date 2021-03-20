package com.haumea.gitanalyzer.dto;

import java.util.Date;
import java.util.List;

public class CommitGraphDTO {
    private Date date;
    private int numberOfCommits;
    private int commitScore;

    public CommitGraphDTO(Date date, int numberOfCommits, int commitScore) {
        this.date = date;
        this.numberOfCommits = numberOfCommits;
        this.commitScore = commitScore;
    }

    public Date getDate() {
        return date;
    }

    public int getNumberOfCommits() {
        return numberOfCommits;
    }

    public int getCommitScore() {
        return commitScore;
    }

}
