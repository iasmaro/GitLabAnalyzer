package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dao.MemberRepository;
import com.haumea.gitanalyzer.dto.MemberRequestDTO;
import com.haumea.gitanalyzer.utility.GlobalConstants;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.ProjectApi;
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

    public List<String> getMembers(MemberRequestDTO memberRequestDTO) throws Exception{

        if(memberRequestDTO.getUserId() == null || memberRequestDTO.getProjectId() == null){
            throw new Exception("useId and projectId must be provided!");
        }

        String token;
        try {
            token = userService.getPersonalAccessToken(memberRequestDTO.getUserId());
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

        GitLabApi gitLabApi = new GitLabApi(GlobalConstants.gitlabURL, token);

        ProjectApi projectApi = new ProjectApi(gitLabApi);
        List<String> members = new ArrayList<>();
        try {
            List<org.gitlab4j.api.models.Member> gitlabMembers = projectApi.getAllMembers(memberRequestDTO.getProjectId());
            for(org.gitlab4j.api.models.Member current: gitlabMembers){
                members.add(current.getUsername());
            }

            return members;
        }
        catch(GitLabApiException e){
            throw new Exception(e.getMessage());
        }
    }

}
