package com.haumea.gitanalyzer.gitlab;

import org.gitlab4j.api.models.Note;

public class CommentWrapper {

    private Note note;
    private Boolean isOwn;

    public CommentWrapper(Note note, Boolean isOwn) {
        this.note = note;
        this.isOwn = isOwn;
    }

    public Note getNote() { return note; }

    public Boolean getOwn() { return isOwn; }
}
