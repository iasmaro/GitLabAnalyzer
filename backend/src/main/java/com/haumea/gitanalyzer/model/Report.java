package com.haumea.gitanalyzer.model;

import com.haumea.gitanalyzer.dto.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Map;

//@Document(collection = "Reports")
public class Report {
    @Id
    private String id;

    private int projectId;
    private Date start;
    private Date end;

    @NotBlank
    @Field
    private Map<String, List<MergeRequestDTO>> mergeRequestListByMemberId;
    @Field
    private Map<String, List<CommitDTO>> commitListByMemberId;
    @Field
    private Map<String, List<CommentDTO>> MRCommentListByMemberId;
    @Field
    private Map<String, List<CommentDTO>> issueCommentListByMemberId;
    @Field
    private Map<String, List<CommitGraphDTO>> commitGraphListByMemberId;
    @Field
    private Map<String, List<MergeRequestGraphDTO>> MRGraphListByMemberId;
    @Field
    private Map<String, List<CodeReviewGraphDTO>> codeReviewGraphListByMemberId;
    @Field
    private Map<String, List<IssueGraphDTO>> issueGraphListByMemberId;
    @Field
    private List<String> userList;



    @PersistenceConstructor
    public Report(int projectId,
                     Date start,
                     Date end,
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
        this.start = start;
        this.end = end;
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

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
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

