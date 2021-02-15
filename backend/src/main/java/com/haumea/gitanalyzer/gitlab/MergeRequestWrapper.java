package com.haumea.gitanalyzer.gitlab;

import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.MergeRequestApi;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.MergeRequestDiff;

import java.util.ArrayList;
import java.util.List;

public class MergeRequestWrapper {
    private MergeRequest mergeRequestData;

    private List<MergeRequestDiff> mergeRequestVersion; // used to create mergeRequestChanges in calling code
    private List<MergeRequestDiff> mergeRequestChanges;


    public MergeRequest getMergeRequestData() {
        return mergeRequestData;
    }
    public List<MergeRequestDiff> getMergeRequestChanges() {
        return mergeRequestChanges;
    }

    public List<MergeRequestDiff> getMergeRequestVersion() {
        return mergeRequestVersion;
    }

    public void addMergeRequestChange(MergeRequestDiff newDiff) {
        mergeRequestChanges.add(newDiff);
    }

    public MergeRequestWrapper(MergeRequestApi mergeRequestApi, int projectId, MergeRequest mergeRequestData) throws GitLabApiException {
        this.mergeRequestData = mergeRequestData;
        this.mergeRequestVersion = mergeRequestApi.getMergeRequestDiffs(projectId, this.mergeRequestData.getIid());

        mergeRequestChanges = new ArrayList<>();
    }
}
