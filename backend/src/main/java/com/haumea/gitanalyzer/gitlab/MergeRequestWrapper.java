package com.haumea.gitanalyzer.gitlab;

import com.haumea.gitanalyzer.exception.GitLabRuntimeException;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.MergeRequestApi;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.MergeRequestDiff;

public class MergeRequestWrapper {
    private MergeRequest mergeRequestData;
    private MergeRequestDiff mergeRequestDiff;

    public MergeRequestWrapper(MergeRequestApi mergeRequestApi, int projectId, MergeRequest mergeRequestData) {
        this.mergeRequestData = mergeRequestData;
        try{
            // pre-condition: the latest version is at index 0
            Integer latestVersion = mergeRequestApi.getMergeRequestDiffs(projectId, this.mergeRequestData.getIid()).get(0).getId();
            this.mergeRequestDiff = mergeRequestApi.getMergeRequestDiff(projectId, this.mergeRequestData.getIid(), latestVersion);
        } catch (GitLabApiException e){
            throw new GitLabRuntimeException(e.getLocalizedMessage());
        }
    }

    public MergeRequest getMergeRequestData() {
        return mergeRequestData;
    }
    public MergeRequestDiff getMergeRequestDiff() { return mergeRequestDiff; }

}
