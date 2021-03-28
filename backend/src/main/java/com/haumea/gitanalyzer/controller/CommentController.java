package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.dto.CommentDTO;
import com.haumea.gitanalyzer.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/mergeRequests/members/{memberId}")
    public List<CommentDTO> getCommentsForMergeRequests(@RequestParam @NotBlank String userId,
                                                        @RequestParam @NotNull int projectId,
                                                        @PathVariable @NotBlank String memberId) {

        return commentService.getMergeRequestComments(userId, projectId, memberId);
    }

    @GetMapping("/mergeRequests/members/{memberId}")
    public List<CommentDTO> getCommentsForIssues(@RequestParam @NotBlank String userId,
                                                 @RequestParam @NotNull int projectId,
                                                 @PathVariable @NotBlank String memberId) {

        return commentService.getIssueComments(userId, projectId, memberId);
    }


}
