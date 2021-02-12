package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.model.Member;
import com.haumea.gitanalyzer.model.MemberRequestDTO;
import com.haumea.gitanalyzer.service.MemberService;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {

        this.memberService = memberService;
    }

    @GetMapping
    public ArrayList<Member> getMembers(@RequestBody MemberRequestDTO memberRequestDTO){
        // TODO: handle error throw by called function
        return memberService.getMembers(memberRequestDTO);
    }

}
