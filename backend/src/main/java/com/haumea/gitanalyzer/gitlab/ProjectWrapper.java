package com.haumea.gitanalyzer.gitlab;
import org.gitlab4j.api.models.Project;

import java.util.ArrayList;
import java.util.List;
/*
Wrapper class designed to encapsulate the project
*/

public class ProjectWrapper {
    private String projectName;
    private Project project;

    public Project getProject() {
        return project;
    }
    public String getProjectName() {
        return projectName;
    }

    public ProjectWrapper(Project project) {

        this.projectName = project.getName();
        this.project = project;

    }



}

