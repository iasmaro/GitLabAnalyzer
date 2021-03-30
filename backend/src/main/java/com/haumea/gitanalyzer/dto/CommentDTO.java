package com.haumea.gitanalyzer.dto;

public class CommentDTO {
    private String url;
    private String commentDescription;
    private boolean isOnOwnRequestOrIssue;
    private String creationDate;

    public CommentDTO(String url, String commentDescription, boolean isOnOwnRequestOrIssue, String creationDate) {
        this.url = url;
        this.commentDescription = commentDescription;
        this.isOnOwnRequestOrIssue = isOnOwnRequestOrIssue;
        this.creationDate = creationDate;
    }

    public String getUrl() {
        return url;
    }

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
