package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/members")
@Validated
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {

        this.memberService = memberService;
    }

    @GetMapping
    public List<String> getMembers(@RequestParam @NotBlank String userId,
                                   @RequestParam @NotNull Integer projectId){

        return memberService.getMembers(userId, projectId);
    }

}
