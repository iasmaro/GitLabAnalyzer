package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dto.CommitDTO;
import com.haumea.gitanalyzer.exception.GitLabRuntimeException;
import com.haumea.gitanalyzer.gitlab.CommitWrapper;
import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.model.User;
import com.haumea.gitanalyzer.dao.MemberRepository;
import com.haumea.gitanalyzer.utility.GlobalConstants;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommitService {

    private final UserService userService;

    @Autowired
    public CommitService(UserService userService) {
        this.userService = userService;
    }

    public List<CommitDTO> getMergeRequestCommitsForMember(String userId, Integer projectId,
                                                           Integer mergeRequestId, String memberId) {

        String token = userService.getPersonalAccessToken(userId);

        GitlabService gitLabService = new GitlabService(GlobalConstants.gitlabURL, token);

        return gitLabService.getMergeRequestCommitsForMember(projectId, mergeRequestId, memberId);
    }
}
