package com.haumea.gitanalyzer.gitlab;

import org.gitlab4j.api.CommitsApi;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.MergeRequestApi;
import org.gitlab4j.api.models.*;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Main {
    // use this for debugging purposes
    public static void printCommits(String projectName, String hostUrl, String personalAccessToken) throws GitLabApiException {
        GitLabApi gitLabApi = new GitLabApi(hostUrl, personalAccessToken);

        List<Project> projects = gitLabApi.getProjectApi().getMemberProjects();

        Project selectedProject = null;

        for(Project cur : projects) {
            System.out.println("Project is " + cur.getName());
            if(cur.getName().equals(projectName)) {
                selectedProject = cur;

                System.out.println("name in here is " + selectedProject.getName());
            }
        }

        CommitsApi commits = new CommitsApi(gitLabApi);

        List<Commit> commitData = commits.getCommits(selectedProject);

        for (int i=0; i<commitData.size(); i++) {
            System.out.println("Commit data");
            System.out.println(commitData.get(i));

            List<Diff> newCode = commits.getDiff(selectedProject, commitData.get(i).getId());
            for (Diff code : newCode) {
                String difference = code.getDiff().trim();
                System.out.println("Diff size: " + difference.length());
                System.out.println("New code: " + code.getDiff());
            }
        }
    }

    // warning comment out the sections you do not wish to run or else it will take at least a min to run
    public static void printAllProjectData(GitlabService app, int projectNum) throws GitLabApiException {
        List<ProjectWrapper> projects = app.getProjects();

        for(ProjectWrapper currentProject : projects) {

            System.out.println(currentProject.getProjectName() + " " + currentProject.getProject().getId());
        }

        List<MemberWrapper> memberWrappers = app.getMembers(projects.get(projectNum).getProject().getId());

        System.out.println();

        for(MemberWrapper current : memberWrappers) {
            System.out.println(current.getName() + " " + current.getMemberId());
        }

        System.out.println();

        List<MergeRequest> mergeRequests = app.getAllMergeRequests(projects.get(projectNum).getProject().getId());
        for(MergeRequest current : mergeRequests) {
            System.out.println("Merge request: " + current);

            List<Commit> commitList = app.getMergeRequestCommits(projects.get(projectNum).getProject().getId(), current.getIid());

            for(Commit commit : commitList) {
                System.out.println("MR Commit: " + commit);
            }
        }

        System.out.println();
        for(CommitWrapper current : app.getAllCommits(projects.get(projectNum).getProject().getId())) {
            System.out.println("current commit: " + current.getCommitData());

        }

        testCommitFiltering(projects, projectNum, app);

        testMergeRequestFiltering(projects.get(projectNum).getProject().getId(), "aursu", app);

    }


    public static void testMergeRequestFiltering(int projectId, String memberId, GitlabService app) throws GitLabApiException {

        List<MergeRequest> memberRequests = app.getMergeRequestForMember(projectId, memberId);

        for(MergeRequest current : memberRequests) {
            System.out.println("Filtered MR: " + current);
        }

    }

    public static void testCommitFiltering(List<ProjectWrapper> projects, int projectNum, GitlabService app) throws GitLabApiException {
        Calendar calender = new GregorianCalendar(2021, Calendar.FEBRUARY, 1);


        Date start = calender.getTime();

        calender.set(2021, Calendar.MAY, 10);
        Date end = calender.getTime();


        for(CommitWrapper current : app.filterCommitsForDateAndAuthor(projects.get(projectNum).getProject().getId(), "Andrew Ursu", start, end)) {
            System.out.println("current filtered commit: " + current.getCommitData());
        }
    }

    private static double getMRDifScore(GitlabService gitlabService, int projectID, int mergeRequestIiD) throws GitLabApiException {

        MergeRequestApi mergeRequestApi = gitlabService.getMergeRequestApi();
        MergeRequest mergeRequest = mergeRequestApi.getMergeRequest(projectID, mergeRequestIiD);
        List<Commit> commits = mergeRequestApi.getCommits(projectID, mergeRequestIiD);
        CommitsApi commitsApi = gitlabService.getGitLabApi().getCommitsApi();
        double MRDifScore = 0;
        int insertions = 0;
        int deletions = 0;

        for(Commit commit : commits){

            List<Diff> newCode = commitsApi.getDiff(projectID, commit.getId());
            for (Diff code : newCode) {

                insertions = insertions + StringUtils.countOccurrencesOf(code.getDiff(), "\n+");
                deletions = deletions + StringUtils.countOccurrencesOf(code.getDiff(), "\n-");
            }

        }

        MRDifScore = insertions + deletions*0.2;
        System.out.println(insertions);
        System.out.println(deletions);
        System.out.println(MRDifScore);

        return MRDifScore;
    }

    public static void main(String[] args) throws Exception {
        GitlabService csil = new GitlabService("https://csil-git1.cs.surrey.sfu.ca/", "thDxkfQVmkRUJP9mKGsm");
        GitlabService haumeaTeamGitlabService = new GitlabService("http://cmpt373-1211-11.cmpt.sfu.ca/gitlab", "R-qyMoy2MxVPyj7Ezq_V");

        printCommits("GitLabAnalyzer","https://csil-git1.cs.surrey.sfu.ca/", "thDxkfQVmkRUJP9mKGsm");

        //printAllProjectData(csil, 5);
    }
}

