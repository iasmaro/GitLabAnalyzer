package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.service.CommitService;
import org.gitlab4j.api.models.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


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
    public List<Commit> getMergeRequestCommitsForMember(@RequestParam String userId, @RequestParam Integer projectId, @RequestParam Integer mergeRequestId, @RequestParam String memberId) {
        try {
            return commitService.getMergeRequestCommitsForMember(userId, projectId, mergeRequestId, memberId);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}

