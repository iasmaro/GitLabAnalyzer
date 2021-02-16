package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.dto.CommitDTO;
import com.haumea.gitanalyzer.service.CommitService;
import org.gitlab4j.api.models.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
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
    public List<CommitDTO> getMergeRequestCommitsForMember(@RequestParam @NotBlank String userId,
                                                           @RequestParam @NotNull Integer projectId,
                                                           @RequestParam @NotNull Integer mergeRequestId,
                                                           @RequestParam @NotBlank String memberId) {

        return commitService.getMergeRequestCommitsForMember(userId, projectId, mergeRequestId, memberId);
    }

//    @GetMapping
//    public List<CommitDTO> getCommitsForMemberAndDate(@RequestParam @NotBlank String userId,
//                                                      @RequestParam @NotNull Integer projectId,
//                                                      @RequestParam @NotNull String commitAuthor,
//                                                      @RequestParam @NotNull String start,
//                                                      @RequestParam @NotNull String end) {
//
//    }
}

