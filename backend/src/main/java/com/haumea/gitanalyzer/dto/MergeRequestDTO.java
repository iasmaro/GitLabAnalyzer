package com.haumea.gitanalyzer.dto;

import java.util.Date;
import java.util.List;

public class MergeRequestDTO {

    private int mergeId;
    private Date mergedDate;

    private Date createdDate;
    private Date updatedDate;
    private double MRScore;
    private double sumOfCommitScore;
    private List<DiffDTO> mergeRequestDiffs;
    private int linesAdded;
    private int linesRemoved;

    public MergeRequestDTO(int mergeId, Date mergedDate, Date createdDate, Date updatedDate, double MRScore, double sumOfCommitScore, List<DiffDTO> mergeRequestDiffs, int linesAdded, int linesRemoved) {
        this.mergeId = mergeId;
        this.mergedDate = mergedDate;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.MRScore = MRScore;
        this.sumOfCommitScore = sumOfCommitScore;
        this.mergeRequestDiffs = mergeRequestDiffs;
        this.linesAdded = linesAdded;
        this.linesRemoved = linesRemoved;
    }

    public int getMergeId() {
        return mergeId;
    }

    public Date getMergedDate() {
        return mergedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public double getMRScore() {
        return MRScore;
    }

    public double getSumOfCommitScore() {
        return sumOfCommitScore;
    }

    public List<DiffDTO> getMergeRequestDiffs() {
        return mergeRequestDiffs;
    }

    public int getLinesAdded() {
        return linesAdded;
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }
}
