package com.haumea.gitanalyzer.dto;

import java.util.Date;

public class MergeRequestGraphDTO {
    private Date date;
    private int numberOfMergeRequests;
    private double totalMergeRequestScore;

    public MergeRequestGraphDTO(Date date, int numberOfMergeRequests, double totalMergeRequestScore) {
        this.date = date;
        this.numberOfMergeRequests = numberOfMergeRequests;
        this.totalMergeRequestScore = totalMergeRequestScore;
    }

    public Date getDate() {
        return date;
    }

    public int getNumberOfMergeRequests() {
        return numberOfMergeRequests;
    }

    public double getTotalMergeRequestScore() {
        return totalMergeRequestScore;
    }
}
