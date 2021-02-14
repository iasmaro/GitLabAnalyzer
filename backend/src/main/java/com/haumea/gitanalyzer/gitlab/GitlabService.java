package com.haumea.gitanalyzer.gitlab;

import org.gitlab4j.api.*;
import org.gitlab4j.api.models.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
Class that shadows the functionality of our front end web client

Uses wrapper classes and will be used by our spring boot code to hand back data

 */

public class GitlabService {
    private GitLabApi gitLabApi;
    private MergeRequestApi mergeRequestApi;

    private String hostUrl;
    private String personalAccessToken;


    public GitlabService(String hostUrl, String personalAccessToken) {
        this.hostUrl = hostUrl;
        this.personalAccessToken = personalAccessToken;

        this.gitLabApi = new GitLabApi(hostUrl, personalAccessToken);
        this.mergeRequestApi = new MergeRequestApi(gitLabApi);
    }

    public GitLabApi getGitLabApi() {
        return gitLabApi;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public String getPersonalAccessToken() {
        return personalAccessToken;
    }

    // only call after the project has been selected
    public List<MemberWrapper> getMembers(int projectId) throws GitLabApiException {
        ProjectApi projectApi = new ProjectApi(gitLabApi);

        List<Member> members = projectApi.getAllMembers(projectId);


        List<MemberWrapper> allMembers = new ArrayList<>();

        for(org.gitlab4j.api.models.Member current : members) {
            MemberWrapper newMemberWrapper = new MemberWrapper(current.getName(), current.getEmail(), current.getId());

            allMembers.add(newMemberWrapper);
        }

        return allMembers;

    }

    public List<ProjectWrapper> getProjects() throws GitLabApiException {
        List<ProjectWrapper> projects = new ArrayList<>();

        List<Project> projectList = gitLabApi.getProjectApi().getMemberProjects();

        for(Project current : projectList) {
            ProjectWrapper project = new ProjectWrapper(current);

            projects.add(project);
        }

        return projects;

    }


    public List<MergeRequestWrapper> getMergeRequestForMember(int projectId, String memberId) throws GitLabApiException {
        List<MergeRequestWrapper> filteredList = new ArrayList<>();

        for(MergeRequestWrapper currentMergeRequest : getAllMergeRequests(projectId)) {
            if(currentMergeRequest.getMergeRequestData().getAuthor().getName().equals(memberId)) {
                filteredList.add(currentMergeRequest);
            }

        }

        return filteredList;

    }

    public List<MergeRequest> getAllMergeRequestData(int projectId) throws GitLabApiException {
        return mergeRequestApi.getMergeRequests(projectId);

    }
    public List<MergeRequestWrapper> getAllMergeRequests(int projectId) throws GitLabApiException {

        List<MergeRequestWrapper> mergeRequestList = new ArrayList<>();

        for(MergeRequest currentMR : mergeRequestApi.getMergeRequests(projectId)) {

            MergeRequestWrapper newMergeRequest = new MergeRequestWrapper(mergeRequestApi, projectId, currentMR);

            for(int i=0; i<newMergeRequest.getMergeRequestChangeData().size(); i++) {
                MergeRequestDiff diff = mergeRequestApi.getMergeRequestDiff(projectId, currentMR.getIid(), newMergeRequest.getMergeRequestChangeData().get(i).getId());
                newMergeRequest.addMergeRequestChange(diff);

                mergeRequestList.add(newMergeRequest);
            }

        }
        return mergeRequestList;
    }


    public List<CommitWrapper> getMergeRequestCommits(int projectId, int mergeRequestId) throws GitLabApiException {
        List<CommitWrapper> commitListMR = new ArrayList<>();

        for(Commit currentCommit : mergeRequestApi.getCommits(projectId, mergeRequestId)) {
            CommitWrapper newCommit = new CommitWrapper(gitLabApi, projectId, currentCommit);

            commitListMR.add(newCommit);
        }

        return commitListMR;
    }

    // use if you just want the merge request Data and not the code diffs
    public List<Commit> getMergeRequestCommitsData(int projectId, int mergeRequestId) throws GitLabApiException {

        return mergeRequestApi.getCommits(projectId, mergeRequestId);
    }

    public List<CommitWrapper> filterCommitsForDateAndAuthor(int projectId, String authorName, Date start, Date end) throws GitLabApiException {
        CommitsApi commitsApi = new CommitsApi(gitLabApi);
        List<CommitWrapper> commitList = new ArrayList<>();

        for(Commit currentCommit : commitsApi.getCommits(projectId, "master", start, end)) {

            if(currentCommit.getAuthorName().equals(authorName) ) {
                CommitWrapper newCommit = new CommitWrapper(gitLabApi, projectId, currentCommit);

                commitList.add(newCommit);
            }
        }

        return commitList;

    }

    public List<CommitWrapper> getAllCommits(int projectId) throws GitLabApiException {
        CommitsApi commitsApi = new CommitsApi(gitLabApi);
        List<CommitWrapper> commitList = new ArrayList<>();

        for(Commit currentCommit : commitsApi.getCommits(projectId)) {
            CommitWrapper newCommit = new CommitWrapper(gitLabApi, projectId, currentCommit);

            commitList.add(newCommit);
        }

        return commitList;
    }
}
