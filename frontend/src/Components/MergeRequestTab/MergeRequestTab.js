import React, { useState } from 'react';

import Button from 'react-bootstrap/Button';

import MergeRequestList from 'Components/MergeRequestList/MergeRequestList';
import CommitsList from 'Components/CommitsList/CommitsList';
import CodeDifferenceList from 'Components/CodeDifferenceList/CodeDifferenceList';

import './MergeRequestTab.css';

const MergeRequestTab = (props) => {
    const { configInfo } = props || {};
    const [commits, setCommits] = useState();
    const [diffs, setDiffs] = useState();
    const [expand, setExpand] = useState(false);

    const setCommit = (commitList) => {
        setCommits(commitList);
    }

    const setCodeDiffs = (diffsList) => {
        setDiffs(diffsList);
    }

    const handleExpand = () => {
        setExpand(!expand);
    }

    return (
        <div className="merge-request-tab">
            {!expand && <div className="mrs-left">
                <div className="mrs-top">
                    <MergeRequestList {...props} setCommit={setCommit} setCodeDiffs={setCodeDiffs} />
                </div>
                <div className="mrs-bottom">
                    {commits && <CommitsList {...props} commits={commits} setCodeDiffs={setCodeDiffs} />}
                </div>
            </div>}
            {diffs && <div className="mrs-right">
                <Button className="expand-button" onClick={handleExpand}>{expand ? '>' : '<'}</Button>
                <div className={`mr-code-diffs ${expand ? 'expanded' : ''}`}>
                    <CodeDifferenceList diffs={diffs} configInfo={configInfo} />
                </div>
            </div>}
        </div>
    );
}

export default MergeRequestTab;