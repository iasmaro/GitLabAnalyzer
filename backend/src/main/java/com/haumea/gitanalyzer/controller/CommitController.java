package com.haumea.gitanalyzer.controller;


import com.haumea.gitanalyzer.service.CommitService;
import org.gitlab4j.api.models.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.haumea.gitanalyzer.dto.MemberRequestDTO;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/commits")
public class CommitController {

    private final CommitService commitService;

    @Autowired
    public CommitController(CommitService commitService) {

        this.commitService = commitService;

    }

    @GetMapping
    public List<Commit> getMergeRequestCommitsForMember(@RequestBody MemberRequestDTO memberRequestDTO, int mergeRequestId, String memberId) {
        try {
            return commitService.getMergeRequestCommitsForMember(memberRequestDTO, mergeRequestId, memberId);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}

