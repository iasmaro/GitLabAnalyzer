package com.haumea.gitanalyzer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Date;
import java.util.List;
import java.util.HashMap;

public class Configuration {

    private String fileName;
    private Date start;
    private Date end;
    private String gitlabServer;
    private String targetBranch;
    private HashMap<String, Float> editFactor;
    private HashMap<String, Float> fileFactor;
    private List<String> ignoreFileExtension;

    // replace with commentType after it's merged
    private String startType;
    private String endType;


    public Configuration() {
        super();
    }

    @PersistenceConstructor
    public Configuration(String fileName, Date start, Date end, String gitlabServer,
                         String targetBranch, HashMap<String, Float> editFactor,
                         HashMap<String, Float> fileFactor, List<String> ignoreFileExtension,
                         String startType, String endType) {

        this.fileName = fileName;
        this.start = start;
        this.end = end;
        this.gitlabServer = gitlabServer;
        this.targetBranch = targetBranch;
        this.editFactor = editFactor;
        this.fileFactor = fileFactor;
        this.ignoreFileExtension = ignoreFileExtension;
        this.startType = startType;
        this.endType = endType;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getGitlabServer() {
        return gitlabServer;
    }

    public String getTargetBranch() {
        return targetBranch;
    }

    public HashMap<String, Float> getEditFactor(){
        return editFactor;
    }

    public HashMap<String, Float> getFileFactor(){
        return fileFactor;
    }

    public List<String> getIgnoreFileExtension(){
        return ignoreFileExtension;
    }

    public String getStartType() {
        return startType;
    }

    public String getEndType() {
        return endType;
    }
}
