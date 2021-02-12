package com.haumea.gitanalyzer.gitlab;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.ProjectApi;
import org.gitlab4j.api.models.Member;
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

    private String hostUrl;
    private String personalAccessToken;

    public Gitlab(String hostUrl, String personalAccessToken) {
        this.hostUrl = hostUrl;
        this.personalAccessToken = personalAccessToken;

        this.projects = new ArrayList<>();
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

    public void selectProject(String projectName) {
       for(int i=0; i<projects.size(); i++) {
           if(projects.get(i).getProjectName().equals(projectName)) {
               selectedProject = i;
           }
       }

    }

    public int getSelectedProjectID(){
        return selectedProject;
    }

    // called when the user selects a project
    public List<Student> getStudents(String projectName) throws GitLabApiException {
        if(projects.isEmpty()) {
            getProjects();
        }

        selectProject(projectName);

        ProjectApi projectApi = new ProjectApi(gitLabApi);

        List<Member> members = projectApi.getAllMembers(projects.get(selectedProject).getProject());

        for(Member current : members) {
            Student newStudent = new Student(current.getName(), current.getEmail(), projects.get(selectedProject).getProject(), current.getId());

            projects.get(selectedProject).addStudent(newStudent);

        }

        return projects.get(selectedProject).getStudents();

    }

    public List<ProjectWrapper> getProjects() throws GitLabApiException {
        if(projects.isEmpty()) {
            gitLabApi = new GitLabApi(hostUrl, personalAccessToken);

            List<Project> projectList = gitLabApi.getProjectApi().getMemberProjects();

            for(Project current : projectList) {
                ProjectWrapper project = new ProjectWrapper(current);

                projects.add(project);
            }
        }

        return projects;
    }

}
