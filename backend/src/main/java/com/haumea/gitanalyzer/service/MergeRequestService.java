package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dao.MergeRequestRepository;
import com.haumea.gitanalyzer.model.MergeRequest;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class MergeRequestService {

    private final MergeRequestRepository mergeRequestRepository;

    @Autowired
    public MergeRequestService(MergeRequestRepository mergeRequestRepository){
        this.mergeRequestRepository = mergeRequestRepository;
    }

    public List<MergeRequest> getAllMergeRequests() throws GitLabApiException {
        try{
            return mergeRequestRepository.getAllMergeRequests();
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No merge requests was found", e);
        }
    }
}
