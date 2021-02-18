package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dto.CommitDTO;
import com.haumea.gitanalyzer.exception.GitLabRuntimeException;
import com.haumea.gitanalyzer.exception.ResourceNotFoundException;
import com.haumea.gitanalyzer.gitlab.CommitWrapper;
import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.model.User;
import com.haumea.gitanalyzer.dao.MemberRepository;
import com.haumea.gitanalyzer.utility.GlobalConstants;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.haumea.gitanalyzer.model.Member;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommitService {

    private final UserService userService;
    private final MemberRepository memberRepository;

    @Autowired
    public CommitService(UserService userService, MemberRepository memberRepository) {
        this.userService = userService;
        this.memberRepository = memberRepository;
    }

    public List<CommitDTO> getMergeRequestCommitsForMember(String userId, Integer projectId,
                                                           Integer mergeRequestId, String memberId) throws GitLabRuntimeException {

        String token = userService.getPersonalAccessToken(userId);

        GitlabService gitlabService = new GitlabService(GlobalConstants.gitlabURL, token);

        List<CommitDTO> memberCommits= new ArrayList<>();

        Member member = memberRepository.findMemberByMemberId(memberId);

        if(member == null){
            throw new ResourceNotFoundException("Member not found!");
        }

        try {
            List<CommitWrapper> mergeRequestCommits = gitlabService.getMergeRequestCommits(projectId, mergeRequestId);

            for(CommitWrapper currentCommit : mergeRequestCommits) {
                if(member.getAlias().contains(currentCommit.getCommitData().getAuthorName())){
                    CommitDTO commit = new CommitDTO(currentCommit.getCommitData().getId(), currentCommit.getCommitData().getCommittedDate(), currentCommit.getCommitData().getAuthorName(), 0);
                    memberCommits.add(commit);
                }
            }
            return memberCommits;

        }
        catch (GitLabApiException e){
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }
    }
}
