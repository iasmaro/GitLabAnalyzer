package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.dao.UserRepository;
import com.haumea.gitanalyzer.dto.CommitDTO;
import com.haumea.gitanalyzer.service.CommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@RestController
@RequestMapping(path = "/api/v1/commits")
public class CommitController {

    private final CommitService commitService;

    @Autowired
    public CommitController(CommitService commitService) {
        this.commitService = commitService;
    }

    @GetMapping("/mergeRequests/{mergeRequestId}")
    public List<CommitDTO> getCommitsForSelectedMergeRequest(@PathVariable @NotNull int mergeRequestId,
                                                             @RequestParam @NotBlank String userId,
                                                             @RequestParam @NotNull int projectId) {


        return commitService.getCommitsForSelectedMergeRequest(userId, projectId, mergeRequestId);
    }

    @GetMapping("mergeRequests/{mergeRequestId}/members/{memberId}")
    public List<CommitDTO> getMergeRequestCommitsForMember(@RequestParam @NotBlank String userId,
                                                           @RequestParam @NotNull Integer projectId,
                                                           @PathVariable @NotNull Integer mergeRequestId,
                                                           @PathVariable @NotBlank String memberId) {

        return commitService.getMergeRequestCommitsForMember(userId, projectId, mergeRequestId, memberId);
    }

    @GetMapping("/members/{memberId}")
    public List<CommitDTO> getCommitsForMemberAndDate(@PathVariable @NotBlank String memberId,
                                                      @RequestParam @NotBlank String userId,
                                                      @RequestParam @NotNull int projectId) {




        return commitService.getCommitsForSelectedMemberAndDate(userId, projectId, memberId);
    }

}

