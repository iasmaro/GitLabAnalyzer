package com.haumea.gitanalyzer.dto;

import java.util.List;
import java.util.Map;

public class ReportDTO {

    int projectId;
    private Map<String, List<MergeRequestDTO>> mergeRequestListByMemberId;
    private Map<String, List<CommitDTO>> commitListByMemberId;

    private Map<String, List<CommentDTO>> MRCommentListByMemberId;
    private Map<String, List<CommentDTO>> issueCommentListByMemberId;

    private Map<String, List<CommitGraphDTO>> commitGraphListByMemberId;
    private Map<String, List<MergeRequestGraphDTO>> MRGraphListByMemberId;
    private Map<String, List<CodeReviewGraphDTO>> codeReviewGraphListByMemberId;
    private Map<String, List<IssueGraphDTO>> issueGraphListByMemberId;

    private List<String> userList;

    public ReportDTO(int projectId,
                     Map<String, List<MergeRequestDTO>> mergeRequestListByMemberId,
                     Map<String, List<CommitDTO>> commitListByMemberId,
                     Map<String, List<CommentDTO>> MRCommentListByMemberId,
                     Map<String, List<CommentDTO>> issueCommentListByMemberId,
                     Map<String, List<CommitGraphDTO>> commitGraphListByMemberId,
                     Map<String, List<MergeRequestGraphDTO>> MRGraphListByMemberId,
                     Map<String, List<CodeReviewGraphDTO>> codeReviewGraphListByMemberId,
                     Map<String, List<IssueGraphDTO>> issueGraphListByMemberId,
                     List<String> userList) {
        this.projectId = projectId;
        this.mergeRequestListByMemberId = mergeRequestListByMemberId;
        this.commitListByMemberId = commitListByMemberId;
        this.MRCommentListByMemberId = MRCommentListByMemberId;
        this.issueCommentListByMemberId = issueCommentListByMemberId;
        this.commitGraphListByMemberId = commitGraphListByMemberId;
        this.MRGraphListByMemberId = MRGraphListByMemberId;
        this.codeReviewGraphListByMemberId = codeReviewGraphListByMemberId;
        this.issueGraphListByMemberId = issueGraphListByMemberId;
        this.userList = userList;
    }

    public int getProjectId() {
        return projectId;
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

    public Map<String, List<CommitGraphDTO>> getCommitGraphListByMemberId() {
        return commitGraphListByMemberId;
    }

    public Map<String, List<MergeRequestGraphDTO>> getMRGraphListByMemberId() {
        return MRGraphListByMemberId;
    }

    public Map<String, List<CodeReviewGraphDTO>> getCodeReviewGraphListByMemberId() {
        return codeReviewGraphListByMemberId;
    }

    public Map<String, List<IssueGraphDTO>> getIssueGraphListByMemberId() {
        return issueGraphListByMemberId;
    }

    public List<String> getUserList() {
        return userList;
    }
}
