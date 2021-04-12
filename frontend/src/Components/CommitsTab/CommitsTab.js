import React, { useState } from 'react';

import Button from 'react-bootstrap/Button';

import CommitsList from 'Components/CommitsList/CommitsList';
import CodeDifferenceList from 'Components/CodeDifferenceList/CodeDifferenceList';

import './CommitsTab.css';

const CommitsTab = (props) => {
    const { configInfo, diffs, setDiffs } = props || {};
    const [expand, setExpand] = useState(false);
    const [diffsTitle, setDiffsTitle] = useState();

    const setCodeDiffs = (diffsList) => {
        setDiffs(diffsList);
    }

    const handleExpand = () => {
        setExpand(!expand);
    }

    return (
        <div className="commits-tab">
            {!expand && <div className="commits-left">
                <CommitsList {...props} setCodeDiffs={setCodeDiffs} setDiffsTitle={setDiffsTitle}/>
            </div>}
            {diffs && <div className="commits-right">
                <Button className="expand-button" onClick={handleExpand}>{expand ? '>' : '<'}</Button>
                <div className={`commits-code-diffs ${expand ? 'expanded' : ''}`}>
                {diffs && <CodeDifferenceList diffs={diffs} diffsTitle={diffsTitle} configInfo={configInfo}/>}
                </div>
            </div>}
        </div>
    );
}

export default CommitsTab;