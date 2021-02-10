package com.haumea.gitanalyzer.gitlab;

import org.gitlab4j.api.CommitsApi;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Diff;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.Project;

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
    public static void printAllFunctions(Gitlab app, int projectNum) throws GitLabApiException {
        List<ProjectWrapper> projects = app.getProjects();

        for(ProjectWrapper c : projects) {

            System.out.println(c.getProjectName());
        }

        List<MemberWrapper> memberWrappers = app.getMembers(projects.get(projectNum).getProject().getId());

        System.out.println();

        for(MemberWrapper current : memberWrappers) {
            System.out.println(current.getName());
        }

        System.out.println();

        List<MergeRequest> mergeRequests = app.getMergeRequests(projects.get(projectNum).getProject().getId());

        for(MergeRequest current : mergeRequests) {
            System.out.println("Merge request: " + current);

            List<Commit> commitList = app.getMergeRequestCommits(projects.get(projectNum).getProject().getId(), current.getIid());

            for(Commit com : commitList) {
                System.out.println("Commit: " + com);
            }
        }

        System.out.println();
        for(Commit current : app.getAllCommits(projects.get(projectNum).getProject().getId())) {
           System.out.println("current commit: " + current);
        }

    }


    public static void main(String[] args) throws GitLabApiException {
        Gitlab app = new Gitlab("https://csil-git1.cs.surrey.sfu.ca/", "gYLtys_E24PNBWmG_i86");
        Gitlab app2 = new Gitlab("http://cmpt373-1211-11.cmpt.sfu.ca/gitlab", "R-qyMoy2MxVPyj7Ezq_V");

//       printCommits("tester", "http://cmpt373-1211-11.cmpt.sfu.ca/gitlab", "R-qyMoy2MxVPyj7Ezq_V");
//       printCommits("tester", "https://csil-git1.cs.surrey.sfu.ca/", "gYLtys_E24PNBWmG_i86");


        printAllFunctions(app2, 0);
    }
}

