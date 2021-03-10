package com.haumea.gitanalyzer.gitlab;
import com.haumea.gitanalyzer.exception.GitLabRuntimeException;
import org.gitlab4j.api.CommitsApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Diff;

import java.util.List;
/*
Wrapper class designed to encapsulate the commit code from the gitlab library

Needed as the commit data and code differences are separated into 2 different objects

 */

public class CommitWrapper {
    private final Commit commitData;
    private final List<Diff> codeChanges;

    public List<Diff> getNewCode() {
        return codeChanges;
    }

    public Commit getCommitData() {
        return commitData;
    }

    // need to create commitData list in calling code and create the student and commit wrapper objects from that list
    public CommitWrapper(int projectId, CommitsApi commitsApi, Commit commitData) throws GitLabRuntimeException {

        this.commitData = commitData;
        try{
            this.codeChanges = commitsApi.getDiff(projectId, commitData.getId());
        } catch (GitLabApiException e){
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }

    }
}

