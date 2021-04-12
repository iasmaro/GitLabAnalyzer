import React, { useEffect, useState } from 'react';

import Button from 'react-bootstrap/Button';

import updateMRScore from 'Utils/updateMRScore';
import updateMrCommitScore from 'Utils/updateMrCommitScore';
import MergeRequestList from 'Components/MergeRequestList/MergeRequestList';
import CommitsList from 'Components/CommitsList/CommitsList';
import CodeDifferenceList from 'Components/CodeDifferenceList/CodeDifferenceList';

import './MergeRequestTab.css';

const MergeRequestTab = (props) => {
    const { configInfo, mergerequests: MRs, updateCommitsTotal, updateMRsTotal, student, reportName } = props || {};
    const [mergerequests, setMergeRequests] = useState();
    const [commits, setCommits] = useState();
    const [diffs, setDiffs] = useState();
    const [expand, setExpand] = useState(false);
    const [diffsTitle, setDiffsTitle] = useState();
    const [selected, setSelected] = useState();
    const [selectedMR, setSelectedMR] = useState();
    const [selectedCommit, setSelectedCommit] = useState();

    useEffect(()=> {
        setMergeRequests(MRs);
    }, [MRs]);

    const setCommit = (commitList) => {
        setCommits(commitList);
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
        for (let i = 0; commits.length; i++) {
            if (commits[i].commitLink === commitLink) {
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
            const newCommits = commits.slice();
            const oldScore =  parseFloat(newCommits[selectedCommit].commitScore);
            const newScore = (oldScore + scoreChange + 0).toFixed(1);
            newCommits[selectedCommit].commitScore = newScore;
            if (newCommits[selectedCommit]?.commitDiffs[diffIndex]?.scoreDTO) {
                const oldScore = newCommits[selectedCommit].commitDiffs[diffIndex].scoreDTO.score;
                newCommits[selectedCommit].commitDiffs[diffIndex].scoreDTO.modifiedScore = oldScore + scoreChange;
            }
            setCommits(newCommits);
            updateCommitsTotal(scoreChange);
            const newMRs = mergerequests.slice();
            newMRs[selectedMR].commitDTOList = newCommits;
            newMRs[selectedMR].sumOfCommitScore += scoreChange;
            setMergeRequests(newMRs);
            updateMrCommitScore(selectedMR, selectedCommit, diffIndex, student, newScore, reportName);
        } else {
            const newMRs = mergerequests.slice();
            const oldScore =  parseFloat(newMRs[selectedMR].mrscore);
            const newScore = Math.round(10*(oldScore + scoreChange)) / 10;
            newMRs[selectedMR].mrscore = newScore;
            let newDiffScore = 0;
            if (newMRs[selectedMR].mergeRequestDiffs[diffIndex]?.scoreDTO) {
                const oldScore = newMRs[selectedMR].mergeRequestDiffs[diffIndex].scoreDTO.score;
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
                    {commits && <CommitsList {...props} commits={commits} setCodeDiffs={setCommitCodeDiffs} setDiffsTitle={setDiffsTitle}/>}
                </div>
            </div>}
            {diffs && <div className="mrs-right">
                <Button className="expand-button" onClick={handleExpand}>{expand ? '>' : '<'}</Button>
                <div className={`mr-code-diffs ${expand ? 'expanded' : ''}`}>
                    <CodeDifferenceList diffs={diffs} diffsTitle={diffsTitle} changeMRScore={changeMRScore} configInfo={configInfo}/>
                </div>
            </div>}
        </div>
    );
}

export default MergeRequestTab;