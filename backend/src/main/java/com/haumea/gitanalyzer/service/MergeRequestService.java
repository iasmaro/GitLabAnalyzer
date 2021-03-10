package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dto.DiffDTO;
import com.haumea.gitanalyzer.dto.DiffScoreDTO;
import com.haumea.gitanalyzer.gitlab.CommentType;
import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.gitlab.IndividualDiffScoreCalculator;
import com.haumea.gitanalyzer.gitlab.MergeRequestWrapper;
import com.haumea.gitanalyzer.dto.MergeRequestDTO;
import com.haumea.gitanalyzer.utility.GlobalConstants;
import org.gitlab4j.api.models.Diff;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.MergeRequestDiff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MergeRequestService {

    private final UserService userService;
    private final MemberService memberService;
    private int linesAdded;
    private int linesRemoved;
    private double MRScore;

    @Autowired
    public MergeRequestService(UserService userService, MemberService memberService) {

        this.userService = userService;
        this.memberService = memberService;

        this.linesAdded = 0;
        this.linesRemoved = 0;
        this.MRScore = 0.0;
    }

    private GitlabService getGitLabService(String userId) {

        String accessToken = userService.getPersonalAccessToken(userId);

        return new GitlabService(GlobalConstants.gitlabURL, accessToken);
    }

    private List<String> getAliasForMember(String memberId) {

        return memberService.getAliasesForSelectedMember(memberId);
    }

    private List<MergeRequestWrapper> getMergeRequestWrapper(GitlabService gitlabService, int projectId, Date start, Date end) {

        return gitlabService.getFilteredMergeRequestsWithDiff(projectId, "master", start, end);
    }

    private List<MergeRequestWrapper> getMergeRequestWrapperForMember(GitlabService gitlabService, int projectId, Date start, Date end, List<String> alias) {

        return gitlabService.getFilteredMergeRequestsWithDiffByAuthor(projectId, "master", start, end, alias);
    }

    //TODO: Update passing configuration file to calculator
    public List<DiffDTO> getMergeRequestDiffs(MergeRequestDiff mergeRequestDiff){

        IndividualDiffScoreCalculator diffScoreCalculator = new IndividualDiffScoreCalculator();

        this.linesAdded = 0;
        this.linesRemoved = 0;
        this.MRScore = 0.0;

        List<DiffDTO> mergeRequestDiffs = new ArrayList<>();

        List<CommentType> commentTypes = new ArrayList<>();
        commentTypes.add(new CommentType("//", ""));
        commentTypes.add(new CommentType("/*", "*/"));

        List<Diff> codeDiffs = mergeRequestDiff.getDiffs();

        for(Diff diff : codeDiffs) {

            DiffScoreDTO scoreDTO = diffScoreCalculator.calculateDiffScore(diff.getDiff(),
                                                                            diff.getDeletedFile(),
                                                                            1,
                                                                            0.2,
                                                                            0,
                                                                            0,
                                                                            1,
                                                                            commentTypes);

            DiffDTO diffDTO = new DiffDTO(diff.getDiff(), diff.getNewPath(), diff.getDiff(), scoreDTO);

            this.linesAdded = this.linesAdded + scoreDTO.getLinesAdded();
            this.linesRemoved = this.linesRemoved + scoreDTO.getLinesRemoved();
            this.MRScore = this.MRScore + scoreDTO.getDiffScore();

            mergeRequestDiffs.add(diffDTO);
        }

        return mergeRequestDiffs;
    }

    public MergeRequestDTO getMergeRequestDTO(MergeRequestWrapper mergeRequestWrapper){

        MergeRequest mergeRequest = mergeRequestWrapper.getMergeRequestData();

        int mergeRequestIiD = mergeRequest.getIid();
        String mergeRequestTitle = mergeRequest.getTitle();
        Date mergedDate = mergeRequest.getMergedAt();
        Date createdDate = mergeRequest.getCreatedAt();
        Date updatedDate = mergeRequest.getUpdatedAt();

        //TODO: Update with method to calculate MR's score and member's score.
        double sumOfCommitScore = 0.0;

        List<DiffDTO> mergeRequestDiffs = getMergeRequestDiffs(mergeRequestWrapper.getMergeRequestDiff());

        return new MergeRequestDTO(mergeRequestIiD, mergeRequestTitle, mergedDate, createdDate, updatedDate, this.MRScore, sumOfCommitScore, mergeRequestDiffs, this.linesAdded, this.linesRemoved);
    }

    public List<MergeRequestDTO> getAllMergeRequests(String userId, int projectId, Date start, Date end) {

        GitlabService gitlabService = getGitLabService(userId);

        List<MergeRequestWrapper> mergeRequestsList = getMergeRequestWrapper(gitlabService, projectId, start, end);

        List<MergeRequestDTO> mergeRequestDTOList = new ArrayList<>();

        for(MergeRequestWrapper mergeRequestWrapper : mergeRequestsList) {

            MergeRequestDTO mergeRequestDTO = getMergeRequestDTO(mergeRequestWrapper);
            mergeRequestDTOList.add(mergeRequestDTO);
        }

        return mergeRequestDTOList;
    }

    public List<MergeRequestDTO> getAllMergeRequestsForMember(String userId, int projectId, String memberId, Date start, Date end) {

        GitlabService gitlabService = getGitLabService(userId);

        List<String> alias = getAliasForMember(memberId);
        List<MergeRequestWrapper> mergeRequestsList = getMergeRequestWrapperForMember(gitlabService, projectId, start, end, alias);

        List<MergeRequestDTO> mergeRequestDTOList = new ArrayList<>();

        for(MergeRequestWrapper mergeRequestWrapper : mergeRequestsList) {

            MergeRequestDTO mergeRequestDTO = getMergeRequestDTO(mergeRequestWrapper);
            mergeRequestDTOList.add(mergeRequestDTO);
        }

        return mergeRequestDTOList;
    }
}
