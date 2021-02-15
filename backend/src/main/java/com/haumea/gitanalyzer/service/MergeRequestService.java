package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.model.MergeRequest;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.MergeRequestApi;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MergeRequestService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public MergeRequestService(MongoTemplate mongoTemplate){

        this.mongoTemplate = mongoTemplate;
    }

    private List<String> getAllCommitters(MergeRequestApi mergeRequestApi, int projectID, int mergeRequestID) throws GitLabApiException {
        List<String> memberID = new ArrayList<>();

        List<Commit> commits = mergeRequestApi.getCommits(projectID, mergeRequestID);
        for(int i = 0; i < commits.size(); i++){
            Commit commit = commits.get(i);
            String member = commit.getCommitterName();
            if(!memberID.contains(member)){
                memberID.add(member);
            }
        }

        return memberID;
    }

    public List<MergeRequest> getAllMergeRequests() {

        GitlabService gitlabService = new GitlabService("https://csil-git1.cs.surrey.sfu.ca/", "thDxkfQVmkRUJP9mKGsm");

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

            String userID = "BFraser";
            int projectID = mergeRequest.getProjectId();
            int mergeID = mergeRequest.getId();
            String memberID = mergeRequest.getAuthor().getName();
            Date mergeDate = mergeRequest.getMergedAt();
            int diffScore = 0;
            System.out.println(mergeRequest.getState());

            MergeRequest normalizedMR = new MergeRequest(userID, projectID, memberID, mergeID, mergeDate, diffScore);
            normalizedMergeRequestList.add(normalizedMR);
        }

        return normalizedMergeRequestList;
    }
}
