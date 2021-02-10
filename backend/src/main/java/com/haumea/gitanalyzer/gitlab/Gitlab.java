package com.haumea.gitanalyzer.gitlab;

import org.gitlab4j.api.*;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Member;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.Project;

import java.util.ArrayList;
import java.util.List;

/*
Class that shadows the functionality of our front end web client

Uses wrapper classes and will be used by our spring boot code to hand back data

 */

public class Gitlab {
//    private List<ProjectWrapper> projects;
    private GitLabApi gitLabApi;
    private MergeRequestApi mergeRequestApi;

    private List<String> commitUsers;

    private String hostUrl;
    private String personalAccessToken;


    public Gitlab(String hostUrl, String personalAccessToken) {
        this.hostUrl = hostUrl;
        this.personalAccessToken = personalAccessToken;

        this.commitUsers = new ArrayList<>();

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

        Project selectedProject = projectApi.getProject(projectId);

        List<MemberWrapper> allMembers = new ArrayList<>();


        for(org.gitlab4j.api.models.Member current : members) {
            MemberWrapper newMemberWrapper = new MemberWrapper(current.getName(), current.getEmail(), selectedProject, current.getId());

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

    public List<MergeRequest> getMergeRequests(int projectId) throws GitLabApiException {
        List<MergeRequest> mergeRequests = mergeRequestApi.getMergeRequests(projectId);

        return mergeRequests;

    }

    public List<Commit> getMergeRequestCommits(int projectId, int mergeRequestId) throws GitLabApiException {
        return mergeRequestApi.getCommits(projectId, mergeRequestId);
    }

    public List<Commit> getAllCommits(int projectId) throws GitLabApiException {
        CommitsApi commitsApi = new CommitsApi(gitLabApi);

        for(Commit currentCommit : commitsApi.getCommits(projectId)) {
            CommitWrapper newCommit = new CommitWrapper(gitLabApi, projectId, currentCommit);
        }

        return commitsApi.getCommits(projectId);
    }



}
