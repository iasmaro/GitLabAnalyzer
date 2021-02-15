package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.dto.MemberDTO;
import com.haumea.gitanalyzer.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {

        this.memberService = memberService;
    }

    @GetMapping
    public List<String> getMembers(@RequestParam String userId, @RequestParam Integer projectId){

        try {
            return memberService.getMembers(userId, projectId);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("alias")
    public void mapAliasToMember(@RequestParam List<MemberDTO> membersAndAliases){

        try{
            memberService.mapAliasToMember(membersAndAliases);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}

