package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dto.CommentDTO;
import com.haumea.gitanalyzer.gitlab.CommentWrapper;
import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.model.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final UserService userService;
    private final MemberService memberService;

    @Autowired
    public CommentService(UserService userService, MemberService memberService) {
        this.userService = userService;
        this.memberService = memberService;
    }

    private List<String> getAliasForMember(String memberId) {

        return memberService.getAliasesForSelectedMember(memberId);
    }

    public List<CommentDTO> getMergeRequestComments(String userId, int projectId, String memberId) {
        GitlabService gitlabService = userService.createGitlabService(userId);
        Configuration activeConfiguration = userService.getConfiguration(userId, projectId);

        List<CommentWrapper> commentWrappers = gitlabService.getMRCommentsByAuthor(
                projectId,
                activeConfiguration.getTargetBranch(),
                userService.getStart(userId),
                userService.getEnd(userId),
                getAliasForMember(memberId));



        return convertCommentWrappersToDTOs(commentWrappers);

    }

    public List<CommentDTO> getIssueComments(String userId, int projectId, String memberId) {

        GitlabService gitlabService = userService.createGitlabService(userId);
        Configuration activeConfiguration = userService.getConfiguration(userId, projectId);

        List<CommentWrapper> commentWrappers = gitlabService.getIssueCommentsByAuthor(
                projectId,
                userService.getStart(userId),
                userService.getEnd(userId),
                getAliasForMember(memberId));

        return convertCommentWrappersToDTOs(commentWrappers);
    }

    private List<CommentDTO> convertCommentWrappersToDTOs(List<CommentWrapper> commentWrappers) {
        List<CommentDTO> commentDTOS = new ArrayList<>();

        for(CommentWrapper current : commentWrappers) {

            CommentDTO commentDTO = new CommentDTO(
                    current.getUrl(),
                    current.getNote().getBody(),
                    current.getIsSameAuthor(),
                    current.getNote().getCreatedAt().toString());

            commentDTOS.add(commentDTO);

        }

        return commentDTOS;
    }



}
