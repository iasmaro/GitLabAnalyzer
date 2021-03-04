package com.haumea.gitanalyzer.gitlab;

import org.gitlab4j.api.CommitsApi;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

//        for(MemberWrapper current : memberWrappers) {
//            System.out.println("Author is: " + current.getName() + " " + current.getMemberId());
//
//
//            newMRFilterTest(projects.get(projectNum).getProject().getId(), current.getName(), app);
//
//            testCommitFiltering(projects, projectNum, app);
//        }


//        System.out.println();
//
//        List<MergeRequest> mergeRequests = app.getAllMergeRequestData(projects.get(projectNum).getProject().getId());
//        for(MergeRequest current : mergeRequests) {
//            System.out.println("Merge request: " + current);
//
//            List<CommitWrapper> commitList = app.getMergeRequestCommits(projects.get(projectNum).getProject().getId(), current.getIid());
//
//            for(CommitWrapper commit : commitList) {
//                System.out.println("MR Commit: " + commit.getCommitData());
//            }
//        }
//
//        List<MergeRequestWrapper> mergeRequestWrappers = app.getAllMergeRequests(projects.get(projectNum).getProject().getId());
//        for(MergeRequestWrapper current : mergeRequestWrappers) {
//            System.out.println("Merge request: " + current.getMergeRequestData());
//
//            System.out.println();
//
//            System.out.println("Size of diff list is: " + current.getMergeRequestVersion().size());
//
//            for(MergeRequestDiff change : current.getMergeRequestChanges()) {
//
//                System.out.println("change is: " + change.getDiffs());
//
//            }
//
//            List<CommitWrapper> commitList = app.getMergeRequestCommits(projects.get(projectNum).getProject().getId(), current.getMergeRequestData().getIid());
//
//            for(CommitWrapper commit : commitList) {
//                System.out.println("MR Commit: " + commit.getCommitData());
//            }
//        }

        List<CommentType> commentTypes = new ArrayList<>();
        System.out.println();
        CommentType singleJavaComment = new CommentType("//", "");

        commentTypes.add(singleJavaComment);

        for(CommitWrapper current : app.getAllCommits(projects.get(projectNum).getProject().getId())) {
            System.out.println("current commit: " + current.getCommitData());
            for(Diff currentDiff : current.getNewCode()) {
                System.out.println("  current commit diff: " + currentDiff.getDiff());
                IndividualDiffScoreCalculator calculator = new IndividualDiffScoreCalculator();


                calculator.calculateDiffScore(currentDiff.getDiff(), currentDiff.getDeletedFile(), 1.0, 0.2, 0.2, commentTypes);

                System.out.println("file path is: " + currentDiff.getNewPath());

            }

        }

//        testCommitFiltering(projects, projectNum, app);
//
//        testMergeRequestFiltering(projects.get(projectNum).getProject().getId(), "aursu", app);


    }



    public static void testMergeRequestFiltering(int projectId, String memberId, GitlabService app) throws GitLabApiException {

        List<MergeRequestWrapper> memberRequests = app.getMergeRequestForMember(projectId, memberId);

        for(MergeRequestWrapper current : memberRequests) {
            System.out.println("Filtered MR: " + current.getMergeRequestData());
        }

    }

    public static void testCommitFiltering(List<ProjectWrapper> projects, int projectNum, GitlabService app) throws GitLabApiException {
        Calendar calender = new GregorianCalendar(2021, Calendar.FEBRUARY, 15);
        Date start = calender.getTime();

        calender.set(2021, Calendar.MAY, 10);
        Date end = calender.getTime();


        for(CommitWrapper current : app.filterCommitsForDateAndAuthor(projects.get(projectNum).getProject().getId(), "Andrew Ursu", start, end)) {
            System.out.println("current filtered commit: " + current.getCommitData());
        }
    }

    public static void newMRFilterTest(int projectId, String name, GitlabService app) throws GitLabApiException {
        Calendar calender = new GregorianCalendar(2021, Calendar.FEBRUARY, 14);
        TimeZone utc = TimeZone.getTimeZone("UTC");
        calender.setTimeZone(utc);

        Date start = calender.getTime();

        calender.set(2021, Calendar.FEBRUARY, 15);
        calender.setTimeZone(utc);
        Date end = calender.getTime();



        List<MergeRequestWrapper> mergeRequestWrappers = app.filterMergeRequestByDate(projectId, name, start, end);

        for(MergeRequestWrapper current : mergeRequestWrappers) {
            System.out.println("data is " + current.getMergeRequestData());
        }

    }


    public static void main(String[] args) throws GitLabApiException {
        GitlabService csil = new GitlabService("https://csil-git1.cs.surrey.sfu.ca/", "gYLtys_E24PNBWmG_i86");
//        GitlabService haumeaTeamGitlabService = new GitlabService("http://cmpt373-1211-11.cmpt.sfu.ca/gitlab", "R-qyMoy2MxVPyj7Ezq_V");


        printAllProjectData(csil, 0);
//        printCommits("GitLabAnalyzer", "https://csil-git1.cs.surrey.sfu.ca/", "gYLtys_E24PNBWmG_i86");
    }
}

