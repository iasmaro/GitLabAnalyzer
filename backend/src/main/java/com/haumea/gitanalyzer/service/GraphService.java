package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dto.CodeReviewGraphDTO;
import com.haumea.gitanalyzer.dto.CommitGraphDTO;
import com.haumea.gitanalyzer.dto.MergeRequestGraphDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GraphService {

    public List<CommitGraphDTO> getCommitGraphDetails(String userId) {
        List<CommitGraphDTO> returnList = new ArrayList<>();
        return returnList;
    }

    public List<MergeRequestGraphDTO> getMergeRequestGraphDetails(String userId) {
        List<MergeRequestGraphDTO> returnList = new ArrayList<>();
        return returnList;
    }

    public List<CodeReviewGraphDTO> getCodeReviewGraphDetails(String userId) {
        List<CodeReviewGraphDTO> returnList = new ArrayList<>();
        return returnList;
    }
}
