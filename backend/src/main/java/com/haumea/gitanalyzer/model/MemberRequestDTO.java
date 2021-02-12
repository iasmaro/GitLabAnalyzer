package com.haumea.gitanalyzer.model;

public class MemberRequestDTO {
    private String memberId;
    private Integer projectId;

    public MemberRequestDTO(String memberId, Integer projectId) {
        this.memberId = memberId;
        this.projectId = projectId;
    }

    public String getMemberId() {
        return memberId;
    }

    public Integer getProjectId() {
        return projectId;
    }
}
