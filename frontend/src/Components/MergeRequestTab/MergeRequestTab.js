import React, { useEffect, useState } from 'react';

import Button from 'react-bootstrap/Button';

import updateMRScore from 'Utils/updateMRScore';
import updateMrCommitScore from 'Utils/updateMrCommitScore';
import MergeRequestList from 'Components/MergeRequestList/MergeRequestList';
import CommitsList from 'Components/CommitsList/CommitsList';
import CodeDifferenceList from 'Components/CodeDifferenceList/CodeDifferenceList';

import './MergeRequestTab.css';

const MergeRequestTab = (props) => {
    const { configInfo, mergerequests: MRs, updateCommitsTotal, updateMRsTotal, student, reportName, diffs, setDiffs, activeCommits, setActiveCommits } = props || {};
    const [mergerequests, setMergeRequests] = useState();
    const [expand, setExpand] = useState(false);
    const [diffsTitle, setDiffsTitle] = useState();
    const [selected, setSelected] = useState();
    const [selectedMR, setSelectedMR] = useState();
    const [selectedCommit, setSelectedCommit] = useState();

    useEffect(()=> {
        setMergeRequests(MRs);
    }, [MRs]);

    const setCommit = (commitList) => {
        setActiveCommits(commitList);
    }

    const setCodeDiffs = (diffsList, mergeRequestLink) => {
        for (let i = 0; mergerequests.length; i++) {
            if (mergerequests[i].mergeRequestLink === mergeRequestLink) {
                setSelectedMR(i);
                break;
            }
        }
        setDiffs(diffsList);
        setSelected('MR');
    }

    const setCommitCodeDiffs = (diffsList, commitLink) => {
        for (let i = 0; activeCommits.length; i++) {
            if (activeCommits[i].commitLink === commitLink) {
                setSelectedCommit(i);
                break;
            }
        }
        setDiffs(diffsList);
        setSelected('commit');
    }

    const handleExpand = () => {
        setExpand(!expand);
    }

    const changeMRScore = (scoreChange, diffIndex) => {
        if (selected === 'commit') {
            const newCommits = activeCommits.slice();
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
            setActiveCommits(newCommits);
            updateCommitsTotal(scoreChange);
            const newMRs = mergerequests.slice();
            newMRs[selectedMR].commitDTOList = newCommits;
            newMRs[selectedMR].sumOfCommitScore += scoreChange;
            setMergeRequests(newMRs);
            updateMrCommitScore(selectedMR, selectedCommit, diffIndex, student, newDiffScore, reportName);
        } else {
            const newMRs = mergerequests.slice();
            const oldScore =  parseFloat(newMRs[selectedMR].mrscore);
            const newScore = Math.round(10*(oldScore + scoreChange)) / 10;
            newMRs[selectedMR].mrscore = newScore;
            let newDiffScore = 0;
            if (newMRs[selectedMR].mergeRequestDiffs[diffIndex]?.scoreDTO) {
                const originalScore = newMRs[selectedMR].mergeRequestDiffs[diffIndex].scoreDTO.score;
                const modifiedScore = newMRs[selectedMR].mergeRequestDiffs[diffIndex].scoreDTO.modifiedScore;
                const oldScore = modifiedScore !== -1 ? modifiedScore : originalScore;
                newDiffScore = oldScore + scoreChange;
                newMRs[selectedMR].mergeRequestDiffs[diffIndex].scoreDTO.modifiedScore = newDiffScore;
            }
            updateMRsTotal(scoreChange);
            setMergeRequests(newMRs);
            updateMRScore(selectedMR, diffIndex, student, newDiffScore, reportName);
        }
    }

    return (
        <div className="merge-request-tab">
            {!expand && <div className="mrs-left">
                <div className="mrs-top">
                    <MergeRequestList {...props} mergerequests={mergerequests} setCommit={setCommit} setCodeDiffs={setCodeDiffs} setDiffsTitle={setDiffsTitle} />
                </div>
                <div className="mrs-bottom">
                    {activeCommits && <CommitsList {...props} commits={activeCommits} setCodeDiffs={setCommitCodeDiffs} setDiffsTitle={setDiffsTitle}/>}
                </div>
            </div>}
            {diffs && <div className="mrs-right">
                <Button variant="dark" className="expand-button" onClick={handleExpand}>{expand ? '>' : '<'}</Button>
                <div className={`mr-code-diffs ${expand ? 'expanded' : ''}`}>
                    <CodeDifferenceList diffs={diffs} diffsTitle={diffsTitle} changeMRScore={changeMRScore} configInfo={configInfo}/>
                </div>
            </div>}
        </div>
    );
}

export default MergeRequestTab;