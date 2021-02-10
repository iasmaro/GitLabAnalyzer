package com.haumea.gitanalyzer.gitlab;
import org.gitlab4j.api.models.Project;

import java.util.ArrayList;
import java.util.List;
/*
Wrapper class designed to encapsulate the project
*/

public class ProjectWrapper {
    private String projectName;
    private List<Member> members;
    private Project project;

    public List<Member> getMembers() {
        return members;
    }

    public Project getProject() {
        return project;
    }
    public String getProjectName() {
        return projectName;
    }

    public ProjectWrapper(Project project) {

        this.members = new ArrayList<>();
        this.projectName = project.getName();
        this.project = project;

    }

    public void addMember(Member member) {
        members.add(member);
    }


}

