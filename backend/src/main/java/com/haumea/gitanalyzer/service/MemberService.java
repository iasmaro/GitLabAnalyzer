package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dao.MemberRepository;
import com.haumea.gitanalyzer.model.Member;
import com.haumea.gitanalyzer.model.MemberRequestDTO;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    public ArrayList<Member> getMembers(MemberRequestDTO memberRequestDTO){
        // validate request object

        // retrieve access token from database

        // make connection to gitlab & retrieve info

        // store value in ArrayList<Member>

        // return

        return new ArrayList<>();
    }

}
