package com.haumea.gitanalyzer.gitlab;

import org.gitlab4j.api.models.Note;

public class CommentWrapper {

    private String url;
    private String title;
    private Note note;
    private Boolean isSameAuthor;

    public CommentWrapper(String url, String title, Note note, Boolean isSameAuthor) {
        this.url = url;
        this.title = title;
        this.note = note;
        this.isSameAuthor = isSameAuthor;
    }

    public String getUrl() { return url; }

    public String getTitle() { return title; }

    public Note getNote() { return note; }

    public Boolean getIsSameAuthor() { return isSameAuthor; }

    public String getAuthor() {
        return getNote().getAuthor().getUsername();
    }
}
