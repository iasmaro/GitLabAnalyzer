package com.haumea.gitanalyzer.gitlab;

import org.gitlab4j.api.models.Note;

public class CommentIssueWrapper extends CommentWrapper{

    String issueURL;

    public CommentIssueWrapper(Note note, Boolean isOwn, String issueURL) {
        super(note, isOwn);
        this.issueURL = issueURL;
    }

    public String getIssueURL() { return issueURL; }

    public String getAuthor() {
        return getNote().getAuthor().getUsername();
    }
}
