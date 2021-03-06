package com.haumea.gitanalyzer.gitlab;

import com.haumea.gitanalyzer.exception.GitLabRuntimeException;
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

    private boolean hasItem(List<String> items, String checkItem){
        return items.stream().anyMatch(item -> item.equals(checkItem));
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

    public List<MergeRequest> getFilteredMergeRequestsNoDiffs(Integer projectId,
                                                              String targetBranch,
                                                              Date start,
                                                              Date end){

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

        List<MergeRequest> filteredMergeRequests = new ArrayList<>();
        /*
         * have to filter for start time by merged_at, neither created_at or updated_at works
         * if we filter by created_at or updated_at directly, we risk excluding MRs that should included because
         * a MR whose created_at and updated_at Date are outside the time interval (start, end)
         * should be included as long it got merged within (start, end)
         * ---[created]---[updated]---[start]----[merged]----[end]---
         * */
        for(MergeRequest current : mergeRequests) {

            if(current.getMergedAt().after(start) && current.getMergedAt().before(end)){

                filteredMergeRequests.add(current);
            }
        }

        return filteredMergeRequests;

    }

    // we can't filter MRs by commit author without incur the cost of retrieving all commits of each MRs
    // this is why getFilteredMergeRequestsNoDiffsByAuthor calls getFilteredMergeRequestsWithDiffsByAuthor
    public List<MergeRequest> getFilteredMergeRequestsNoDiffsByAuthor(Integer projectId,
                                                                      String targetBranch,
                                                                      Date start,
                                                                      Date end,
                                                                      List<String> alias){

        List<MergeRequestWrapper> mergeRequests = getFilteredMergeRequestsWithDiffsByAuthor(
                projectId,
                targetBranch,
                start,
                end, alias);
        List<MergeRequest> filteredMergeRequests = new ArrayList<>();

        for(MergeRequestWrapper current : mergeRequests) {

            filteredMergeRequests.add(current.getMergeRequestData());

        }

        return filteredMergeRequests;

    }

    // get all MRs of a repo, keep only MRs that is merged into target branch within start and end Date
    public List<MergeRequestWrapper> getFilteredMergeRequestsWithDiffs(Integer projectId,
                                                                       String targetBranch,
                                                                       Date start,
                                                                       Date end) {

        List<MergeRequest> mergeRequests = getFilteredMergeRequestsNoDiffs(projectId, targetBranch, start, end);

        List<MergeRequestWrapper> filteredMergeRequests = new ArrayList<>();

        for(MergeRequest current : mergeRequests) {
            MergeRequestWrapper newMergeRequest = new MergeRequestWrapper(mergeRequestApi, projectId, current);
            filteredMergeRequests.add(newMergeRequest);
        }

        return filteredMergeRequests;

    }

    public List<MergeRequestWrapper> getFilteredMergeRequestsWithDiffsByAuthor(Integer projectId,
                                                                               String targetBranch,
                                                                               Date start,
                                                                               Date end,
                                                                               List<String> alias){

        List<MergeRequestWrapper> mergeRequests = getFilteredMergeRequestsWithDiffs(projectId, targetBranch, start, end);

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

    private List<Commit> getMergeRequestCommits(Integer projectId,
                                                Integer mergeRequestIid){
        List<Commit> commits;

        try{
            commits = mergeRequestApi.getCommits(projectId, mergeRequestIid);
        } catch (GitLabApiException e){
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

        return commits;
    }

    // get commits (metadata + diffs) associated with a MR
    public List<CommitWrapper> getMergeRequestCommitsWithDiffs(Integer projectId,
                                                               Integer mergeRequestIid){
        List<Commit> commits = getMergeRequestCommits(projectId, mergeRequestIid);

        List<CommitWrapper> commitsWithDiff = new ArrayList<>();
        for (Commit commit: commits){
            CommitWrapper newCommit = new CommitWrapper(projectId, commitsApi, commit);
            commitsWithDiff.add(newCommit);
        }

        return commitsWithDiff;
    }

    // get commits (metadata + diffs) associated with a MR then filter by commit author
    public List<CommitWrapper> getMergeRequestCommitsWithDiffsByAuthor(Integer projectId,
                                                                       Integer mergeRequestIid,
                                                                       List<String> alias){

        List<Commit> commits = getMergeRequestCommits(projectId, mergeRequestIid);

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

    // get commits (metadata) associated with a MR
    public List<Commit> getMergeRequestCommitsNoDiffs(Integer projectId,
                                                      Integer mergeRequestIid){

        return getMergeRequestCommits(projectId, mergeRequestIid);

    }

    // get commits (metadata) associated with a MR then filter by commit author
    public List<Commit> getMergeRequestCommitsNoDiffsByAuthor(Integer projectId,
                                                              Integer mergeRequestIid,
                                                              List<String> alias){

        List<Commit> commits = getMergeRequestCommits(projectId, mergeRequestIid);
        List<Commit> filterdCommits = new ArrayList<>();

        for(Commit current: commits){
            if(hasItem(alias, current.getAuthorName())){
                filterdCommits.add(current);
            }
        }

        return filterdCommits;

    }

    public List<Commit> getFilterdCommitsNoDiff(Integer projectId,
                                                 String targetBranch,
                                                 Date start,
                                                 Date end){
        try{
            return commitsApi.getCommits(projectId, targetBranch, start, end);
        } catch (GitLabApiException e){
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

    }

    public List<Commit> getFilterdCommitsNoDiffByAuthor(Integer projectId,
                                                        String targetBranch,
                                                        Date start,
                                                        Date end,
                                                        List<String> alias){
        List<Commit> commits = getFilterdCommitsNoDiff(projectId, targetBranch, start, end);

        List<Commit> filteredCommits = new ArrayList<>();
        for(Commit current: commits){
            if(hasItem(alias, current.getAuthorName())){
                filteredCommits.add(current);
            }
        }

        return filteredCommits;

    }

    // get all commits of a repo, filtered by target branch, start and end Date
    public List<CommitWrapper> getFilterdCommitsWithDiffs(Integer projectId,
                                                          String targetBranch,
                                                          Date start,
                                                          Date end){
        List<Commit> commits = getFilterdCommitsNoDiff(projectId, targetBranch, start, end);

        List<CommitWrapper> filteredCommits = new ArrayList<>();
        for(Commit current: commits){
            CommitWrapper commit = new CommitWrapper(projectId, commitsApi, current);
            filteredCommits.add(commit);

        }

        return filteredCommits;
    }

    // get all commits of a repo, filtered by target branch, start and end Date, and author
    public List<CommitWrapper> getFilterdCommitsWithDiffsByAuthor(Integer projectId,
                                                                  String targetBranch,
                                                                  Date start,
                                                                  Date end,
                                                                  List<String> alias){

        List<Commit> commits = getFilterdCommitsNoDiffByAuthor(projectId, targetBranch, start, end, alias);
        List<CommitWrapper> filteredCommits = new ArrayList<>();

        for(Commit current: commits){
            CommitWrapper commit = new CommitWrapper(projectId, commitsApi, current);
            filteredCommits.add(commit);
        }

        return filteredCommits;

    }

    // get all commits (metadata) from a repo
    // use this function for alias filtering
    public List<Commit> getAllCommitsNoDiff(Integer projectId){
        try {
            return commitsApi.getCommits(projectId);
        }catch (GitLabApiException e){
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }
    }

    // get all commits (metadata + diffs) from a repo
    public List<CommitWrapper> getAllCommitsWithDiff(Integer projectId){
        List<Commit> commits = getAllCommitsNoDiff(projectId);

        List<CommitWrapper> commitList = new ArrayList<>();

        for(Commit current : commits) {
            CommitWrapper newCommit = new CommitWrapper(projectId, commitsApi ,current);

            commitList.add(newCommit);
        }

        return commitList;
    }

    // get diffs of a commit
    public List<Diff> getCommitDiffs(Integer projectId, String commitId){

        try {
            return commitsApi.getDiff(projectId, commitId);
        }catch (GitLabApiException e){
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }
    }

    @Deprecated
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
