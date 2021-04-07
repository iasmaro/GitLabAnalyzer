import React, { useState } from 'react';

import MergeRequestList from 'Components/MergeRequestList/MergeRequestList';
import CommitsList from 'Components/CommitsList/CommitsList';
import CodeDifferenceList from 'Components/CodeDifferenceList/CodeDifferenceList';

import './MergeRequestTab.css'

const MergeRequestTab = (props) => {
    const { configInfo } = props || {};
    const [commits, setCommits] = useState();
    const [diffs, setDiffs] = useState();

    const setCommit = (commitList) => {
        setCommits(commitList);
    }

    const setCodeDiffs = (diffsList) => {
        setDiffs(diffsList);
    }

    return (
        <div className="merge-request-tab">
            <div className="mrs-left">
                <div className="mrs-top">
                    <MergeRequestList {...props} setCommit={setCommit} setCodeDiffs={setCodeDiffs} />
                </div>
                <div className="mrs-bottom">
                    {commits && <CommitsList commits={commits} setCodeDiffs={setCodeDiffs} />}
                </div>
            </div>
            <div className="mrs-right">
               {diffs && <CodeDifferenceList diffs={diffs} configInfo={configInfo} />}
            </div>
        </div>
    );
}

export default MergeRequestTab;