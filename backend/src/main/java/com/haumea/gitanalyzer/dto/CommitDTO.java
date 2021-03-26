package com.haumea.gitanalyzer.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class CommitDTO {
    private String commitMessage;
    private Date commitDate;
    private String commitAuthor;
    private String commitLink;
    private double commitScore;
    private Map<String, Double> scoreByFileTypes;
    private List<DiffDTO> commitDiffs;
    private int linesAdded;
    private int linesRemoved;

    public CommitDTO(String commitMessage, Date commitDate, String commitAuthor, String commitLink, double commitScore, Map<String, Double> scoreByFileTypes, List<DiffDTO> commitDiffs, int linesAdded, int linesRemoved) {
        this.commitMessage = commitMessage;
        this.commitDate = commitDate;
        this.commitAuthor = commitAuthor;
        this.commitLink = commitLink;
        this.commitScore = commitScore;
        this.scoreByFileTypes = scoreByFileTypes;
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

    public String getCommitLink() {
        return commitLink;
    }

    public double getCommitScore() {
        return commitScore;
    }

    public Map<String, Double> getScoreByFileTypes() {
        return scoreByFileTypes;
    }

    public List<DiffDTO> getCommitDiffs() {
        return commitDiffs;
    }

    public int getLinesAdded() {
        return linesAdded;
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }
}
