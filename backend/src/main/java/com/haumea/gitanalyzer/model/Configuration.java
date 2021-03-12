package com.haumea.gitanalyzer.model;

import com.haumea.gitanalyzer.gitlab.CommentType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration {
    private ObjectId _id;
    private String fileName;
    private Date start;
    private Date end;
    private String targetBranch;
    private Map<String, Float> editFactor;
    private Map<String, Float> fileFactor;
    private List<String> ignoreFileExtension;
    private Map<String, List<CommentType>> commentTypes;

    public Configuration() {
        super();
    }

    @PersistenceConstructor
    public Configuration(String fileName, Date start, Date end,
                         String targetBranch, Map<String, Float> editFactor,
                         Map<String, Float> fileFactor, List<String> ignoreFileExtension,
                         Map<String, List<CommentType>> commentTypes) {
        this._id = new ObjectId();
        this.fileName = fileName;
        this.start = start;
        this.end = end;
        this.targetBranch = targetBranch;
        this.editFactor = editFactor;
        this.fileFactor = fileFactor;
        this.ignoreFileExtension = ignoreFileExtension;
        this.commentTypes = commentTypes;
    }

    public String getFileName(){
        return fileName;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getTargetBranch() {
        return targetBranch;
    }

    public Map<String, Float> getEditFactor(){
        return editFactor;
    }

    public Map<String, Float> getFileFactor(){
        return fileFactor;
    }

    public List<String> getIgnoreFileExtension(){
        return ignoreFileExtension;
    }

    public Map<String, List<CommentType>> getCommentTypes() {
        return commentTypes;
    }

}
