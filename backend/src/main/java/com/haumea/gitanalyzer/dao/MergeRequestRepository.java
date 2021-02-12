package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.gitlab.Gitlab;
import com.haumea.gitanalyzer.model.MergeRequest;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.MergeRequestApi;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class MergeRequestRepository {

    private final MongoTemplate mongoTemplate;

    private MergeRequestApi mergeRequestApi;
    private int projectID;

    @Autowired
    public MergeRequestRepository(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;

        Gitlab gitlab = new Gitlab("https://csil-git1.cs.surrey.sfu.ca/users/sign_in", "G9AYVYcZ54VzZRz2RTut");
        gitlab.selectProject("GitLabAnalyzer");

        this.projectID = gitlab.getSelectedProjectID();
        this.mergeRequestApi = gitlab.getGitLabApi().getMergeRequestApi();
    }

    private List<String> getAllCommitters(MergeRequestApi mergeREquestApi, int projectID, int mergeRequestID) throws GitLabApiException {
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

    public List<MergeRequest> getAllMergeRequests() throws GitLabApiException {
        List<org.gitlab4j.api.models.MergeRequest> mergeRequestsList = mergeRequestApi.getMergeRequests(projectID);
        List<MergeRequest> normalizedMergeRequestList = new ArrayList<>();

        for(int i = 0; i < mergeRequestsList.size(); i++){
            org.gitlab4j.api.models.MergeRequest mergeRequest = mergeRequestsList.get(i);

            String userID = mergeRequest.getAuthor().getName();
            int projectID = mergeRequest.getProjectId();
            int mergeID = mergeRequest.getId();
            List<String> memberID = getAllCommitters(mergeRequestApi, projectID, mergeID);
            Date mergeDate = mergeRequest.getMergedAt();
            int diffScore = 0;

            MergeRequest normalizedMR = new MergeRequest(userID, projectID, memberID, mergeID, mergeDate, diffScore);
            normalizedMergeRequestList.add(normalizedMR);
        }

        return normalizedMergeRequestList;
    }

}
