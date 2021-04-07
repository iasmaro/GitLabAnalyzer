package com.haumea.gitanalyzer.dto;

public class CommentDTO {
    private String url;
    private String title;
    private String commentDescription;
    private boolean isOnOwnRequestOrIssue;
    private String creationDate;

    public CommentDTO(String url, String title, String commentDescription, boolean isOnOwnRequestOrIssue, String creationDate) {
        this.url = url;
        this.title = title;
        this.commentDescription = commentDescription;
        this.isOnOwnRequestOrIssue = isOnOwnRequestOrIssue;
        this.creationDate = creationDate;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() { return title; }

    public String getCommentDescription() {
        return commentDescription;
    }

    public boolean isOnOwnRequestOrIssue() {
        return isOnOwnRequestOrIssue;
    }

    public String getCreationDate() {
        return creationDate;
    }
}
