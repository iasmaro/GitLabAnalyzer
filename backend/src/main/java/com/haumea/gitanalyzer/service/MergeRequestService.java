package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.exception.GitLabRuntimeException;
import com.haumea.gitanalyzer.gitlab.CommitWrapper;
import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.gitlab.MergeRequestWrapper;
import com.haumea.gitanalyzer.dto.MergeRequestDTO;
import com.haumea.gitanalyzer.utility.GlobalConstants;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Diff;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.MergeRequestDiff;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MergeRequestService {
    private final UserService userService;
    private double memberScore;

    @Autowired
    public MergeRequestService(UserService userService) {
        this.userService = userService;
        memberScore = 0;
    }

    private GitlabService getGitLabService(String userId){

        //String accessToken = userService.getPersonalAccessToken(userId);

        return new GitlabService("https://csil-git1.cs.surrey.sfu.ca/", "gYLtys_E24PNBWmG_i86");
    }

    private Project getProject(GitlabService gitlabService, int projectId) throws GitLabRuntimeException {

        Project project = null;

        try {

            project = gitlabService.getSelectedProject(projectId);
        } catch (GitLabApiException e) {

            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

        return project;
    }

    private List<MergeRequestWrapper> getMergeRequestWrapper(GitlabService gitlabService, int projectId, Project project, Date start, Date end) throws GitLabRuntimeException{

        List<MergeRequestWrapper> mergeRequestsList = null;

        try {

            mergeRequestsList = gitlabService.filterMergeRequestByDate(projectId, project.getName(), start, end);

        } catch (GitLabApiException e) {

            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

        return mergeRequestsList;
    }

    private List<CommitWrapper> getCommitWrapper(GitlabService gitlabService, int projectId, int mergeRequestIiD) throws GitLabRuntimeException{

        List<CommitWrapper> commits = null;

        try {

            commits = gitlabService.getMergeRequestCommits(projectId, mergeRequestIiD);
        } catch (GitLabApiException e) {

            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

        return commits;
    }

    //TODO: Update the comparison with data from alias
    private boolean filterMemberId(String authorName, String authorEmail, String memberId){

        if(authorName.equals(memberId) || authorEmail.equals(memberId + "@sfu.ca")){

            return true;
        }

        return false;
    }

    private double getMRDiffScoreAndMemberScore(List<CommitWrapper> commits, String memberId){

        double MRDifScore = 0;
        int insertions = 0;
        int deletions = 0;

        //Temporarily comparing author and email to capture alias
        //TODO: Recalculate scores using more appropriate analysis
        for(CommitWrapper commit : commits){

            List<Diff> newCode = commit.getNewCode();
            for (Diff code : newCode) {

                int newInsertions = 0;
                newInsertions = StringUtils.countOccurrencesOf(code.getDiff(), "\n+");
                int newDeletions = 0;
                newDeletions = StringUtils.countOccurrencesOf(code.getDiff(), "\n-");

                insertions = insertions + newInsertions;
                deletions = deletions + newDeletions;

                if(filterMemberId(commit.getCommitData().getAuthorName(), commit.getCommitData().getAuthorEmail(), memberId)){

                    memberScore = memberScore + newInsertions + newDeletions*0.2;
                }

            }

        }

        MRDifScore = insertions + deletions*0.2;

        return MRDifScore;
    }

    private double roundScore(double score){

        return Math.round(score*10)/10.0;
    }

    public List<String> getMergeRequestDiffs(List<MergeRequestDiff> mergeRequestDiffList){

        List<String> mergeRequestDiffs = new ArrayList<String>();

        for (MergeRequestDiff change : mergeRequestDiffList) {

            for(Diff diff:change.getDiffs()){

                System.out.println(diff.getDiff());
                mergeRequestDiffs.add(diff.getDiff());

            }

        }

        return mergeRequestDiffs;
    }

    public List<MergeRequestDTO> getAllMergeRequests(String userId, int projectId, String memberId, Date start, Date end, boolean memberFilter){

        GitlabService gitlabService = getGitLabService(userId);

        Project project = getProject(gitlabService, projectId);

        List<MergeRequestWrapper> mergeRequestsList = getMergeRequestWrapper(gitlabService, projectId, project, start, end);

        List<MergeRequestDTO> normalizedMergeRequestDTOList = new ArrayList<>();

        for(int i = 0; i < mergeRequestsList.size(); i++){
            MergeRequestWrapper mergeRequestWrapper = mergeRequestsList.get(i);
            MergeRequest mergeRequest = mergeRequestWrapper.getMergeRequestData();

            int mergeRequestIiD = mergeRequest.getIid();
            int mergeIiD = mergeRequest.getIid();
            Date mergedDate = mergeRequest.getMergedAt();
            Date createdDate = mergeRequest.getCreatedAt();
            Date updatedDate = mergeRequest.getUpdatedAt();

            List<CommitWrapper> commits = getCommitWrapper(gitlabService, projectId, mergeRequestIiD);

            memberScore = 0;
            double MRScore = roundScore(getMRDiffScoreAndMemberScore(commits, memberId));
            memberScore = roundScore(memberScore);

            if(memberFilter && memberScore == 0.0) {
                continue;
            }

            List<String> mergeRequestDiffs = getMergeRequestDiffs(mergeRequestWrapper.getMergeRequestChanges());

            MergeRequestDTO normalizedMR = new MergeRequestDTO(mergeIiD, mergedDate, createdDate, updatedDate, MRScore, memberScore, mergeRequestDiffs);
            normalizedMergeRequestDTOList.add(normalizedMR);
        }

        return normalizedMergeRequestDTOList;
    }
}
