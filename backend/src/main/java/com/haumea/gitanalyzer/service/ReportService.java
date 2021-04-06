package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dao.ReportRepository;
import com.haumea.gitanalyzer.dto.*;
import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.model.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final MergeRequestService mergeRequestService;
    private final CommitService commitService;
    private final CommentService commentService;
    private final GraphService graphService;

    private final UserService userService;
    private final MemberService memberService;

    @Autowired
    public ReportService(ReportRepository reportRepository, MergeRequestService mergeRequestService, CommitService commitService, CommentService commentService, GraphService graphService, UserService userService, MemberService memberService) {
        this.reportRepository = reportRepository;
        this.mergeRequestService = mergeRequestService;
        this.commitService = commitService;
        this.commentService = commentService;
        this.graphService = graphService;
        this.userService = userService;
        this.memberService = memberService;
    }

    public ReportDTO getReportForRepository(String userId, int projectId) {

        Map<String, List<MergeRequestDTO>> mergeRequestListByMemberId = new HashMap<>();
        Map<String, List<CommitDTO>> commitListByMemberId = new HashMap<>();

        Map<String, List<CommentDTO>> MRCommentListByMemberId = new HashMap<>();
        Map<String, List<CommentDTO>> issueCommentListByMemberId = new HashMap<>();

        Map<String, List<CommitGraphDTO>> commitGraphListByMemberId = new HashMap<>();
        Map<String, List<MergeRequestGraphDTO>> MRGraphListByMemberId = new HashMap<>();
        Map<String, List<CodeReviewGraphDTO>> codeReviewGraphListByMemberId = new HashMap<>();
        Map<String, List<IssueGraphDTO>> issueGraphListByMemberId = new HashMap<>();

        List<String> userList = new ArrayList<>();

        userList.add(userId);

        Date start = userService.getStart(userId);
        Date end = userService.getEnd(userId);

        List<String> memberList = memberService.getMembers(userId, projectId);


        for(String member : memberList) {

            List<MergeRequestDTO> mergeRequestDTOs = mergeRequestService.getAllMergeRequestsForMember(userId, projectId, member);
            List<CommitDTO> commits = commitService.getCommitsForSelectedMemberAndDate(userId, projectId, member);

            List<CommentDTO> MRComments = commentService.getMergeRequestComments(userId, projectId, member);
            List<CommentDTO> issueComments = commentService.getMergeRequestComments(userId, projectId, member);

            List<CommitGraphDTO> commitGraphs = graphService.getCommitGraphDetails(userId, member, projectId);
            List<MergeRequestGraphDTO> MRGraphs = graphService.getMergeRequestGraphDetails(userId, member, projectId);
            List<CodeReviewGraphDTO> codeReviewGraphs = graphService.getCodeReviewGraphDetails(userId, member, projectId);
            List<IssueGraphDTO> issueGraphs = graphService.getIssueGraphDetails(userId, member, projectId);

            mergeRequestListByMemberId.put(member, mergeRequestDTOs);
            commitListByMemberId.put(member, commits);

            MRCommentListByMemberId.put(member, MRComments);
            issueCommentListByMemberId.put(member, issueComments);

            commitGraphListByMemberId.put(member, commitGraphs);
            MRGraphListByMemberId.put(member, MRGraphs);
            codeReviewGraphListByMemberId.put(member, codeReviewGraphs);
            issueGraphListByMemberId.put(member, issueGraphs);

        }

        GitlabService gitlabService = userService.createGitlabService(userId);

        return new ReportDTO(
                projectId,
                start,
                end,
                mergeRequestListByMemberId,
                commitListByMemberId,
                MRCommentListByMemberId,
                issueCommentListByMemberId,
                commitGraphListByMemberId,
                MRGraphListByMemberId,
                codeReviewGraphListByMemberId,
                issueGraphListByMemberId,
                userList,
                userService.getActiveConfig(userId),
                gitlabService.getSelectedProject(projectId).getName(),
                gitlabService.getSelectedProject(projectId).getNamespace().getName());

    }

    public void saveReport(ReportDTO reportDTO) {
        reportRepository.saveReportToDatabase(reportDTO);
    }

    public Optional<ReportDTO> checkIfInDb(String userId,int projectId) {

        return reportRepository.findReportInDb(
                projectId,
                userService.getStart(userId),
                userService.getEnd(userId),
                userService.getActiveConfig(userId));
    }

    public List<ReportDTO> getAllReports() {
        return reportRepository.getAllReportsInDb();
    }
}
