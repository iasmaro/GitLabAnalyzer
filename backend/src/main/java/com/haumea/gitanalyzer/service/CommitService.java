package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.model.User;
import com.haumea.gitanalyzer.dao.MemberRepository;
import com.haumea.gitanalyzer.dto.MemberRequestDTO;
import com.haumea.gitanalyzer.utility.GlobalConstants;
import org.gitlab4j.api.models.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommitService {

    private final UserService userService;

    @Autowired
    public CommitService(UserService userService) {
        this.userService = userService;
    }

    public List<Commit> getMergeRequestCommitsForMember(MemberRequestDTO memberRequestDTO, int mergeRequestId, String memberId) throws Exception{
        String token;
        try {
            token = userService.getPersonalAccessToken(memberRequestDTO.getUserId());
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

        GitlabService gitLabService = new GitlabService(GlobalConstants.gitlabURL, token);
        try {
            return gitLabService.getMergeRequestCommitsForMember(memberRequestDTO, mergeRequestId, memberId);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
