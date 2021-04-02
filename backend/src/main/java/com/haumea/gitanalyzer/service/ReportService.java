package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dto.CommitDTO;
import com.haumea.gitanalyzer.dto.MergeRequestDTO;
import com.haumea.gitanalyzer.dto.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final MergeRequestService mergeRequestService;
    private final CommitService commitService;
    private final CommentService commentService;

    private final MemberService memberService;

    @Autowired
    public ReportService(MergeRequestService mergeRequestService, CommitService commitService, CommentService commentService, MemberService memberService) {
        this.mergeRequestService = mergeRequestService;
        this.commitService = commitService;
        this.commentService = commentService;
        this.memberService = memberService;
    }

    public ReportDTO getReportForRepository(String userId, int projectId) {

        Map<String, List<MergeRequestDTO>> mergeRequestListByMemberId = new HashMap<>();
        Map<String, List<CommitDTO>> commitListByMemberId = new HashMap<>();
        List<String> userList = new ArrayList<>();

        userList.add(userId);

        List<String> memberList = memberService.getMembers(userId, projectId);

        for(String member : memberList) {

            List<MergeRequestDTO> mergeRequestDTOs = mergeRequestService.getAllMergeRequestsForMember(userId, projectId, member);
            List<CommitDTO> commitDTOs = commitService.getCommitsForSelectedMemberAndDate(userId, projectId, member);

            mergeRequestListByMemberId.put(member, mergeRequestDTOs);
            commitListByMemberId.put(member, commitDTOs);

        }

        return new ReportDTO(mergeRequestListByMemberId, commitListByMemberId, userList);

    }

}
