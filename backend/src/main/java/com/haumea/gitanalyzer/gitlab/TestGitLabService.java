package com.haumea.gitanalyzer.gitlab;

import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Diff;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TestGitLabService {

    private GitlabService gitlabService;
    private Boolean verbose;

    public TestGitLabService(GitlabService gitlabService, Boolean verbose) {
        this.gitlabService = gitlabService;
        this.verbose = verbose;
    }

    private void printCommitWrappers(List<CommitWrapper> commits) {
        for (CommitWrapper current : commits) {
            System.out.println(
                    "sha: " + current.getCommitData().getId() +
                            ", author: " + current.getCommitData().getAuthorName() +
                            ", message: " + current.getCommitData().getMessage() +
                            ", # diffs: " + current.getNewCode().size());
            if(verbose){
                for (Diff diff : current.getNewCode()) {
                    System.out.println("diff: " + diff.getDiff());
                }
            }

        }
    }

    private void printCommits(List<Commit> commits) {
        for (Commit current : commits) {
            System.out.println(
                    "sha: " + current.getId() +
                            ", author: " + current.getAuthorName() +
                            ", message: " + current.getMessage());
        }
    }

    private void printMergeRequests(List<MergeRequest> MRs) {
        for (MergeRequest current : MRs) {
            System.out.println(
                            "iid: " + current.getIid() +
                            ", state: " + current.getState() +
                            ", merged at: " + current.getMergedAt());
        }
    }

    private void printMergeRequestWrappers(List<MergeRequestWrapper> MRs) {
        for(MergeRequestWrapper current: MRs){

            System.out.println(
                    "iid: " + current.getMergeRequestData().getIid() +
                            ", state: " + current.getMergeRequestData().getState() +
                            ", merged at: " + current.getMergeRequestData().getMergedAt() +
                            ", # commits: " + current.getMergeRequestDiff().getCommits().size() +
                            ", # diffs: " + current.getMergeRequestDiff().getDiffs().size());

            List<Commit> commits = current.getMergeRequestDiff().getCommits();
            List<String> commitAuthors = new ArrayList<>();
            for (Commit commit: commits){
                commitAuthors.add(commit.getAuthorName());
            }
            System.out.println("commit authors: " + Arrays.toString(commitAuthors.toArray()));
        }
    }

    public void testgetProjects(){

        System.out.println("\n---Testing getProjects---");
        List<ProjectWrapper> projectWrappers = gitlabService.getProjects();

        for(ProjectWrapper current: projectWrappers){
            System.out.println(current.getProject().getName() + ", " + current.getProject().getId());
        }

    }

    public void testGetSelectedProject(Integer projectID){

        System.out.println("\n---Testing getSelectedProject---");
        Project project = gitlabService.getSelectedProject(projectID);
        System.out.println(project.getName() + ", " + project.getId());

    }

    public void testGetMembers(Integer projectID){

        System.out.println("\n---Testing getMembers---");
        List<MemberWrapper> members = gitlabService.getMembers(projectID);
        for(MemberWrapper current: members){
            System.out.println(current.getUsername());
        }
    }

    public void testGetFilteredMergeRequestsNoDiff(Integer projectId,
                                                   String targetBranch,
                                                   Date start,
                                                   Date end){

        System.out.println("\n---Testing getFilteredMergeRequestsNoDiff---");
        List<MergeRequest> MRs = gitlabService.getFilteredMergeRequestsNoDiff(
                projectId,
                targetBranch,
                start,
                end);

        printMergeRequests(MRs);

    }

    public void testGetFilteredMergeRequestsNoDiffByAuthor(Integer projectId,
                                                            String targetBranch,
                                                            Date start,
                                                            Date end,
                                                            List<String> alias){

        System.out.println("\n---Testing getFilteredMergeRequestsNoDiffByAuthor---");
        List<MergeRequest> MRs = gitlabService.getFilteredMergeRequestsNoDiffByAuthor(
                projectId,
                targetBranch,
                start,
                end,
                alias);

        printMergeRequests(MRs);

    }

    public void testGetFilteredMergeRequestsWithDiff(Integer projectID,
                                                     String targetBranch,
                                                     Date start,
                                                     Date end){
        System.out.println("\n---Testing getFilteredMergeRequestsWithDiff---");
        List<MergeRequestWrapper> MRs = gitlabService.getFilteredMergeRequestsWithDiff(projectID, targetBranch, start, end);

        printMergeRequestWrappers(MRs);
    }

    public void testGetFilteredMergeRequestsWithDiffByAuthor(Integer projectID,
                                                             String targetBranch,
                                                             Date start,
                                                             Date end,
                                                             List<String> alias){
        System.out.println("\n---Testing getFilteredMergeRequestsWithDiffByAuthor---");
        List<MergeRequestWrapper> MRs = gitlabService.getFilteredMergeRequestsWithDiffByAuthor(
                projectID,
                targetBranch,
                start,
                end,
                alias);

        printMergeRequestWrappers(MRs);
    }

    public void testGetMergeRequestCommitsWithDiff(Integer projectId, Integer mergeRequestIid){
        System.out.println("\n---Testing getMergeRequestCommitsWithDiff---");
        List<CommitWrapper> commits = gitlabService.getMergeRequestCommitsWithDiff(projectId, mergeRequestIid);

        printCommitWrappers(commits);
    }

    public void testGetMergeRequestCommitsWithDiffByAuthor(Integer projectId,
                                                           Integer mergeRequestIid,
                                                           List<String> alias){
        System.out.println("\n---Testing getMergeRequestCommitsWithDiffByAuthor---");
        List<CommitWrapper> commits = gitlabService.getMergeRequestCommitsWithDiffByAuthor(
                projectId,
                mergeRequestIid,
                alias);

        printCommitWrappers(commits);
    }

    public void testGetMergeRequestCommitsNoDiff(Integer projectId, Integer mergeRequestIid){
        System.out.println("\n---Testing getMergeRequestCommitsNoDiff---");
        List<Commit> commits = gitlabService.getMergeRequestCommitsNoDiff(projectId, mergeRequestIid);

        printCommits(commits);
    }

    public void testGetMergeRequestCommitsNoDiffByAuthor(Integer projectId,
                                                         Integer mergeRequestIid,
                                                         List<String> alias){
        System.out.println("\n---Testing getMergeRequestCommitsNoDiffByAuthor---");
        List<Commit> commits = gitlabService.getMergeRequestCommitsNoDiffByAuthor(
                projectId,
                mergeRequestIid,
                alias);

        printCommits(commits);
    }

    public void testGetFilterdCommitsNoDiff(Integer projectId,
                                                      String targetBranch,
                                                      Date start,
                                                      Date end){
        System.out.println("\n---Testing getFilterdCommitsNoDiff---");
        List<Commit> commits = gitlabService.getFilterdCommitsNoDiff(
                projectId,
                targetBranch,
                start,
                end);


        printCommits(commits);
    }

    public void testGetFilterdCommitsNoDiffByAuthor(Integer projectId,
                                                   String targetBranch,
                                                   Date start,
                                                   Date end,
                                                   List<String> alias){
        System.out.println("\n---Testing getFilterdCommitsNoDiffByAuthor---");
        List<Commit> commits = gitlabService.getFilterdCommitsNoDiffByAuthor(
                projectId,
                targetBranch,
                start,
                end,
                alias);


        printCommits(commits);
    }

    public void testGetFilterdCommitsWithDiff(Integer projectId,
                                              String targetBranch,
                                              Date start,
                                              Date end){
        System.out.println("\n---Testing getFilterdCommitsWithDiff---");
        List<CommitWrapper> commits = gitlabService.getFilterdCommitsWithDiff(
                projectId,
                targetBranch,
                start,
                end);

        printCommitWrappers(commits);

    }

    public void testGetFilterdCommitsWithDiffByAuthor(Integer projectId,
                                                      String targetBranch,
                                                      Date start,
                                                      Date end,
                                                      List<String> alias){
        System.out.println("\n---Testing getFilterdCommitsWithDiffByAuthor---");
        List<CommitWrapper> commits = gitlabService.getFilterdCommitsWithDiffByAuthor(
                projectId,
                targetBranch,
                start,
                end,
                alias);

        printCommitWrappers(commits);

    }

    public void testGetAllCommitsNoDiff(Integer projectId){
        System.out.println("\n---Testing getAllCommitsNoDiff---");
        List<Commit> commits = gitlabService.getAllCommitsNoDiff(projectId);
        printCommits(commits);

    }

    public void testGetAllCommitsWithDiff(Integer projectId){
        System.out.println("\n---Testing getAllCommitsWithDiff---");
        List<CommitWrapper> commits = gitlabService.getAllCommitsWithDiff(projectId);
        printCommitWrappers(commits);

    }

    public void testGetCommitDiffs(Integer projectId, String commitId){
        System.out.println("\n---Testing getCommitDiffs---");
        List<Diff> diffs = gitlabService.getCommitDiffs(projectId, commitId);

        for(Diff diff: diffs){
            System.out.println(diff.getDiff());
        }
    }

    public void testGetMergeRequestDiffs(Integer projectId, Integer mergeRequestIid){
        System.out.println("\n---Testing getMergeRequestDiffs---");
        MergeRequestWrapper mergeRequestWrapper = gitlabService.getMergeRequestDiffs(projectId, mergeRequestIid);

        System.out.println(
                        "# commits: " + mergeRequestWrapper.getMergeRequestDiff().getCommits().size() +
                        ", # diffs: " + mergeRequestWrapper.getMergeRequestDiff().getDiffs().size());
    }


}
