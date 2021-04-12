import React, { useEffect, useState } from 'react';
import { Col, Row, Tabs, Tab } from 'react-bootstrap';

import { TABS } from 'Constants/constants';
import MergeRequestTab from 'Components/MergeRequestTab/MergeRequestTab';
import SummaryTab from 'Components/SummaryTab/SummaryTab';
import CommitsTab from 'Components/CommitsTab/CommitsTab';
import CommentsTab from 'Components/CommentsTab/CommentsTab';
import Scores from 'Components/Scores/Scores';

import calculateCommitScore from './utils/calculateCommitScore';
import calculateMrScore from './utils/calculateMrScore';
import './AnalyzerTabs.css';

const AnalyzerTabs = (props) => {
    const [key, setKey] = useState('summary');

    const [commitScore, setCommitScore] = useState();
    const [mrScore, setMrScore] = useState();

    const { commits, mergerequests, issueComments, mergeRequestComments } = props || {};

    useEffect(() => {
        const sumOfCommits = calculateCommitScore(commits);
        setCommitScore(sumOfCommits);
    }, [commits]);

    useEffect(() => {
        const sumOfMRs = calculateMrScore(mergerequests);
        setMrScore(sumOfMRs);
    }, [mergerequests]);

    const updateCommitScore = (change) => {
        setCommitScore(Math.round((commitScore + change) * 10) / 10);
    }
    
    const updateMrScore = (change) => {
        setMrScore(Math.round((mrScore + change) * 10) / 10);
    }

    const numOfCommits = commits?.length || 0;
    const numOfMRs = mergerequests?.length || 0;

    const changeTab = (k) => {
        setKey(k);
    }
    

    return (
        <Tab.Container>
            <Row className="tab-container" >
                <Col>
                    <Tabs activeKey={key} onSelect={(k) => changeTab(k)} data-testid="tabs" >
                        <Tab eventKey={"summary"} title={TABS.SUMMARY}>
                            <Scores commitsScore={commitScore} mrsScore={mrScore} totalCommits={numOfCommits} totalMRs={numOfMRs} />
                            <SummaryTab {...props} />
                        </Tab>
                        <Tab eventKey={"merge-requests"} title={TABS.MERGE_REQUESTS} data-testid="merge-request-tab">
                            <Scores commitsScore={commitScore} mrsScore={mrScore} totalCommits={numOfCommits} totalMRs={numOfMRs} />
                            <MergeRequestTab {...props} updateMRsTotal={updateMrScore} updateCommitsTotal={updateCommitScore} />
                        </Tab>
                        <Tab eventKey={"commits"} title={TABS.COMMITS} data-testid="commits-tab">
                            <Scores commitsScore={commitScore} mrsScore={mrScore} totalCommits={numOfCommits} totalMRs={numOfMRs} />
                            <CommitsTab {...props} updateCommitsTotal={updateCommitScore} />
                        </Tab>
                        <Tab eventKey={"comments"} title={TABS.COMMENTS}>
                            <CommentsTab issueComments={issueComments} mergeRequestComments={mergeRequestComments} />
                        </Tab>
                    </Tabs>
                </Col>
            </Row>
        </Tab.Container>
    );
}

export default AnalyzerTabs 
