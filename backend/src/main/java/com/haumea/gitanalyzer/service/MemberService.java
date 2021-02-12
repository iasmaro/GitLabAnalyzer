package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dao.MemberRepository;
import com.haumea.gitanalyzer.dao.UserRepository;
import com.haumea.gitanalyzer.model.Member;
import com.haumea.gitanalyzer.model.MemberRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
        String token = userRepository.getPersonalAccessToken(memberRequestDTO.getUserId());

        // make connection to gitlab & retrieve info

        // store value in ArrayList<Member>

        // return

        return new ArrayList<>();
    }

}
