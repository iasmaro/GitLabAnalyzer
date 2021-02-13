package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dao.MemberRepository;
import com.haumea.gitanalyzer.dto.MemberRequestDTO;
import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.gitlab.MemberWrapper;
import com.haumea.gitanalyzer.utility.GlobalConstants;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserService userService;

    @Autowired
    public MemberService(MemberRepository memberRepository, UserService userService) {

        this.memberRepository = memberRepository;
        this.userService = userService;
    }

    public List<String> getMembers(String userId, Integer projectId) throws Exception{

        if(userId == null || projectId == null){
            throw new Exception("useId and projectId must be provided!");
        }

        String token;
        try {
            token = userService.getPersonalAccessToken(userId);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

        GitlabService gitlabService = new GitlabService(GlobalConstants.gitlabURL, token);

        List<String> members = new ArrayList<>();
        try {
            List<MemberWrapper> gitlabMembers = gitlabService.getMembers(projectId);
            for(MemberWrapper current: gitlabMembers){
                members.add(current.getMemberId());
            }

            return members;
        }
        catch(GitLabApiException e){
            throw new Exception(e.getMessage());
        }
    }

}
