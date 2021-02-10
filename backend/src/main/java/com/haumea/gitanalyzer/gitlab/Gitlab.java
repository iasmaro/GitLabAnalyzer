package com.haumea.gitanalyzer.gitlab;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.MergeRequestApi;
import org.gitlab4j.api.ProjectApi;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.Project;

import java.util.ArrayList;
import java.util.List;

/*
Class that shadows the functionality of our front end web client

Uses wrapper classes and will be used by our spring boot code to hand back data

 */

public class Gitlab {
    private List<ProjectWrapper> projects;
    private int selectedProject;

    private GitLabApi gitLabApi;
    private MergeRequestApi mergeRequestApi;

    private List<String> commitUsers;

    private String hostUrl;
    private String personalAccessToken;


    public Gitlab(String hostUrl, String personalAccessToken) {
        this.hostUrl = hostUrl;
        this.personalAccessToken = personalAccessToken;

        this.projects = new ArrayList<>();
        this.commitUsers = new ArrayList<>();

        this.gitLabApi = new GitLabApi(hostUrl, personalAccessToken);
        this.mergeRequestApi = new MergeRequestApi(gitLabApi);
    }

    public GitLabApi getGitLabApi() {
        return gitLabApi;
    }

    public ProjectWrapper getSelectedProject() {
        return projects.get(selectedProject);
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public String getPersonalAccessToken() {
        return personalAccessToken;
    }

    private void selectProject(String projectName) {
       for(int i=0; i<projects.size(); i++) {
           if(projects.get(i).getProjectName().equals(projectName)) {
               selectedProject = i;
           }
       }

    }

    // only call after the project has been selected
    public List<Member> getMembers(String projectName) throws GitLabApiException {
        if(projects.isEmpty()) {
            getProjects();
        }

        selectProject(projectName);

        ProjectApi projectApi = new ProjectApi(gitLabApi);

        List<org.gitlab4j.api.models.Member> members = projectApi.getAllMembers(projects.get(selectedProject).getProject().getId());


        for(org.gitlab4j.api.models.Member current : members) {
            Member newMember = new Member(current.getName(), current.getEmail(), projects.get(selectedProject).getProject(), current.getId());

            projects.get(selectedProject).addMember(newMember);

        }

        return projects.get(selectedProject).getMembers();

    }

    public List<ProjectWrapper> getProjects() throws GitLabApiException {
        if(projects.isEmpty()) {


            List<Project> projectList = gitLabApi.getProjectApi().getMemberProjects();

            for(Project current : projectList) {
                ProjectWrapper project = new ProjectWrapper(current);

                projects.add(project);
            }
        }

        return projects;
    }

    public List<MergeRequest> getMergeRequests(int projectId) throws GitLabApiException {
        List<MergeRequest> mergeRequests = mergeRequestApi.getMergeRequests(projectId);

        return mergeRequests;

    }

    public List<Commit> getMergeRequestCommits(int projectId, MergeRequest mergeRequest) throws GitLabApiException {
        return mergeRequestApi.getCommits(projectId, mergeRequest.getIid());
    }

}
