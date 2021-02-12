package com.haumea.gitanalyzer.model;

public class MemberRequestDTO {
    private String username;
    private Integer projectId;

    public MemberRequestDTO(String username, Integer projectId) {
        this.username = username;
        this.projectId = projectId;
    }

    public String getUsername() {
        return username;
    }

    public Integer getProjectId() {
        return projectId;
    }
}
