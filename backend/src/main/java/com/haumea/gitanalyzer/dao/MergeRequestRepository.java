package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.gitlab.Gitlab;
import com.haumea.gitanalyzer.model.MergeRequest;
import org.gitlab4j.api.GitLabApi;
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
    public MergeRequestRepository(MongoTemplate mongoTemplate) throws GitLabApiException {
        this.mongoTemplate = mongoTemplate;

        GitLabApi gitlabApi = new GitLabApi("https://csil-git1.cs.surrey.sfu.ca/", "thDxkfQVmkRUJP9mKGsm");

        List<Project> projects = gitlabApi.getProjectApi().getMemberProjects();

        Project project = null;

        for(Project cur : projects) {
            if(cur.getName().equals("GitLabAnalyzer")) {
                project = cur;
            }
        }

        this.projectID = project.getId();
        this.mergeRequestApi = gitlabApi.getMergeRequestApi();
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
            System.out.println(userID);

            MergeRequest normalizedMR = new MergeRequest(userID, projectID, memberID, mergeID, mergeDate, diffScore);
            normalizedMergeRequestList.add(normalizedMR);
        }

        return normalizedMergeRequestList;
    }

}
