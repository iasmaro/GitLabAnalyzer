package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dto.ProjectDTO;
import com.haumea.gitanalyzer.exception.GitLabRuntimeException;
import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.gitlab.ProjectWrapper;
import com.haumea.gitanalyzer.utility.GlobalConstants;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private final UserService userService;

    @Autowired
    public ProjectService(UserService userService) {
        this.userService = userService;
    }

    public List<ProjectDTO> getProjects(String userId) throws GitLabRuntimeException {

        String token = userService.getPersonalAccessToken(userId);

        GitlabService gitlabService = new GitlabService(GlobalConstants.gitlabURL, token);

        try{
            List<ProjectWrapper> gitlabProjects = gitlabService.getProjects();
            List<ProjectDTO> projects = new ArrayList<>();

            for(ProjectWrapper current: gitlabProjects){
                ProjectDTO project = new ProjectDTO(current.getProjectName(), current.getProject().getId(),
                        current.getProject().getWebUrl());
                projects.add(project);
            }

            return projects;
        }
        catch (GitLabApiException e){
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

    }
}