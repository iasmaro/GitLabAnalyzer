package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.exception.GitLabRuntimeException;
import com.haumea.gitanalyzer.gitlab.CommitWrapper;
import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.model.MergeRequest;
import com.haumea.gitanalyzer.model.User;
import com.haumea.gitanalyzer.utility.GlobalConstants;
import org.gitlab4j.api.CommitsApi;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.MergeRequestApi;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Diff;
import org.gitlab4j.api.models.MergeRequestDiff;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MergeRequestService {
    private final UserService userService;
    private double memberScore;

    @Autowired
    public MergeRequestService(UserService userService) {
        this.userService = userService;
        memberScore = 0;
    }

    private List<org.gitlab4j.api.models.MergeRequest> filterMergedMRsForDate(GitlabService gitlabService, Project project, Date start, Date end) throws GitLabApiException {

        MergeRequestApi mergeRequestApi = gitlabService.getMergeRequestApi();
        List<org.gitlab4j.api.models.MergeRequest> mergeRequestsList = mergeRequestApi.getMergeRequests(project);

        List<org.gitlab4j.api.models.MergeRequest> filteredMrs = new ArrayList<>();

        for(int i = 0; i < mergeRequestsList.size(); i++){

            org.gitlab4j.api.models.MergeRequest mergeRequest = mergeRequestsList.get(i);
            Date mergedDate = mergeRequest.getMergedAt();
            if(mergedDate == null || mergedDate.before(start) || mergedDate.after(end) || !mergeRequest.getState().equals("merged")){

                continue;
            }

            filteredMrs.add(mergeRequest);
        }

        return filteredMrs;
    }

    private double getMRDiffScoreAndMemberScore(List<CommitWrapper> commits, String memberID) throws GitLabRuntimeException {

        double MRDifScore = 0;
        int insertions = 0;
        int deletions = 0;

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

                //Temporarily comparing author and email to capture alias
                //TODO: Update the comparison with data from alias
                if(commit.getCommitData().getAuthorName().equals(memberID) || (commit.getCommitData().getAuthorEmail().equals(memberID + "@sfu.ca"))){

                    memberScore = memberScore + newInsertions + newDeletions*0.2;
                }

            }

        }

        MRDifScore = insertions + deletions*0.2;

        return MRDifScore;
    }

    public List<MergeRequest> getAllMergeRequests(String userID, int projectID, String memberID, Date start, Date end) throws Exception {

        String accessToken = userService.getPersonalAccessToken(userID);

        GitlabService gitlabService = new GitlabService(GlobalConstants.gitlabURL, accessToken);

        Project project = null;
        project = gitlabService.getSelectedProject(projectID);

        List<org.gitlab4j.api.models.MergeRequest> mergeRequestsList = filterMergedMRsForDate(gitlabService, project, start, end);

        List<MergeRequest> normalizedMergeRequestList = new ArrayList<>();

        for(int i = 0; i < mergeRequestsList.size(); i++){
            org.gitlab4j.api.models.MergeRequest mergeRequest = mergeRequestsList.get(i);

            int mergeRequestIiD = mergeRequest.getIid();
            int mergeID = mergeRequest.getId();
            Date mergeDate = mergeRequest.getMergedAt();

            List<CommitWrapper> commits = gitlabService.getMergeRequestCommits(projectID, mergeRequestIiD);

            double memberScore = 0;
            double MRScore = Math.round(getMRDiffScoreAndMemberScore(commits, memberID)*10)/10.0;
            memberScore = Math.round(memberScore*10)/10.0;

            MergeRequest normalizedMR = new MergeRequest(userID, projectID, memberID, mergeID, mergeDate, MRScore, memberScore);
            normalizedMergeRequestList.add(normalizedMR);
        }

        return normalizedMergeRequestList;
    }
}
