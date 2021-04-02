package com.haumea.gitanalyzer.dto;

import java.util.List;
import java.util.Map;

public class ReportDTO {

    private List<MergeRequestDTO> allMergeRequestList;

    private Map<String, List<MergeRequestDTO>> mergeRequestListByMemberId;
    private Map<String, List<CommitDTO>> commitListByMemberId;
    private Map<String, List<CommentDTO>> commentListByMemberId;
    private List<String> userList;

    public ReportDTO(List<MergeRequestDTO> allMergeRequestList, Map<String, List<MergeRequestDTO>> mergeRequestListByMemberId, Map<String, List<CommitDTO>> commitListByMemberId, Map<String, List<CommentDTO>> commentListByMemberId, List<String> userList) {
        this.allMergeRequestList = allMergeRequestList;
        this.mergeRequestListByMemberId = mergeRequestListByMemberId;
        this.commitListByMemberId = commitListByMemberId;
        this.commentListByMemberId = commentListByMemberId;
        this.userList = userList;
    }

    public List<MergeRequestDTO> getAllMergeRequestList() {
        return allMergeRequestList;
    }

    public Map<String, List<MergeRequestDTO>> getMergeRequestListByMemberId() {
        return mergeRequestListByMemberId;
    }

    public Map<String, List<CommitDTO>> getCommitListByMemberId() {
        return commitListByMemberId;
    }

    public Map<String, List<CommentDTO>> getCommentListByMemberId() {
        return commentListByMemberId;
    }

    public List<String> getUserList() {
        return userList;
    }

}
