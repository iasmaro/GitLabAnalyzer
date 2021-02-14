package com.haumea.gitanalyzer.dto;

public class ProjectDTO {
    private String projectName;
    private Integer projectId;
    private String projectURL;

    public ProjectDTO(String projectName, Integer projectId, String projectURL) {
        this.projectName = projectName;
        this.projectId = projectId;
        this.projectURL = projectURL;
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

}
