package com.haumea.gitanalyzer.dto;

public class CommentDTO {
    private String url;
    private String commentDescription;
    private boolean isOnOwnRequest;
    private String creationDate;

    public CommentDTO(String url, String commentDescription, boolean isOnOwnRequest, String creationDate) {
        this.url = url;
        this.commentDescription = commentDescription;
        this.isOnOwnRequest = isOnOwnRequest;
        this.creationDate = creationDate;
    }

    public String getUrl() {
        return url;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public boolean isOnOwnRequest() {
        return isOnOwnRequest;
    }

    public String getCreationDate() {
        return creationDate;
    }
}
