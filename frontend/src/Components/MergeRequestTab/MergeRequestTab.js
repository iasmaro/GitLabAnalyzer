import React, { useState } from 'react';
import Spinner from 'react-bootstrap/Spinner';

import MergeRequestList from 'Components/MergeRequestList/MergeRequestList';
import CommitsList from 'Components/CommitsList/CommitsList';
import CodeDifferenceList from 'Components/CodeDifferenceList/CodeDifferenceList';

import './MergeRequestTab.css'

const MergeRequestTab = (props) => {
    const [isLoading, setIsLoading] = useState(false);
    const [commits, setCommits] = useState();
    const [diffs, setDiffs] = useState();

    const setCommit = (commitList) => {
        setIsLoading(true);
        commitList.then((data) => {
            setCommits(data);
            setIsLoading(false);
        });
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
                    {isLoading && <Spinner animation="border" className="bottom-spinner" />}
                    {commits && !isLoading && <CommitsList commits={commits} setCodeDiffs={setCodeDiffs} />}
                </div>
            </div>
            <div className="mrs-right">
               {diffs && <CodeDifferenceList diffs={diffs} />}
            </div>
        </div>
    );
}

export default MergeRequestTab;