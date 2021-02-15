package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.model.MergeRequest;
import org.gitlab4j.api.CommitsApi;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.MergeRequestApi;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Diff;
import org.gitlab4j.api.models.MergeRequestDiff;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MergeRequestService {

    private GitlabService gitlabService;

    private List<String> getAllCommitters(MergeRequestApi mergeRequestApi, int projectID, int mergeRequestID) throws GitLabApiException {

        List<String> members = new ArrayList<>();

        List<Commit> commits = mergeRequestApi.getCommits(projectID, mergeRequestID);
        for(int i = 0; i < commits.size(); i++){
            Commit commit = commits.get(i);
            String member = commit.getCommitterName();
            if(!members.contains(member)){
                members.add(member);
            }
        }

        return members;
    }

    private double getMRDiffScore(int projectID, int mergeRequestIiD) throws GitLabApiException {

        MergeRequestApi mergeRequestApi = gitlabService.getMergeRequestApi();
        List<Commit> commits = mergeRequestApi.getCommits(projectID, mergeRequestIiD);
        CommitsApi commitsApi = gitlabService.getGitLabApi().getCommitsApi();

        double MRDifScore = 0;
        int insertions = 0;
        int deletions = 0;

        for(Commit commit : commits){

            List<Diff> newCode = commitsApi.getDiff(projectID, commit.getId());
            for (Diff code : newCode) {

                insertions = insertions + StringUtils.countOccurrencesOf(code.getDiff(), "\n+");
                deletions = deletions + StringUtils.countOccurrencesOf(code.getDiff(), "\n-");
            }

        }

        MRDifScore = insertions + deletions*0.2;

        return MRDifScore;
    }

    private double getScoreForMember(int projectID, int mergeRequestIiD, String memberID) throws GitLabApiException {

        MergeRequestApi mergeRequestApi = gitlabService.getMergeRequestApi();
        List<Commit> commits = mergeRequestApi.getCommits(projectID, mergeRequestIiD);
        CommitsApi commitsApi = gitlabService.getGitLabApi().getCommitsApi();

        double MRDifScore = 0;
        int insertions = 0;
        int deletions = 0;

        for(Commit commit : commits){

            if(!commit.getAuthorName().equals(memberID) && !(commit.getAuthorEmail().equals(memberID + "@sfu.ca"))){

                continue;
            }

            List<Diff> newCode = commitsApi.getDiff(projectID, commit.getId());
            for (Diff code : newCode) {

                insertions = insertions + StringUtils.countOccurrencesOf(code.getDiff(), "\n+");
                deletions = deletions + StringUtils.countOccurrencesOf(code.getDiff(), "\n-");
            }

        }

        MRDifScore = insertions + deletions*0.2;

        return MRDifScore;
    }

    public List<MergeRequest> getAllMergeRequests() throws GitLabApiException {

        gitlabService = new GitlabService("https://csil-git1.cs.surrey.sfu.ca/", "thDxkfQVmkRUJP9mKGsm");

        Project project = null;
        try {
            project = gitlabService.getSelectedProject("GitLabAnalyzer");
        } catch (Exception e) {
            e.printStackTrace();
        }

        MergeRequestApi mergeRequestApi = gitlabService.getMergeRequestApi();
        List<org.gitlab4j.api.models.MergeRequest> mergeRequestsList = null;
        try {
            mergeRequestsList = mergeRequestApi.getMergeRequests(project);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }

        List<MergeRequest> normalizedMergeRequestList = new ArrayList<>();

        for(int i = 0; i < mergeRequestsList.size(); i++){
            org.gitlab4j.api.models.MergeRequest mergeRequest = mergeRequestsList.get(i);

            if(!mergeRequest.getState().equals("merged")){
                continue;
            }

            int mergeRequestIiD = mergeRequest.getIid();

            String userID = "BFraser";
            int projectID = mergeRequest.getProjectId();
            int mergeID = mergeRequest.getId();
            String memberID = mergeRequest.getAuthor().getName();
            Date mergeDate = mergeRequest.getMergedAt();
            double MRScore = getMRDiffScore(projectID, mergeRequestIiD);
            double memberScore = getScoreForMember(projectID, mergeRequestIiD, memberID);

            MergeRequest normalizedMR = new MergeRequest(userID, projectID, memberID, mergeID, mergeDate, MRScore, memberScore);
            normalizedMergeRequestList.add(normalizedMR);
        }

        return normalizedMergeRequestList;
    }
}
