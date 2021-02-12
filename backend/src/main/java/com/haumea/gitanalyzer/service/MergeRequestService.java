package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dao.MergeRequestRepository;
import org.gitlab4j.api.models.MergeRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class MergeRequestService {

    private final MergeRequestRepository mergeRequestRepository;

    @Autowired
    public MergeRequestService(MergeRequestRepository mergeRequestRepository){
        this.mergeRequestRepository = mergeRequestRepository;
    }
}
