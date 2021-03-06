package com.haumea.gitanalyzer.dto;

import java.util.Date;

public class ProjectDTO {
    private String projectName;
    private Integer projectId;
    private String projectURL;
    private Date createdAt;
    private Date updatedAt;
    private String namespace;

    public ProjectDTO(String projectName, Integer projectId, String projectURL, Date createdAt, Date updatedAt, String namespace) {
        this.projectName = projectName;
        this.projectId = projectId;
        this.projectURL = projectURL;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.namespace = namespace;
    }
    // getters needed to serialize object to json
    // setters needed to deserialize json to object

    public String getProjectName() {
        return projectName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public String getProjectURL() {
        return projectURL;
    }

    public Date getCreatedAt() { return createdAt; }

    public Date getUpdatedAt() { return updatedAt; }

    public String getNamespace() { return namespace; }
}
