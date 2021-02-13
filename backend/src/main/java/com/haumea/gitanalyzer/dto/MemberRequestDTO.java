package com.haumea.gitanalyzer.dto;

public class MemberRequestDTO {
    private String userId;
    private Integer projectId;

    public MemberRequestDTO(String userId, Integer projectId) {
        this.userId = userId;
        this.projectId = projectId;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getProjectId() {
        return projectId;
    }
}
