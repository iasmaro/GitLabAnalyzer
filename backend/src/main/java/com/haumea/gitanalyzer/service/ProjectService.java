package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dto.ProjectDTO;
import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.gitlab.ProjectWrapper;
import com.haumea.gitanalyzer.utility.GlobalConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private final UserService userService;

    @Autowired
    public ProjectService(UserService userService) {
        this.userService = userService;
    }

    public List<ProjectDTO> getProjects(String userId) {

        GitlabService gitlabService = userService.createGitlabService(userId);

        List<ProjectWrapper> gitlabProjects = gitlabService.getProjects();
        List<ProjectDTO> projects = new ArrayList<>();

        for(ProjectWrapper current: gitlabProjects){
            ProjectDTO project = new ProjectDTO(
                    current.getProject().getName(),
                    current.getProject().getId(),
                    current.getProject().getWebUrl(),
                    current.getProject().getCreatedAt(),
                    current.getProject().getLastActivityAt(),
                    current.getProject().getNamespace().getName());
            projects.add(project);
        }

        return projects;

    }
}
