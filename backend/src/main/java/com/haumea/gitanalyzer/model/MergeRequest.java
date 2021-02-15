package com.haumea.gitanalyzer.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

public class MergeRequest {

    private String userID;
    private int projectID;
    private String memberID;

    private int mergeID;
    private Date mergeDate;
    private double MRScore;
    private double memberScore;


    public MergeRequest(String userID, int projectID, String memberID, int mergeID, Date mergeDate, double MRScore, double memberScore) {
        this.userID = userID;
        this.projectID = projectID;
        this.memberID = memberID;
        this.mergeID = mergeID;
        this.mergeDate = mergeDate;
        this.MRScore = MRScore;
        this.memberScore = memberScore;
    }

    public String getUserID() {
        return userID;
    }

    public int getProjectID() {
        return projectID;
    }

    public String getMemberID() {
        return memberID;
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
