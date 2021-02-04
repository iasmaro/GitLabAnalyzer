package com.haumea.gitanalyzer.model;

import org.gitlab4j.api.CommitsApi;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.GroupApi;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Diff;
import org.gitlab4j.api.models.Project;

import java.util.ArrayList;
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

    public static void main(String[] args) throws GitLabApiException {
       Gitlab app = new Gitlab("http://142.58.22.176/", "XqHspL4ix3qXsww4ismP");

        printCommits("GitLabAnalyzer", "https://csil-git1.cs.surrey.sfu.ca/", "G9AYVYcZ54VzZRz2RTut");
    }
}

