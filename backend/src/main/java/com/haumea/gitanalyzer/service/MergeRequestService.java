package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.model.MergeRequest;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.MergeRequestApi;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MergeRequestService {

    private final GitlabService gitlabService;

    @Autowired
    public MergeRequestService(GitlabService gitlabService){
        this.gitlabService = gitlabService;
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

    public List<MergeRequest> getAllMergeRequests() throws Exception {

        Project project = gitlabService.getSelectedProject("GitLabAnalyzer");
        MergeRequestApi mergeRequestApi = gitlabService.getMergeRequestApi();
        List<org.gitlab4j.api.models.MergeRequest> mergeRequestsList = mergeRequestApi.getMergeRequests(project);

        List<MergeRequest> normalizedMergeRequestList = new ArrayList<>();

        for(int i = 0; i < mergeRequestsList.size(); i++){
            org.gitlab4j.api.models.MergeRequest mergeRequest = mergeRequestsList.get(i);

            String userID = mergeRequest.getAuthor().getName();
            int projectID = mergeRequest.getProjectId();
            int mergeID = mergeRequest.getId();
            //List<String> memberID = getAllCommitters(mergeRequestApi, projectID, mergeID);
            Date mergeDate = mergeRequest.getMergedAt();
            int diffScore = 0;
            System.out.println(userID);

            MergeRequest normalizedMR = new MergeRequest(userID, projectID, mergeID, mergeDate, diffScore);
            normalizedMergeRequestList.add(normalizedMR);
        }

        return normalizedMergeRequestList;
    }
}
