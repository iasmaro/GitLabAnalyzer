import React, { useState } from 'react';
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

    const { commits, mergerequests, issueComments, mergeRequestComments } = props || {};
    const numOfCommits = commits?.length || 0;
    const numOfMRs = mergerequests?.length || 0;
    const sumOfCommits = calculateCommitScore(commits);
    const sumOfMRs = calculateMrScore(mergerequests);

    const changeTab = (k) => {
        setKey(k);
    }
    

    return (
        <Tab.Container>
            <Row className="tab-container" >
                <Col>
                    <Tabs activeKey={key} onSelect={(k) => changeTab(k)} data-testid="tabs" >
                        <Tab eventKey={"summary"} title={TABS.SUMMARY}>
                            <Scores commitsScore={sumOfCommits} mrsScore={sumOfMRs} totalCommits={numOfCommits} totalMRs={numOfMRs} />
                            <SummaryTab {...props} />
                        </Tab>
                        <Tab eventKey={"merge-requests"} title={TABS.MERGE_REQUESTS} data-testid="merge-request-tab">
                            <Scores commitsScore={sumOfCommits} mrsScore={sumOfMRs} totalCommits={numOfCommits} totalMRs={numOfMRs} />
                            <MergeRequestTab {...props} />
                        </Tab>
                        <Tab eventKey={"commits"} title={TABS.COMMITS} data-testid="commits-tab">
                            <Scores commitsScore={sumOfCommits} mrsScore={sumOfMRs} totalCommits={numOfCommits} totalMRs={numOfMRs} />
                            <CommitsTab {...props} />
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
