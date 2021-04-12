import React, { useState, useEffect } from 'react';

import Button from 'react-bootstrap/Button';

import updateCommitScore from 'Utils/updateCommitScore';
import CommitsList from 'Components/CommitsList/CommitsList';
import CodeDifferenceList from 'Components/CodeDifferenceList/CodeDifferenceList';

import './CommitsTab.css';

const CommitsTab = (props) => {
    const { configInfo, commits: unmodifiedCommits, student, reportName, updateCommitsTotal } = props || {};
    const [diffs, setDiffs] = useState();
    const [commits, setCommits] = useState();
    const [selectedCommit, setSelectedCommit] = useState();
    const [expand, setExpand] = useState(false);
    const [diffsTitle, setDiffsTitle] = useState();

    useEffect(() => {
        if(unmodifiedCommits) {
            setCommits(unmodifiedCommits);
        }
    }, [unmodifiedCommits]);

    const setCodeDiffs = (diffsList, commitLink) => {
        for (let i = 0; commits.length; i++) {
            if (commits[i].commitLink === commitLink) {
                setSelectedCommit(i);
                break;
            }
        }
        setDiffs(diffsList);
    }

    const changeCommitScore = (scoreChange, diffIndex) => {
        const newCommits = commits.slice();
        const oldScore =  parseFloat(newCommits[selectedCommit].commitScore);
        const newScore = (oldScore + scoreChange + 0).toFixed(1);
        newCommits[selectedCommit].commitScore = newScore;
        setCommits(newCommits);
        updateCommitsTotal(scoreChange);
        updateCommitScore(selectedCommit, diffIndex, student, newScore, reportName);
    }

    const handleExpand = () => {
        setExpand(!expand);
    }

    return (
        <div className="commits-tab">
            {!expand && <div className="commits-left">
                <CommitsList {...props} commits={commits} setCodeDiffs={setCodeDiffs} setDiffsTitle={setDiffsTitle}/>
            </div>}
            {diffs && <div className="commits-right">
                <Button className="expand-button" onClick={handleExpand}>{expand ? '>' : '<'}</Button>
                <div className={`commits-code-diffs ${expand ? 'expanded' : ''}`}>
                {diffs && <CodeDifferenceList changeCommitScore={changeCommitScore} diffs={diffs} diffsTitle={diffsTitle} configInfo={configInfo}/>}
                </div>
            </div>}
        </div>
    );
}

export default CommitsTab;