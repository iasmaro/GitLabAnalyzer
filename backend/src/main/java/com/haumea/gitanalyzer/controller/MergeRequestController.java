package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.model.MergeRequest;
import com.haumea.gitanalyzer.service.MergeRequestService;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/mrs")
@Validated
public class MergeRequestController {

    private final MergeRequestService mergeRequestService;

    @Autowired
    public MergeRequestController(MergeRequestService mergeRequestService){

        this.mergeRequestService = mergeRequestService;
    }

    @GetMapping
    public List<MergeRequest> getAllMergeRequests(@NotBlank @RequestParam String userID,
                                                  @NotNull @RequestParam int projectID,
                                                  @NotBlank @RequestParam String memberID,
                                                  @NotNull @RequestParam Date start,
                                                  @NotNull @RequestParam Date end) throws Exception {

        return mergeRequestService.getAllMergeRequests(userID, projectID, memberID, start, end);
    }
}
