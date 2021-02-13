package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dao.MemberRepository;
import com.haumea.gitanalyzer.dao.UserRepository;
import com.haumea.gitanalyzer.model.Member;
import com.haumea.gitanalyzer.dto.MemberRequestDTO;
import com.haumea.gitanalyzer.utility.GlobalConstants;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.ProjectApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, UserRepository userRepository) {

        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    public ArrayList<Member> getMembers(MemberRequestDTO memberRequestDTO) throws Exception{
        // validate request object
        if(memberRequestDTO.getUserId() == null || memberRequestDTO.getProjectId() == null){
            throw new Exception("useId and projectId must be provided!");
        }

        // retrieve access token from database
        String token;
        try {
            token = userRepository.getPersonalAccessToken(memberRequestDTO.getUserId());
        }
        catch (Exception e){
            throw new Exception("Empty token!");
        }

        // make connection to gitlab & retrieve info
        GitLabApi gitLabApi = new GitLabApi(GlobalConstants.gitlabURL, token);

        // store value in ArrayList<Member>
        ProjectApi projectApi = new ProjectApi(gitLabApi);
        List<org.gitlab4j.api.models.Member> gitlabMembers = projectApi.getAllMembers(memberRequestDTO.getProjectId());

        ArrayList<Member> members = new ArrayList<>();
        for(org.gitlab4j.api.models.Member current: gitlabMembers){
//            Member member = new Member(current.getUsername(), new ArrayList<String>());
        }

        // return

        return new ArrayList<>();
    }

}
