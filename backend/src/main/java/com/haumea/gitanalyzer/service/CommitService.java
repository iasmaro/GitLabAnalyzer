package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.model.User;
import org.gitlab4j.api.models.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommitService {
    private final GitlabService gitLabService;

    @Autowired
    public CommitService(GitlabService gitLabService) {
        this.gitLabService = gitLabService;
    }

    public List<Commit> getMergeRequestCommitsForMember(int projectId, int mergeRequestId, String memberId) throws Exception{
        try {
            return gitLabService.getMergeRequestCommitsForMember(projectId, mergeRequestId, memberId);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
