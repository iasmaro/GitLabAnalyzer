package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dto.CommitDTO;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class MergeRequestService {

    private final UserService userService;
    private final MemberService memberService;
    private final CommitService commitService;

    private int linesAdded;
    private int linesRemoved;
    private double MRScore;

    @Autowired
    public MergeRequestService(UserService userService, MemberService memberService, CommitService commitService) {

        this.userService = userService;
        this.memberService = memberService;
        this.commitService = commitService;

        this.linesAdded = 0;
        this.linesRemoved = 0;
        this.MRScore = 0.0;
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

    private String getDiffExtension(String newPath) {

        for(int index = newPath.length() - 1; index >= 0; index--) {

            if(newPath.charAt(index) == '.') {
                return newPath.substring(index);
            }

        }

        return "No extension";
    }

    //TODO: Update passing configuration file to calculator
    private List<DiffDTO> getMergeRequestDiffs(MergeRequestDiff mergeRequestDiff) {

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

            String diffExtension = getDiffExtension(diff.getNewPath());

            DiffDTO diffDTO = new DiffDTO(diff.getOldPath(), diff.getNewPath(), diffExtension, diff.getDiff(), scoreDTO);

            this.linesAdded = this.linesAdded + scoreDTO.getLinesAdded();
            this.linesRemoved = this.linesRemoved + scoreDTO.getLinesRemoved();
            this.MRScore = this.MRScore + scoreDTO.getDiffScore();

            mergeRequestDiffs.add(diffDTO);
        }

        return mergeRequestDiffs;
    }

    //Source: Andrew's IndividualDiffScoreCalculator
    private double roundScore(double score) {

        BigDecimal roundedScore = new BigDecimal(Double.toString(score));
        roundedScore = roundedScore.setScale(2, RoundingMode.HALF_UP);

        return roundedScore.doubleValue();
    }

    private double getSumOfCommitsScore(List<CommitDTO> commitDTOList) {

        double sumOfCommitsScore = 0.0;

        for(CommitDTO commitDTO : commitDTOList) {

            sumOfCommitsScore = sumOfCommitsScore + commitDTO.getCommitScore();

        }

        return roundScore(sumOfCommitsScore);
    }

    private MergeRequestDTO getMergeRequestDTO(String userId, int projectId, MergeRequestWrapper mergeRequestWrapper) {

        MergeRequest mergeRequest = mergeRequestWrapper.getMergeRequestData();

        int mergeRequestIiD = mergeRequest.getIid();
        String mergeRequestTitle = mergeRequest.getTitle();
        Date mergedDate = mergeRequest.getMergedAt();
        Date createdDate = mergeRequest.getCreatedAt();
        Date updatedDate = mergeRequest.getUpdatedAt();

        List<DiffDTO> mergeRequestDiffs = getMergeRequestDiffs(mergeRequestWrapper.getMergeRequestDiff());
        List<CommitDTO> commitDTOList = commitService.getCommitsForSelectedMergeRequest(userId, projectId, mergeRequestIiD);

        double sumOfCommitScore = getSumOfCommitsScore(commitDTOList);

        return new MergeRequestDTO(mergeRequestIiD, mergeRequestTitle, mergedDate, createdDate, updatedDate, roundScore(this.MRScore), sumOfCommitScore, mergeRequestDiffs, this.linesAdded, this.linesRemoved, commitDTOList);
    }

    public List<MergeRequestDTO> getAllMergeRequests(String userId, int projectId, Date start, Date end) {

        GitlabService gitlabService = userService.createGitlabService(userId);

        List<MergeRequestWrapper> mergeRequestsList = getMergeRequestWrapper(gitlabService, projectId, start, end);

        List<MergeRequestDTO> mergeRequestDTOList = new ArrayList<>();

        for(MergeRequestWrapper mergeRequestWrapper : mergeRequestsList) {

            MergeRequestDTO mergeRequestDTO = getMergeRequestDTO(userId, projectId, mergeRequestWrapper);
            mergeRequestDTOList.add(mergeRequestDTO);
        }

        return mergeRequestDTOList;
    }

    public List<MergeRequestDTO> getAllMergeRequestsForMember(String userId, int projectId, String memberId, Date start, Date end) {

        GitlabService gitlabService = userService.createGitlabService(userId);

        List<String> alias = getAliasForMember(memberId);
        List<MergeRequestWrapper> mergeRequestsList = getMergeRequestWrapperForMember(gitlabService, projectId, start, end, alias);

        List<MergeRequestDTO> mergeRequestDTOList = new ArrayList<>();

        for(MergeRequestWrapper mergeRequestWrapper : mergeRequestsList) {

            MergeRequestDTO mergeRequestDTO = getMergeRequestDTO(userId, projectId, mergeRequestWrapper);
            mergeRequestDTOList.add(mergeRequestDTO);
        }

        return mergeRequestDTOList;
    }
}
