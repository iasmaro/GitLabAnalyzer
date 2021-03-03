package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.dto.MergeRequestDTO;
import com.haumea.gitanalyzer.service.MergeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/projects/{projectId}/merge_requests")
@Validated
public class MergeRequestController {

    private final MergeRequestService mergeRequestService;

    @Autowired
    public MergeRequestController(MergeRequestService mergeRequestService){

        this.mergeRequestService = mergeRequestService;
    }

    @GetMapping
    public List<MergeRequestDTO> getAllMergeRequests(@NotBlank @RequestParam String userId,
                                                     @PathVariable int projectId,
                                                     @NotBlank @RequestParam String memberId,
                                                     @NotNull @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date start,
                                                     @NotNull @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date end,
                                                     @RequestParam (required = false, defaultValue = "false") boolean memberFilter){

        return mergeRequestService.getAllMergeRequests(userId, projectId, memberId, start, end, memberFilter);
    }
}
