package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.dto.MergeRequestDTO;
import com.haumea.gitanalyzer.service.MergeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/mergeRequests")
@Validated
public class MergeRequestController {

    private final MergeRequestService mergeRequestService;

    @Autowired
    public MergeRequestController(MergeRequestService mergeRequestService) {

        this.mergeRequestService = mergeRequestService;
    }

    @GetMapping
    public List<MergeRequestDTO> getAllMergeRequests(@NotBlank @RequestParam String userId,
                                                     @NotNull @RequestParam int projectId) {

        return mergeRequestService.getAllMergeRequests(userId, projectId);
    }

    @GetMapping(path = "/member/{memberId}")
    public List<MergeRequestDTO> getAllMergeRequests(@NotBlank @RequestParam String userId,
                                                     @NotNull @RequestParam int projectId,
                                                     @NotNull @PathVariable String memberId) {

        return mergeRequestService.getAllMergeRequestsForMember(userId, projectId, memberId);
    }
}
