package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dto.CommitDTO;
import com.haumea.gitanalyzer.exception.GitLabRuntimeException;
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
import java.util.Date;
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

    private GitlabService createGitlabService(String userId) {
        String token = userService.getPersonalAccessToken(userId);

        return new GitlabService(GlobalConstants.gitlabURL, token);

    }

    public List<CommitDTO> getMergeRequestCommitsForMember(String userId, Integer projectId,
                                                           Integer mergeRequestId, String memberId) {
        GitlabService gitLabService = createGitlabService(userId);

        Member member = memberRepository.findMemberByMemberId(memberId);

        return gitLabService.getMergeRequestCommitsForMember(projectId, mergeRequestId, member);
    }

    public List<CommitDTO> getCommitsForSelectedMemberAndDate(String userId, int projectId, String memberId, Date start, Date end) {
        GitlabService gitlabService = createGitlabService(userId);

        Member member = memberRepository.findMemberByMemberId(memberId);
        List<CommitWrapper> filteredCommits;

        try {
            filteredCommits = gitlabService.filterCommitsForDateAndAuthor(projectId, memberId, start, end);
        }
        catch (GitLabApiException e) {
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

        List<CommitDTO> commitDtoList = new ArrayList<>();

        for(CommitWrapper currentCommit : filteredCommits) {
            CommitDTO newDto = new CommitDTO(currentCommit.getCommitData().getId(), currentCommit.getCommitData().getCommittedDate(),
                    currentCommit.getCommitData().getAuthorName(), 11);

            commitDtoList.add(newDto);
        }

        return commitDtoList;
    }


}
