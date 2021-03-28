package com.haumea.gitanalyzer.controller;


import com.haumea.gitanalyzer.dto.CodeReviewGraphDTO;
import com.haumea.gitanalyzer.dto.CommitGraphDTO;
import com.haumea.gitanalyzer.dto.IssueGraphDTO;
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
import javax.validation.constraints.NotNull;
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

    public List<CommitGraphDTO> getCommitGraphDetails(@RequestParam @NotBlank String userId,
                                                      @RequestParam @NotBlank String memberId,
                                                      @RequestParam @NotNull int projectId) {

        return graphService.getCommitGraphDetails(userId, memberId, projectId);

    }

    @GetMapping("/mergeRequest")

    public List<MergeRequestGraphDTO> getMergeRequestGraphDetails(@RequestParam @NotBlank String userId,
                                                                  @RequestParam @NotBlank String memberId,
                                                                  @RequestParam @NotNull int projectId) {


        return graphService.getMergeRequestGraphDetails(userId, memberId, projectId);

    }

    @GetMapping("/codeReview")

    public List<CodeReviewGraphDTO> getCodeReviewGraphDetails(@RequestParam @NotBlank String userId,
                                                              @RequestParam @NotBlank String memberId,
                                                              @RequestParam @NotNull int projectId) {

        return graphService.getCodeReviewGraphDetails(userId, memberId, projectId);

    }

    @GetMapping("/issue")

    public List<IssueGraphDTO> getIssueGraphDetails(@RequestParam @NotBlank String userId,
                                                    @RequestParam @NotBlank String memberId,
                                                    @RequestParam @NotNull int projectId) {

        return graphService.getIssueGraphDetails(userId, memberId, projectId);

    }

}
