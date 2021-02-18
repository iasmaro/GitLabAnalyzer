package com.haumea.gitanalyzer.controller;

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

    @GetMapping
    public List<CommitDTO> getMergeRequestCommitsForMember(@RequestParam @NotBlank String userId,
                                                           @RequestParam @NotNull Integer projectId,
                                                           @RequestParam @NotNull Integer mergeRequestId,
                                                           @RequestParam @NotBlank String memberId) {

        return commitService.getMergeRequestCommitsForMember(userId, projectId, mergeRequestId, memberId);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/members/{memberId}")
    public List<CommitDTO> getCommitsForMemberAndDate(@PathVariable String memberId,
                                                      @RequestParam @NotBlank String userId,
                                                      @RequestParam @NotNull int projectId,
                                                      @NotNull @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date start,
                                                      @NotNull @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date end) throws ParseException {


        System.out.println("Info is: " + memberId +  " " + userId + " " + projectId + " " + " " + start + " " + end);
        return commitService.getCommitsForSelectedMemberAndDate(userId, projectId, memberId, start, end);
    }



}

