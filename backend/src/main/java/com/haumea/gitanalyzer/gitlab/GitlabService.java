package com.haumea.gitanalyzer.gitlab;

import com.haumea.gitanalyzer.exception.GitLabRuntimeException;
import io.swagger.models.auth.In;
import org.gitlab4j.api.*;
import org.gitlab4j.api.models.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
Class that shadows the functionality of our front end web client

Uses wrapper classes and will be used by our spring boot code to hand back data

*/

public class GitlabService {
    private GitLabApi gitLabApi;
    private MergeRequestApi mergeRequestApi;
    private CommitsApi commitsApi;
    private ProjectApi projectApi;
    private String hostUrl;
    private String personalAccessToken;

    public GitlabService(String hostUrl, String personalAccessToken) {
        this.hostUrl = hostUrl;
        this.personalAccessToken = personalAccessToken;
        this.gitLabApi = new GitLabApi(hostUrl, personalAccessToken);
        this.projectApi = new ProjectApi(gitLabApi);
        this.mergeRequestApi = new MergeRequestApi(gitLabApi);
        this.commitsApi = new CommitsApi(gitLabApi);
    }

    public GitLabApi getGitLabApi() {
        return gitLabApi;
    }

    public ProjectApi getProjectApi() {
        return projectApi;
    }

    public MergeRequestApi getMergeRequestApi() {
        return mergeRequestApi;
    }

    public CommitsApi getCommitsApi() {
        return commitsApi;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public String getPersonalAccessToken() {
        return personalAccessToken;
    }

    public List<ProjectWrapper> getProjects() {
        List<Project> projectList;

        try{
            projectList = gitLabApi.getProjectApi().getMemberProjects();
        } catch (GitLabApiException e){
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

        List<ProjectWrapper> projects = new ArrayList<>();

        for(Project current : projectList) {
            ProjectWrapper project = new ProjectWrapper(current);

            projects.add(project);
        }

        return projects;

    }

    public Project getSelectedProject(Integer projectID) {
        Project selectedProject;

        try{
            selectedProject = projectApi.getProject(projectID);
        } catch (GitLabApiException e){
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

        return selectedProject;
    }

    public List<MemberWrapper> getMembers(Integer projectId) {

        List<org.gitlab4j.api.models.Member> members;

        try{
            members = projectApi.getAllMembers(projectId);
        } catch (GitLabApiException e){
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

        List<MemberWrapper> allMembers = new ArrayList<>();

        for(org.gitlab4j.api.models.Member current : members) {
            MemberWrapper newMemberWrapper = new MemberWrapper(current.getName(), current.getEmail(), current.getUsername(), current.getId());

            allMembers.add(newMemberWrapper);

        }
        return allMembers;
    }

    // get all MRs of a repo, keep only MRs that is merged into target branch within start and end Date
    public List<MergeRequestWrapper> getFilteredMergeRequests(Integer projectId,
                                                              String targetBranch,
                                                              Date start,
                                                              Date end) {

        MergeRequestFilter filter = new MergeRequestFilter();
        filter.setProjectId(projectId);
        filter.setState(Constants.MergeRequestState.MERGED);
        filter.setTargetBranch(targetBranch);
        filter.setCreatedBefore(end);

        List<MergeRequest> mergeRequests;
        try{
            mergeRequests = mergeRequestApi.getMergeRequests(filter);
        } catch (GitLabApiException e){
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

        List<MergeRequestWrapper> result = new ArrayList<>();

        /*
         * have to filter for start time by merged_at, neither created_at or updated_at works
         * if we filter by created_at or updated_at directly, we risk excluding MRs that should included because
         * a MR whose created_at and updated_at Date are outside the time interval (start, end)
         * should be included as long it got merged within (start, end)
         * ---[created]---[updated]---[start]----[merged]----[end]---
         * */
        for(MergeRequest current : mergeRequests) {

            if(current.getMergedAt().after(start)){

                // the constructor of CommitWrapper incurs 2 API calls
                // to get the latest MR version then get the MR diff
                // which include list of commits (with metadata only, no diffs) and list of MR diffs
                MergeRequestWrapper newMergeRequest = new MergeRequestWrapper(mergeRequestApi, projectId, current);

                result.add(newMergeRequest);
            }
        }

        return result;

    }

    public List<MergeRequestWrapper> getFilteredMergeRequestsByAuthor(Integer projectId,
                                                                      String targetBranch,
                                                                      Date start,
                                                                      Date end,
                                                                      List<String> alias){

        List<MergeRequestWrapper> mergeRequests = getFilteredMergeRequests(projectId, targetBranch, start, end);

        List<MergeRequestWrapper> filteredMergeRequests = new ArrayList<>();

        for (MergeRequestWrapper currentMR: mergeRequests){
            for(Commit currentCommit: currentMR.getMergeRequestDiff().getCommits()){
                if(hasItem(alias, currentCommit.getAuthorName())){
                    filteredMergeRequests.add(currentMR);
                    break;
                }
            }
        }

        return filteredMergeRequests;

    }

    private List<Commit> getMergeRequestCommits(Integer projectId, Integer mergeRequestId){
        List<Commit> commits;

        try{
            commits = mergeRequestApi.getCommits(projectId, mergeRequestId);
        } catch (GitLabApiException e){
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

        return commits;
    }

    // get commits associated with a MR
    public List<CommitWrapper> getMergeRequestCommitsWithDiffs(Integer projectId, Integer mergeRequestId){
        List<Commit> commits = getMergeRequestCommits(projectId, mergeRequestId);

        List<CommitWrapper> commitsWithDiff = new ArrayList<>();
        for (Commit commit: commits){
            CommitWrapper newCommit = new CommitWrapper(projectId, commitsApi, commit);
            commitsWithDiff.add(newCommit);
        }

        return commitsWithDiff;
    }

    // get commits associated with a MR then filter by commit author
    public List<CommitWrapper> getMergeRequestCommitsWithDiffsByAuthor(Integer projectId,
                                                              Integer mergeRequestId,
                                                              List<String> alias){

        List<Commit> commits = getMergeRequestCommits(projectId, mergeRequestId);

        List<CommitWrapper> commitsWithDiff = new ArrayList<>();
        for (Commit commit: commits){

            // the constructor of CommitWrapper incurs 1 API to get the commit diffs, thus,
            // we won't create a CommitWrapper object unless it pass the filter condition
            // which is also why we don't call getMergeRequestCommits directly then filter
            if(hasItem(alias, commit.getAuthorName())){
                CommitWrapper newCommit = new CommitWrapper(projectId, commitsApi, commit);
                commitsWithDiff.add(newCommit);
            }
        }

        return commitsWithDiff;

    }

    // get all commits of a repo, filtered by target branch, start and end Date
    public List<CommitWrapper> getFilterdCommits(Integer projectId,
                                                 String targetBranch,
                                                 Date start,
                                                 Date end){
        List<Commit> commits;
        try{
            commits = commitsApi.getCommits(projectId, targetBranch, start, end);
        } catch (GitLabApiException e){
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

        List<CommitWrapper> filteredCommits = new ArrayList<>();
        for(Commit current: commits){
            CommitWrapper commit = new CommitWrapper(projectId, commitsApi, current);
            filteredCommits.add(commit);

        }

        return filteredCommits;
    }

    // get all commits of a repo, filtered by target branch, start and end Date, and author
    public List<CommitWrapper> getFilterdCommitByAuthor(Integer projectId,
                                                        String targetBranch,
                                                        Date start,
                                                        Date end,
                                                        String authorName){

        List<CommitWrapper> commits = getFilterdCommits(projectId, targetBranch, start, end);
        List<CommitWrapper> filteredCommits = new ArrayList<>();

        for(CommitWrapper current: commits){
            if(current.getCommitData().getAuthorName().equals(authorName)){
                CommitWrapper commit = new CommitWrapper(projectId, commitsApi, current.getCommitData());
                filteredCommits.add(commit);
            }
        }

        return filteredCommits;

    }

    // get all commits from a repo
    public List<CommitWrapper> getAllCommits(Integer projectId) {
        List<Commit> commits;
        try{
            commits = commitsApi.getCommits(projectId);
        } catch (GitLabApiException e){
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

        List<CommitWrapper> commitList = new ArrayList<>();

        for(Commit current : commits) {
            CommitWrapper newCommit = new CommitWrapper(projectId, commitsApi ,current);

            commitList.add(newCommit);
        }

        return commitList;
    }

    private boolean hasItem(List<String> items, String checkItem){
        return items.stream().anyMatch(item -> item.equals(checkItem));
    }

    public List<String> getAlias(Integer projectId){

        List<CommitWrapper> commits = getAllCommits(projectId);
        List<String> alias = new ArrayList<>();

        for(CommitWrapper current: commits){
            String name = current.getCommitData().getAuthorName();
            if(!hasItem(alias, name)){
                alias.add(name);
            }
        }

        return alias;

    }

    @Deprecated
    public List<MergeRequestWrapper> getMergeRequestForMember(int projectId, String name) throws GitLabApiException {
        List<MergeRequestWrapper> filteredList = new ArrayList<>();

        for(MergeRequestWrapper currentMergeRequest : getAllMergeRequests(projectId)) {
            if(currentMergeRequest.getMergeRequestData().getAuthor().getName().equals(name)) {
                filteredList.add(currentMergeRequest);
            }

        }

        return filteredList;

    }

    @Deprecated
    public List<MergeRequest> getAllMergeRequestData(int projectId) throws GitLabApiException {
        return mergeRequestApi.getMergeRequests(projectId);

    }

    @Deprecated
    public List<MergeRequestWrapper> getAllMergeRequests(int projectId) throws GitLabApiException {

        List<MergeRequestWrapper> mergeRequestList = new ArrayList<>();

        for(MergeRequest currentMR : mergeRequestApi.getMergeRequests(projectId)) {

            MergeRequestWrapper newMergeRequest = new MergeRequestWrapper(mergeRequestApi, projectId, currentMR);

            mergeRequestList.add(newMergeRequest);

        }
        return mergeRequestList;
    }

    @Deprecated
    public List<CommitWrapper> getMergeRequestCommits(int projectId, int mergeRequestId) throws GitLabApiException {
        List<CommitWrapper> commitListMR = new ArrayList<>();

        for(Commit currentCommit : mergeRequestApi.getCommits(projectId, mergeRequestId)) {
            CommitWrapper newCommit = new CommitWrapper(projectId, commitsApi, currentCommit);

            commitListMR.add(newCommit);
        }

        return commitListMR;
    }

    @Deprecated
    public List<Commit> getMergeRequestCommitsData(int projectId, int mergeRequestId) throws GitLabApiException {

        return mergeRequestApi.getCommits(projectId, mergeRequestId);
    }

    @Deprecated
    public List<CommitWrapper> filterCommitsForDateAndAuthor(int projectId, String authorName, Date start, Date end) throws GitLabApiException {
        CommitsApi commitsApi = new CommitsApi(gitLabApi);
        List<CommitWrapper> commitList = new ArrayList<>();

        for(Commit currentCommit : commitsApi.getCommits(projectId, "master", start, end)) {
            if(currentCommit.getAuthorName().equals(authorName) ) {
                CommitWrapper newCommit = new CommitWrapper(projectId, commitsApi, currentCommit);

                commitList.add(newCommit);

            }
        }

        return commitList;

    }

}
