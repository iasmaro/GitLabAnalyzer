import React, { useState, useEffect } from 'react';

import Button from 'react-bootstrap/Button';

import updateCommitScore from 'Utils/updateCommitScore';
import CommitsList from 'Components/CommitsList/CommitsList';
import CodeDifferenceList from 'Components/CodeDifferenceList/CodeDifferenceList';

import './CommitsTab.css';

const CommitsTab = (props) => {
    const { configInfo, commits: unmodifiedCommits, student, reportName, updateCommitsTotal, diffs, setDiffs } = props || {};
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
        const newScore = Math.round(10*(oldScore + scoreChange)) / 10;
        newCommits[selectedCommit].commitScore = newScore;
        let newDiffScore = 0;
        if (newCommits[selectedCommit]?.commitDiffs[diffIndex]?.scoreDTO) {
            const originalScore = newCommits[selectedCommit].commitDiffs[diffIndex].scoreDTO.score;
            const modifiedScore = newCommits[selectedCommit].commitDiffs[diffIndex].scoreDTO.modifiedScore;
            const oldScore = modifiedScore !== -1 ? modifiedScore : originalScore;
            newDiffScore = oldScore + scoreChange;
            newCommits[selectedCommit].commitDiffs[diffIndex].scoreDTO.modifiedScore = newDiffScore;
        }
        setCommits(newCommits);
        updateCommitsTotal(scoreChange);
        updateCommitScore(selectedCommit, diffIndex, student, newDiffScore, reportName);
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
                <Button variant="dark" className="expand-button" onClick={handleExpand}>{expand ? '>' : '<'}</Button>
                <div className={`commits-code-diffs ${expand ? 'expanded' : ''}`}>
                {diffs && <CodeDifferenceList changeCommitScore={changeCommitScore} diffs={diffs} diffsTitle={diffsTitle} configInfo={configInfo}/>}
                </div>
            </div>}
        </div>
    );
}

export default CommitsTab;