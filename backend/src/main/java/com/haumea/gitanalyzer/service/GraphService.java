package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dto.CommitGraphDTO;
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
}
