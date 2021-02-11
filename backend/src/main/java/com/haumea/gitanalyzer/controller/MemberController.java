package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.model.Member;
import com.haumea.gitanalyzer.service.MemberService;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {

        this.memberService = memberService;
    }

    // Using MongoRepository under the hood
    @GetMapping("/all")
    public List<Member> getMember(){

        return memberService.getMember();
    }

    // ID must be provide in the request body
    @PostMapping("/add")
    public String saveMember(@RequestBody Member member){

        memberService.addMember(member);
        return "Member added!";

    }

    // Using MongoTemplate under the hood
    @GetMapping("/all/dal")
    public List<Member> getMemberDAL(){

        return memberService.getMemberDAL();
    }

    // ID is auto generated
    @PostMapping("/add/dal")
    public String saveMemberDAL(@RequestBody Member member){

        memberService.addMemberDAL(member);
        return "Member added!";

    }

    @GetMapping("/projects")
    public List<String> getProject(@RequestBody String personalAccessToken){

        GitLabApi gitLabApi = memberService.connectToGitLab(personalAccessToken);

        try{
            return memberService.getProjects(gitLabApi);
        }
        catch (GitLabApiException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projects not found.", e);

        }
    }

}
