package com.haumea.gitanalyzer.dto;

import java.util.List;
import java.util.Map;

public class ReportDTO {

    private Map<String, List<MergeRequestDTO>> mergeRequestListByMemberId;
    private Map<String, List<CommitDTO>> commitListByMemberId;
    private Map<String, List<CommentDTO>> MRCommentListByMemberId;
    private Map<String, List<CommentDTO>> issueCommentListByMemberId;
    private List<String> userList;
    private int projectId;

    public ReportDTO(Map<String, List<MergeRequestDTO>> mergeRequestListByMemberId,
                     Map<String, List<CommitDTO>> commitListByMemberId,
                     Map<String, List<CommentDTO>> MRCommentListByMemberId,
                     Map<String, List<CommentDTO>> issueCommentListByMemberId,
                     List<String> userList,
                     int projectId) {

        this.mergeRequestListByMemberId = mergeRequestListByMemberId;
        this.commitListByMemberId = commitListByMemberId;
        this.MRCommentListByMemberId = MRCommentListByMemberId;
        this.issueCommentListByMemberId = issueCommentListByMemberId;
        this.userList = userList;
        this.projectId = projectId;
    }

    public Map<String, List<MergeRequestDTO>> getMergeRequestListByMemberId() {
        return mergeRequestListByMemberId;
    }

    public Map<String, List<CommitDTO>> getCommitListByMemberId() {
        return commitListByMemberId;
    }

    public Map<String, List<CommentDTO>> getMRCommentListByMemberId() {
        return MRCommentListByMemberId;
    }

    public Map<String, List<CommentDTO>> getIssueCommentListByMemberId() {
        return issueCommentListByMemberId;
    }

    public List<String> getUserList() {
        return userList;
    }

}
