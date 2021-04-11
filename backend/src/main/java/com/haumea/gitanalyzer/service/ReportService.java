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
                userService.getConfiguration(userId).getFileName(),
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
                userService.getConfiguration(userId).getFileName());
    }

    public Optional<ReportDTO> checkIfInDbViaName(String reportName) {
        return reportRepository.findReportInDbViaName(reportName);
    }

    public List<ReportDTO> getAllReports() {
        return reportRepository.getAllReportsInDb();
    }

    public void deleteReport(String reportName) {
        reportRepository.deleteReportDTO(reportName);
    }

    private double getDiffScore(DiffDTO modifiedDiff) {
        return modifiedDiff.getScoreDTO().getScore();
    }

    private double getExtensionScoreOfMR(MergeRequestDTO modifiedMR, String extension) {
        return modifiedMR.getScoreByFileTypes().getOrDefault(extension, 0.0);
    }

    private double getExtensionScoreOfCommit(CommitDTO modifiedCommit, String extension) {
        return modifiedCommit.getScoreByFileTypes().getOrDefault(extension, 0.0);
    }

    private double getNewScoreDifference(DiffDTO modifiedDiff, double newDiffScore) {

        double originalDiffScore = modifiedDiff.getScoreDTO().getScore();
        return newDiffScore - originalDiffScore;
    }

    private boolean scoreHasBeenModified(DiffDTO diffDTO) {
        return diffDTO.getScoreDTO().getModifiedScore() != -1;
    }

    private double getOriginalScore(double score, DiffDTO modifiedDiff) {
        if(scoreHasBeenModified(modifiedDiff)) {
            ScoreDTO modifiedScoreDTO = modifiedDiff.getScoreDTO();
            double difference = modifiedScoreDTO.getModifiedScore() - modifiedScoreDTO.getScore();

            score = score - difference;
        }

        return score;
    }

    private double getNewScore(double oldScore, double difference) {

        oldScore = oldScore + difference;

        ScoreDTO roundObject = new ScoreDTO();

        return roundObject.roundScore(oldScore);
    }

    public void modifyDiffScoreOfMRDiff(String reportName, String memberId, int mergeIndex, int diffIndex, double newDiffScore) {

        MergeRequestDTO modifiedMR = reportRepository.getModifiedMergeRequestByMemberId(reportName, memberId, mergeIndex);
        DiffDTO modifiedDiff = modifiedMR.getMergeRequestDiffs().get(diffIndex);

        double difference = getNewScoreDifference(modifiedDiff, newDiffScore);

        double MRScore = getOriginalScore(modifiedMR.getMRScore(), modifiedDiff);

        double newMRScore = getNewScore(MRScore, difference);

        String extension = modifiedDiff.getExtension();
        double extensionScore = getExtensionScoreOfMR(modifiedMR, extension);
        extensionScore = getOriginalScore(extensionScore, modifiedDiff);

        double newExtensionScore = getNewScore(extensionScore, difference);

        reportRepository.updateDBWithNewDiffSCoreOfMR(reportName,
                                                      memberId,
                                                      mergeIndex,
                                                      diffIndex,
                                                      newDiffScore,
                                                      newMRScore,
                                                      extension,
                                                      newExtensionScore);

    }

    private boolean isOwnCommitOnSharedMR(String memberId, String commitAuthor) {
        List<String> memberAlias = memberService.getAliasesForSelectedMember(memberId);

        return memberAlias.contains(commitAuthor);
    }

    public void modifyDiffScoreOfCommitInOneMR(String reportName, String memberId, int mergeIndex, int commitIndex, int diffIndex, double newDiffScore) {
        MergeRequestDTO modifiedMR = reportRepository.getModifiedMergeRequestByMemberId(reportName, memberId, mergeIndex);
        CommitDTO modifiedCommit = modifiedMR.getCommitDTOList().get(commitIndex);
        DiffDTO modifiedDiff = modifiedCommit.getCommitDiffs().get(diffIndex);

        double difference = getNewScoreDifference(modifiedDiff, newDiffScore);

        double commitScore = getOriginalScore(modifiedCommit.getCommitScore(), modifiedDiff);

        double newCommitScore = getNewScore(commitScore, difference);

        String extension = modifiedDiff.getExtension();
        double extensionScore = getExtensionScoreOfCommit(modifiedCommit, extension);
        extensionScore = getOriginalScore(extensionScore, modifiedDiff);

        double newExtensionScore = getNewScore(extensionScore, difference);

        double sumOfCommitScore = getOriginalScore(modifiedMR.getSumOfCommitScore(), modifiedDiff);
        double newSumOfCommitScore = getNewScore(sumOfCommitScore, difference);

        reportRepository.updateDBWithNewDiffScoreOfOneCommitInMR(reportName,
                                                                 memberId,
                                                                 mergeIndex,
                                                                 diffIndex,
                                                                 commitIndex,
                                                                 newDiffScore,
                                                                 newCommitScore,
                                                                 extension,
                                                                 newExtensionScore,
                                                                 newSumOfCommitScore);

        if(modifiedMR.isSharedMR() && isOwnCommitOnSharedMR(memberId, modifiedCommit.getCommitAuthor())) {
            double sumOfCommitScoreOnSharedMR = getOriginalScore(modifiedMR.getSumOfCommitScoreOnSharedMR(), modifiedDiff);
            double newSumOfCommitScoreOnSharedMR = getNewScore(sumOfCommitScoreOnSharedMR, difference);

            reportRepository.updateSumOfCommitScoreOnSharedMR(reportName, memberId, mergeIndex, newSumOfCommitScoreOnSharedMR);
        }

    }

    public void modifyDiffScoreOfCommit(String reportName, String memberId, int commitIndex, int diffIndex, double newDiffScore) {
        CommitDTO modifiedCommit = reportRepository.getModifiedCommitByMemberId(reportName, memberId, commitIndex);
        DiffDTO modifiedDiff = modifiedCommit.getCommitDiffs().get(diffIndex);

        double difference = getNewScoreDifference(modifiedDiff, newDiffScore);

        double commitScore = getOriginalScore(modifiedCommit.getCommitScore(), modifiedDiff);

        double newCommitScore = getNewScore(commitScore, difference);

        String extension = modifiedDiff.getExtension();
        double extensionScore = getExtensionScoreOfCommit(modifiedCommit, extension);
        extensionScore = getOriginalScore(extensionScore, modifiedDiff);

        double newExtensionScore = getNewScore(extensionScore, difference);

        reportRepository.updateDBWithNewDiffScoreOfCommit(reportName,
                                                          memberId,
                                                          commitIndex,
                                                          diffIndex,
                                                          newDiffScore,
                                                          newCommitScore,
                                                          extension,
                                                          newExtensionScore);
    }

}