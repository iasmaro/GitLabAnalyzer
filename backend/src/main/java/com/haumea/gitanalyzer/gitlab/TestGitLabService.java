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

    public void testGetFilteredMergeRequestsNoDiffs(Integer projectId,
                                                    String targetBranch,
                                                    Date start,
                                                    Date end){

        System.out.println("\n---Testing getFilteredMergeRequestsNoDiffs---");
        List<MergeRequest> MRs = gitlabService.getFilteredMergeRequestsNoDiffs(
                projectId,
                targetBranch,
                start,
                end);

        printMergeRequests(MRs);

    }

    public void testGetFilteredMergeRequestsNoDiffsByAuthor(Integer projectId,
                                                            String targetBranch,
                                                            Date start,
                                                            Date end,
                                                            List<String> alias){

        System.out.println("\n---Testing getFilteredMergeRequestsNoDiffsByAuthor---");
        List<MergeRequest> MRs = gitlabService.getFilteredMergeRequestsNoDiffsByAuthor(
                projectId,
                targetBranch,
                start,
                end,
                alias);

        printMergeRequests(MRs);

    }

    public void testGetFilteredMergeRequestsWithDiffs(Integer projectID,
                                                      String targetBranch,
                                                      Date start,
                                                      Date end){
        System.out.println("\n---Testing getFilteredMergeRequestsWithDiffs---");
        List<MergeRequestWrapper> MRs = gitlabService.getFilteredMergeRequestsWithDiffs(projectID, targetBranch, start, end);

        printMergeRequestWrappers(MRs);
    }

    public void testGetFilteredMergeRequestsWithDiffsByAuthor(Integer projectID,
                                                              String targetBranch,
                                                              Date start,
                                                              Date end,
                                                              List<String> alias){
        System.out.println("\n---Testing getFilteredMergeRequestsWithDiffsByAuthor---");
        List<MergeRequestWrapper> MRs = gitlabService.getFilteredMergeRequestsWithDiffsByAuthor(
                projectID,
                targetBranch,
                start,
                end,
                alias);

        printMergeRequestWrappers(MRs);
    }

    public void testGetMergeRequestCommitsWithDiffs(Integer projectId, Integer mergeRequestIid){
        System.out.println("\n---Testing getMergeRequestCommitsWithDiffs---");
        List<CommitWrapper> commits = gitlabService.getMergeRequestCommitsWithDiffs(projectId, mergeRequestIid);

        printCommitWrappers(commits);
    }

    public void testGetMergeRequestCommitsWithDiffsByAuthor(Integer projectId,
                                                                   Integer mergeRequestIid,
                                                                   List<String> alias){
        System.out.println("\n---Testing getMergeRequestCommitsWithDiffsByAuthor---");
        List<CommitWrapper> commits = gitlabService.getMergeRequestCommitsWithDiffsByAuthor(
                projectId,
                mergeRequestIid,
                alias);

        printCommitWrappers(commits);
    }

    public void testGetMergeRequestCommitsNoDiffs(Integer projectId, Integer mergeRequestIid){
        System.out.println("\n---Testing getMergeRequestCommitsNoDiffs---");
        List<Commit> commits = gitlabService.getMergeRequestCommitsNoDiffs(projectId, mergeRequestIid);

        printCommits(commits);
    }

    public void testGetMergeRequestCommitsNoDiffsByAuthor(Integer projectId,
                                                                 Integer mergeRequestIid,
                                                                 List<String> alias){
        System.out.println("\n---Testing getMergeRequestCommitsNoDiffsByAuthor---");
        List<Commit> commits = gitlabService.getMergeRequestCommitsNoDiffsByAuthor(
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

    public void testGetFilterdCommitsWithDiffs(Integer projectId,
                                             String targetBranch,
                                             Date start,
                                             Date end){
        System.out.println("\n---Testing getFilterdCommitsWithDiffs---");
        List<CommitWrapper> commits = gitlabService.getFilterdCommitsWithDiffs(
                projectId,
                targetBranch,
                start,
                end);

        printCommitWrappers(commits);

    }

    public void testGetFilterdCommitsWithDiffsByAuthor(Integer projectId,
                                                              String targetBranch,
                                                              Date start,
                                                              Date end,
                                                              List<String> alias){
        System.out.println("\n---Testing getFilterdCommitsWithDiffsByAuthor---");
        List<CommitWrapper> commits = gitlabService.getFilterdCommitsWithDiffsByAuthor(
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


}
