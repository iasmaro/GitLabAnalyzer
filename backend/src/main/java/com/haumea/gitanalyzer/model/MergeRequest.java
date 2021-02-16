package com.haumea.gitanalyzer.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

public class MergeRequest {

    private int mergeID;
    private Date mergeDate;
    private double MRScore;
    private double memberScore;


    public MergeRequest( int mergeID, Date mergeDate, double MRScore, double memberScore) {
        this.mergeID = mergeID;
        this.mergeDate = mergeDate;
        this.MRScore = MRScore;
        this.memberScore = memberScore;
    }

    public int getMergeID() {
        return mergeID;
    }

    public Date getMergeDate() {
        return mergeDate;
    }

    public double getMRScore() {
        return MRScore;
    }

    public double getMemberScore() {
        return memberScore;
    }

    public int getSumOfCommitScore(String userID){

        return 0;
    }
}
