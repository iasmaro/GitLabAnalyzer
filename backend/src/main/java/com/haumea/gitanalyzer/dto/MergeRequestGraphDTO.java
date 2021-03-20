package com.haumea.gitanalyzer.dto;

import java.util.Date;

public class MergeRequestGraphDTO {
    private Date date;
    private int numberOfMergeRequests;
    private int mergeRequestScore;

    public MergeRequestGraphDTO(Date date, int numberOfMergeRequests, int mergeRequestScore) {
        this.date = date;
        this.numberOfMergeRequests = numberOfMergeRequests;
        this.mergeRequestScore = mergeRequestScore;
    }

    public Date getDate() {
        return date;
    }

    public int getNumberOfMergeRequests() {
        return numberOfMergeRequests;
    }

    public int getMergeRequestScore() {
        return mergeRequestScore;
    }
}
