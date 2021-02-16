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


    //TODO: Update the comparison with data from alias
    private boolean filterMemberID(String authorName, String authorEmail, String memberID){

        if(authorName.equals(memberID) || authorEmail.equals(memberID + "@sfu.ca")){

            return true;
        }

        return false;
    }

    private double getMRDiffScoreAndMemberScore(List<CommitWrapper> commits, String memberID) throws GitLabRuntimeException {

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

                if(filterMemberID(commit.getCommitData().getAuthorName(), commit.getCommitData().getAuthorEmail(), memberID)){

                    memberScore = memberScore + newInsertions + newDeletions*0.2;
                }

            }

        }

        MRDifScore = insertions + deletions*0.2;

        return MRDifScore;
    }

    private Date convertPSTtoUTC(Date date){

        int convertedYear = date.getYear() + 1900;
        int convertMonth = date.getMonth() - 1;
        int convertedDate = date.getDate();

        Calendar calendar = new GregorianCalendar(convertedYear, convertMonth, convertedDate);
        TimeZone utc = TimeZone.getTimeZone("UTC");
        calendar.setTimeZone(utc);

        return calendar.getTime();
    }

    public List<MergeRequestDTO> getAllMergeRequests(String userID, int projectID, String memberID, Date start, Date end){

        String accessToken = userService.getPersonalAccessToken(userID);

        GitlabService gitlabService = new GitlabService(GlobalConstants.gitlabURL, accessToken);

        Project project = null;
        try {
            project = gitlabService.getSelectedProject(projectID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<MergeRequestWrapper> mergeRequestsList = null;
        try {

            Date convertedStartDate = convertPSTtoUTC(start);
            Date convertedEndDate = convertPSTtoUTC(end);

            mergeRequestsList = gitlabService.filterMergeRequestByDate(projectID, project.getName(), convertedStartDate, convertedEndDate);
        } catch (GitLabApiException e) {
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
                commits = gitlabService.getMergeRequestCommits(projectID, mergeRequestIiD);
            } catch (GitLabApiException e) {
                e.printStackTrace();
            }

            double memberScore = 0;
            double MRScore = Math.round(getMRDiffScoreAndMemberScore(commits, memberID)*10)/10.0;
            memberScore = Math.round(memberScore*10)/10.0;

            MergeRequestDTO normalizedMR = new MergeRequestDTO(mergeIiD, mergedDate, createdDate, updatedDate, MRScore, memberScore);
            normalizedMergeRequestDTOList.add(normalizedMR);
        }

        return normalizedMergeRequestDTOList;
    }
}
