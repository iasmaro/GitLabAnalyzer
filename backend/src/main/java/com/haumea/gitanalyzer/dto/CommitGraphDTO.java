package com.haumea.gitanalyzer.dto;

import java.util.Date;
import java.util.List;

public class CommitGraphDTO {
    private Date date;
    private int numberOfCommits;
    private int totalCommitScore;

    public CommitGraphDTO(Date date, int numberOfCommits, int totalCommitScore) {
        this.date = date;
        this.numberOfCommits = numberOfCommits;
        this.totalCommitScore = totalCommitScore;
    }

    public Date getDate() {
        return date;
    }

    public int getNumberOfCommits() {
        return numberOfCommits;
    }

    public int getTotalCommitScore() {
        return totalCommitScore;
    }

}
