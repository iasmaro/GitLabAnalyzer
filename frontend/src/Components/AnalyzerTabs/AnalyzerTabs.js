import React, { useState } from 'react';
import { Col, Row, Tabs, Tab } from 'react-bootstrap';

import { TABS } from 'Constants/constants';
import MergeRequestTab from 'Components/MergeRequestTab/MergeRequestTab';
import CommitsTab from 'Components/CommitsTab/CommitsTab';
import Scores from 'Components/Scores/Scores';

import calculateCommitScore from './utils/calculateCommitScore';
import calculateMrScore from './utils/calculateMrScore';
import './AnalyzerTabs.css';

const AnalyzerTabs = (props) => {
    const [key, setKey] = useState('merge-requests');

    const { commits, mergerequests, configInfo } = props || {};
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
                    <Tabs activeKey={key} onSelect={(k) => changeTab(k)} >
                        <Tab eventKey={"merge-requests"} title={TABS.MERGE_REQUESTS}>
                            <Scores commitsScore={sumOfCommits} mrsScore={sumOfMRs} totalCommits={numOfCommits} totalMRs={numOfMRs} />
                            <MergeRequestTab {...props} configInfo={configInfo} />
                        </Tab>
                        <Tab eventKey={"commits"} title={TABS.COMMITS}>
                            <Scores commitsScore={sumOfCommits} mrsScore={sumOfMRs} totalCommits={numOfCommits} totalMRs={numOfMRs} />
                            <CommitsTab {...props} configInfo={configInfo} />
                        </Tab>
                    </Tabs>
                </Col>
            </Row>
        </Tab.Container>
    );
}

export default AnalyzerTabs 
