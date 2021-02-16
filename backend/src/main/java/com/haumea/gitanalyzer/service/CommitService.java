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

import java.util.ArrayList;
import java.util.List;

@Service
public class CommitService {

    private final UserService userService;

    @Autowired
    public CommitService(UserService userService) {
        this.userService = userService;
    }

    public List<CommitDTO> getMergeRequestCommitsForMember(String userId, Integer projectId,
                                                           Integer mergeRequestId, String memberId) throws GitLabRuntimeException {

        String token = userService.getPersonalAccessToken(userId);

        GitlabService gitLabService = new GitlabService(GlobalConstants.gitlabURL, token);

        try {
            List<CommitWrapper> mergeRequestCommits = gitLabService.getMergeRequestCommits(projectId, mergeRequestId);
            List<CommitDTO> memberCommits= new ArrayList<>();

            for(CommitWrapper currentCommit : mergeRequestCommits) {
                if(currentCommit.getCommitData().getAuthorName() == memberId) {
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
