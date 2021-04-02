package com.haumea.gitanalyzer.dto;

import java.util.List;
import java.util.Map;

public class ReportDTO {

    Map<String, List<MergeRequestDTO>> mergeRequestListByMemberId;
    Map<String, List<CommitDTO>> commitListByMemberId;
    List<String> userList;

    public ReportDTO(Map<String, List<MergeRequestDTO>> mergeRequestListByMemberId, Map<String, List<CommitDTO>> commitListByMemberId, List<String> userList) {
        this.mergeRequestListByMemberId = mergeRequestListByMemberId;
        this.commitListByMemberId = commitListByMemberId;
        this.userList = userList;
    }

    public Map<String, List<MergeRequestDTO>> getMergeRequestListByMemberId() {
        return mergeRequestListByMemberId;
    }

    public Map<String, List<CommitDTO>> getCommitListByMemberId() {
        return commitListByMemberId;
    }

    public List<String> getUserList() {
        return userList;
    }

}
