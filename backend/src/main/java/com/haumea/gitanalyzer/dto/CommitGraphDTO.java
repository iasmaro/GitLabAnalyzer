package com.haumea.gitanalyzer.dto;

import java.util.Date;
import java.util.List;

public class CommitGraphDTO {
    private Date date;
    private int numberOfCommits;
    private double totalCommitScore;

    public CommitGraphDTO(Date date, int numberOfCommits, double totalCommitScore) {
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

    public double getTotalCommitScore() {
        return totalCommitScore;
    }

}
