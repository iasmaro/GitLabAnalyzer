package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dto.DiffDTO;
import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.gitlab.MergeRequestWrapper;
import com.haumea.gitanalyzer.dto.MergeRequestDTO;
import com.haumea.gitanalyzer.utility.GlobalConstants;
import org.gitlab4j.api.models.Diff;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.MergeRequestDiff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MergeRequestService {
    private final UserService userService;
    private final MemberService memberService;

    @Autowired
    public MergeRequestService(UserService userService, MemberService memberService) {

        this.userService = userService;
        this.memberService = memberService;
    }

    private GitlabService getGitLabService(String userId){

        String accessToken = userService.getPersonalAccessToken(userId);

        return new GitlabService(GlobalConstants.gitlabURL, accessToken);
    }

    private List<String> getAliasForMember(String memberId){

        return memberService.getAliasesForSelectedMember(memberId);
    }

    private List<MergeRequestWrapper> getMergeRequestWrapper(GitlabService gitlabService, int projectId, Date start, Date end) {

        return gitlabService.getFilteredMergeRequestsWithDiff(projectId, "master", start, end);
    }

    private List<MergeRequestWrapper> getMergeRequestWrapperForMember(GitlabService gitlabService, int projectId, Date start, Date end, List<String> alias) {

        return gitlabService.getFilteredMergeRequestsWithDiffByAuthor(projectId, "master", start, end, alias);
    }

    public List<DiffDTO> getMergeRequestDiffs(MergeRequestDiff mergeRequestDiff){

        List<DiffDTO> mergeRequestDiffs = new ArrayList<>();

        List<Diff> codeDiffs = mergeRequestDiff.getDiffs();

        for(Diff diff : codeDiffs){

            DiffDTO diffDTO = new DiffDTO(diff.getDiff(), diff.getNewPath(), diff.getDiff());

            mergeRequestDiffs.add(diffDTO);
        }

        return mergeRequestDiffs;
    }

    public MergeRequestDTO getMergeRequestDTO(MergeRequestWrapper mergeRequestWrapper){

        MergeRequest mergeRequest = mergeRequestWrapper.getMergeRequestData();

        int mergeRequestIiD = mergeRequest.getIid();
        Date mergedDate = mergeRequest.getMergedAt();
        Date createdDate = mergeRequest.getCreatedAt();
        Date updatedDate = mergeRequest.getUpdatedAt();

        //TODO: Update with method to calculate MR's score and member's score.
        double MRScore = 0.0;
        double memberScore = 0.0;

        List<DiffDTO> mergeRequestDiffs = getMergeRequestDiffs(mergeRequestWrapper.getMergeRequestDiff());

        return new MergeRequestDTO(mergeRequestIiD, mergedDate, createdDate, updatedDate, MRScore, memberScore, mergeRequestDiffs);
    }

    public List<MergeRequestDTO> getAllMergeRequests(String userId, int projectId, Date start, Date end){

        GitlabService gitlabService = getGitLabService(userId);

        List<MergeRequestWrapper> mergeRequestsList = getMergeRequestWrapper(gitlabService, projectId, start, end);

        List<MergeRequestDTO> mergeRequestDTOList = new ArrayList<>();

        for(MergeRequestWrapper mergeRequestWrapper : mergeRequestsList){

            MergeRequestDTO mergeRequestDTO = getMergeRequestDTO(mergeRequestWrapper);
            mergeRequestDTOList.add(mergeRequestDTO);
        }

        return mergeRequestDTOList;
    }

    public List<MergeRequestDTO> getAllMergeRequestsForMember(String userId, int projectId, String memberId, Date start, Date end){

        GitlabService gitlabService = getGitLabService(userId);

        List<String> alias = getAliasForMember(memberId);
        List<MergeRequestWrapper> mergeRequestsList = getMergeRequestWrapperForMember(gitlabService, projectId, start, end, alias);

        List<MergeRequestDTO> mergeRequestDTOList = new ArrayList<>();

        for(MergeRequestWrapper mergeRequestWrapper : mergeRequestsList){

            MergeRequestDTO mergeRequestDTO = getMergeRequestDTO(mergeRequestWrapper);
            mergeRequestDTOList.add(mergeRequestDTO);
        }

        return mergeRequestDTOList;
    }
}
