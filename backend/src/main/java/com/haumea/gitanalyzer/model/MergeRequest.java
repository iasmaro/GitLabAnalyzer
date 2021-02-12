package com.haumea.gitanalyzer.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "merge requests")
public class MergeRequest {

    private String userID;
    private int projectID;
    private List<String> memberID;

    private int mergeID;
    private Date mergeDate;
    private int MRdiffScore;

    public MergeRequest(String userID, int projectID, List<String> memberID, int mergeID, Date mergeDate, int MRdiffScore) {
        this.userID = userID;
        this.projectID = projectID;
        this.memberID = memberID;
        this.mergeID = mergeID;
        this.mergeDate = mergeDate;
        this.MRdiffScore = MRdiffScore;
    }

    public String getUserID() {
        return userID;
    }

    public int getProjectID() {
        return projectID;
    }

    public List<String> getMemberID() {
        return memberID;
    }

    public int getMergeID() {
        return mergeID;
    }

    public Date getMergeDate() {
        return mergeDate;
    }

    public int getMRdiffScore() {
        return MRdiffScore;
    }

    public int getSumOfCommitScore(String userID){

        return 0;
    }
}
