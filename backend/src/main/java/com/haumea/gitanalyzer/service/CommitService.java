package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dto.CommitDTO;
import com.haumea.gitanalyzer.dto.DiffDTO;
import com.haumea.gitanalyzer.dto.DiffScoreDTO;
import com.haumea.gitanalyzer.exception.GitLabRuntimeException;
import com.haumea.gitanalyzer.gitlab.CommentType;
import com.haumea.gitanalyzer.gitlab.CommitWrapper;
import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.gitlab.IndividualDiffScoreCalculator;
import com.haumea.gitanalyzer.utility.GlobalConstants;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Diff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class CommitService {

    private final UserService userService;
    private final MemberService memberService;

    private int linesAdded;
    private int linesRemoved;
    private double commitScore;

    @Autowired
    public CommitService(UserService userService, MemberService memberService) {
        this.userService = userService;
        this.memberService = memberService;

        this.linesAdded = 0;
         this.linesRemoved = 0;
        this.commitScore = 0.0;
    }

    private List<String> getAliasForMember(String memberId) {

        List<String> alias = memberService.getAliasesForSelectedMember(memberId);

        return alias;
    }

    private GitlabService createGitlabService(String userId) {
        String token = userService.getPersonalAccessToken(userId);

        return new GitlabService(GlobalConstants.gitlabURL, token);
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
    private List<DiffDTO> getCommitDiffs(List<Diff> codeDiffs) {

        IndividualDiffScoreCalculator diffScoreCalculator = new IndividualDiffScoreCalculator();

        this.linesAdded = 0;
        this.linesRemoved = 0;
        this.commitScore = 0.0;

        List<DiffDTO> commitDiffs = new ArrayList<>();

        List<CommentType> commentTypes = new ArrayList<>();

        commentTypes.add(new CommentType("//", ""));
        commentTypes.add(new CommentType("/*", "*/"));

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
            this.commitScore = this.commitScore + scoreDTO.getDiffScore();

            commitDiffs.add(diffDTO);
        }

        return commitDiffs;
    }

    //Source: Andrew's IndividualDiffScoreCalculator
    private double roundScore(double commitScore) {

        BigDecimal roundedScore = new BigDecimal(Double.toString(commitScore));
        roundedScore = roundedScore.setScale(2, RoundingMode.HALF_UP);

        return roundedScore.doubleValue();
    }

    private List<CommitDTO> convertCommitWrappersToDTOs(List<CommitWrapper> wrapperList) {

        List<CommitDTO> commitDTOList = new ArrayList<>();

        for(CommitWrapper currentCommit : wrapperList) {

            Commit commit = currentCommit.getCommitData();

            List<DiffDTO> commitDiffs = getCommitDiffs(currentCommit.getNewCode());

            double roundedCommitScore = roundScore(this.commitScore);

            CommitDTO newDTO = new CommitDTO(commit.getMessage(), commit.getCommittedDate(), commit.getAuthorName(), roundedCommitScore, commitDiffs, this.linesAdded, this.linesRemoved);

            commitDTOList.add(newDTO);
        }

        return commitDTOList;
    }

    public List<CommitDTO> getMergeRequestCommitsForMember(String userId, Integer projectId,
                                                           Integer mergeRequestId, String memberId) throws GitLabRuntimeException {

       GitlabService gitlabService = createGitlabService(userId);

        List<String> alias = getAliasForMember(memberId);

        try {
            List<CommitWrapper> mergeRequestCommits = gitlabService.getMergeRequestCommitsWithDiffByAuthor(projectId, mergeRequestId, alias);

            return convertCommitWrappersToDTOs(mergeRequestCommits);
        }
        catch (GitLabRuntimeException e) {
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }
    }

    public List<CommitDTO> getCommitsForSelectedMemberAndDate(String userId, int projectId, String memberId, Date start, Date end) {

        GitlabService gitlabService = createGitlabService(userId);
        List<CommitWrapper> filteredCommits;

        List<String> alias = getAliasForMember(memberId);

        filteredCommits = gitlabService.getFilteredCommitsWithDiffByAuthor(projectId, "master", start, end, alias);

        return convertCommitWrappersToDTOs(filteredCommits);
    }

    public List<CommitDTO> getOrphanCommitsForSelectedMemberAndDate(String userId, int projectId, String targetBranch, String memberId, Date start, Date end) {

        GitlabService gitlabService = createGitlabService(userId);
        List<CommitWrapper> filteredCommits;

        List<String> alias = getAliasForMember(memberId);

        filteredCommits = gitlabService.getOrphanFilteredCommitsWithDiffByAuthor(projectId, targetBranch, start, end, alias);

        return convertCommitWrappersToDTOs(filteredCommits);

    }

    public List<CommitDTO> getCommitsForSelectedMergeRequest(String userId, int projectId, int mergeRequestId) {

        GitlabService gitlabService = createGitlabService(userId);
        List<CommitWrapper> mergeRequestCommits;

        try {
            mergeRequestCommits = gitlabService.getMergeRequestCommitsWithDiff(projectId, mergeRequestId);
        }
        catch (GitLabRuntimeException e) {
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

        return convertCommitWrappersToDTOs(mergeRequestCommits);
    }

    public List<CommitDTO> getAllOrphanCommits(String userId, int projectId, String targetBranch, Date start, Date end) {

        GitlabService gitlabService = createGitlabService(userId);
        List<CommitWrapper> dummyMergeRequestCommits;

        try {
            dummyMergeRequestCommits = gitlabService.getOrphanFilteredCommitsWithDiff(projectId, targetBranch, start, end);
            System.out.println(dummyMergeRequestCommits.size());
        }
        catch (GitLabRuntimeException e) {
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

        return convertCommitWrappersToDTOs(dummyMergeRequestCommits);

    }

}
