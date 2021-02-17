package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.exception.GitLabRuntimeException;
import com.haumea.gitanalyzer.gitlab.CommitWrapper;
import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.gitlab.MergeRequestWrapper;
import com.haumea.gitanalyzer.dto.MergeRequestDTO;
import com.haumea.gitanalyzer.utility.GlobalConstants;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Diff;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MergeRequestService {
    private final UserService userService;
    private double memberScore;

    @Autowired
    public MergeRequestService(UserService userService) {
        this.userService = userService;
        memberScore = 0;
    }

    private Date convertStringToDate(String date) throws ParseException {

        Date convertedDate = new SimpleDateFormat("yyyy/MM/dd").parse(date);

        return convertedDate;
    }

    private Date convertPSTtoUTC(Date date){

        int convertedYear = date.getYear() + 1900;
        int convertMonth = date.getMonth();
        int convertedDate = date.getDate();

        Calendar calendar = new GregorianCalendar(convertedYear, convertMonth, convertedDate);
        TimeZone utc = TimeZone.getTimeZone("UTC");
        calendar.setTimeZone(utc);

        return calendar.getTime();
    }

    //TODO: Update the comparison with data from alias
    private boolean filterMemberId(String authorName, String authorEmail, String memberId){

        if(authorName.equals(memberId) || authorEmail.equals(memberId + "@sfu.ca")){

            return true;
        }

        return false;
    }

    private double getMRDiffScoreAndMemberScore(List<CommitWrapper> commits, String memberId){

        double MRDifScore = 0;
        int insertions = 0;
        int deletions = 0;

        //Temporarily comparing author and email to capture alias
        //TODO: Recalculate scores using more appropriate analysis
        for(CommitWrapper commit : commits){

            List<Diff> newCode = commit.getNewCode();
            for (Diff code : newCode) {

                int newInsertions = 0;
                newInsertions = StringUtils.countOccurrencesOf(code.getDiff(), "\n+");
                int newDeletions = 0;
                newDeletions = StringUtils.countOccurrencesOf(code.getDiff(), "\n-");

                insertions = insertions + newInsertions;
                deletions = deletions + newDeletions;

                if(filterMemberId(commit.getCommitData().getAuthorName(), commit.getCommitData().getAuthorEmail(), memberId)){

                    memberScore = memberScore + newInsertions + newDeletions*0.2;
                }

            }

        }

        MRDifScore = insertions + deletions*0.2;

        return MRDifScore;
    }

    public List<MergeRequestDTO> getAllMergeRequests(String userId, int projectId, String memberId, String start, String end, boolean memberFilter) throws GitLabRuntimeException{

        //String accessToken = userService.getPersonalAccessToken(userId);

        GitlabService gitlabService = new GitlabService("https://csil-git1.cs.surrey.sfu.ca/", "thDxkfQVmkRUJP9mKGsm");

        Project project = null;
        List<MergeRequestWrapper> mergeRequestsList = null;

        try {
            project = gitlabService.getSelectedProject(projectId);

            Date convertedStart = convertStringToDate(start);
            Date convertedEnd = convertStringToDate(end);

            Date convertedStartDate = convertPSTtoUTC(convertedStart);
            Date convertedEndDate = convertPSTtoUTC(convertedEnd);

            mergeRequestsList = gitlabService.filterMergeRequestByDate(projectId, project.getName(), convertedStartDate, convertedEndDate);

        } catch (GitLabApiException | ParseException e) {

            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

        List<MergeRequestDTO> normalizedMergeRequestDTOList = new ArrayList<>();

        for(int i = 0; i < mergeRequestsList.size(); i++){
            org.gitlab4j.api.models.MergeRequest mergeRequest = mergeRequestsList.get(i).getMergeRequestData();

            int mergeRequestIiD = mergeRequest.getIid();
            int mergeIiD = mergeRequest.getIid();
            Date mergedDate = mergeRequest.getMergedAt();
            Date createdDate = mergeRequest.getCreatedAt();
            Date updatedDate = mergeRequest.getUpdatedAt();

            List<CommitWrapper> commits = null;
            try {
                commits = gitlabService.getMergeRequestCommits(projectId, mergeRequestIiD);
            } catch (GitLabApiException e) {
                throw new GitLabRuntimeException(e.getLocalizedMessage());
            }

            memberScore = 0;
            double MRScore = Math.round(getMRDiffScoreAndMemberScore(commits, memberId)*10)/10.0;
            memberScore = Math.round(memberScore*10)/10.0;

            if(memberFilter && memberScore == 0.0){
                continue;
            }

            MergeRequestDTO normalizedMR = new MergeRequestDTO(mergeIiD, mergedDate, createdDate, updatedDate, MRScore, memberScore);
            normalizedMergeRequestDTOList.add(normalizedMR);
        }

        return normalizedMergeRequestDTOList;
    }
}
