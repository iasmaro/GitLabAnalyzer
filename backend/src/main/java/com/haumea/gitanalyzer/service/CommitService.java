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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private Date convertStringToUTCDate(String date) throws ParseException {
        Date pstDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);

        final Calendar calendar  = Calendar.getInstance();
        final int utcOffset = calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET);

        return new Date(pstDate.getTime() - utcOffset);
    }


    public List<CommitDTO> getCommitsForSelectedMemberAndDate(String userId, int projectId, String memberId, Date start, Date end) throws ParseException {
        GitlabService gitlabService = createGitlabService(userId);

        Member member = memberRepository.findMemberByMemberId(memberId);
        List<CommitWrapper> filteredCommits;

//        Date startDate = convertStringToUTCDate(start);
//        Date endDate = convertStringToUTCDate(end);

        try {
            filteredCommits = gitlabService.filterCommitsForDateAndAuthor(projectId, memberId, start, end);

            System.out.println("size is " + filteredCommits.size());
        }
        catch (GitLabApiException e) {
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

        List<CommitDTO> commitDtoList = new ArrayList<>();

        for(CommitWrapper currentCommit : filteredCommits) {
            CommitDTO newDto = new CommitDTO(currentCommit.getCommitData().getId(), currentCommit.getCommitData().getCommittedDate(),
                    currentCommit.getCommitData().getAuthorName(), 11);

            commitDtoList.add(newDto);

            System.out.println("in dto loop");
        }

        return commitDtoList;
    }


}
