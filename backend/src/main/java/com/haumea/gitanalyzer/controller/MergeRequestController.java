package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.model.MergeRequest;
import com.haumea.gitanalyzer.service.MergeRequestService;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/mrs")
public class MergeRequestController {

    private final MergeRequestService mergeRequestService;

    @Autowired
    public MergeRequestController(MergeRequestService mergeRequestService){
        this.mergeRequestService = mergeRequestService;
    }

    @GetMapping
    public List<MergeRequest> getAllMergeRequests() throws Exception {
        return mergeRequestService.getAllMergeRequests();
    }
}
