package com.haumea.gitanalyzer.gitlab;

import org.gitlab4j.api.models.Note;

public class CommentMRWrapper extends CommentWrapper{

    String mergeRequestURL;

    public CommentMRWrapper(Note note, Boolean isOwn, String mergeRequestURL) {
        super(note, isOwn);
        this.mergeRequestURL = mergeRequestURL;
    }

    public String getMergeRequestURL() { return mergeRequestURL; }

    public String getAuthor() {
        return getNote().getAuthor().getUsername();
    }
}
