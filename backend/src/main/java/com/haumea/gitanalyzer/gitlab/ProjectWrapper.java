package com.haumea.gitanalyzer.gitlab;
import org.gitlab4j.api.models.Project;

/*
Wrapper class designed to encapsulate the project
*/

public class ProjectWrapper {
    private Project project;

    public ProjectWrapper(Project project) {

        this.project = project;

    }

    public Project getProject() {
        return project;
    }
}

