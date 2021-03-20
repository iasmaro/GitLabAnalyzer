package com.haumea.gitanalyzer.controller;


import com.haumea.gitanalyzer.dto.CommitGraphDTO;
import com.haumea.gitanalyzer.dto.MergeRequestGraphDTO;
import com.haumea.gitanalyzer.service.GraphService;
import com.haumea.gitanalyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/graphs")
@Validated
public class GraphController {

    private final GraphService graphService;

    @Autowired
    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/commit")

    public List<CommitGraphDTO> getCommitGraphDetails(@RequestParam @NotBlank String userId) {

        return graphService.getCommitGraphDetails(userId);

    }

    @GetMapping("/mergeRequest")

    public List<MergeRequestGraphDTO> getMergeRequestGraphDetails(@RequestParam @NotBlank String userId) {

        return graphService.getMergeRequestGraphDetails(userId);

    }
//
//    @GetMapping("/codeReview")
//
//    public String getPersonalAccessToken(@RequestParam @NotBlank String userId) {
//
//        return userService.getPersonalAccessToken(userId);
//
//    }
//
//    @GetMapping("/issue")
//
//    public String getPersonalAccessToken(@RequestParam @NotBlank String userId) {
//
//        return userService.getPersonalAccessToken(userId);
//
//    }

}
