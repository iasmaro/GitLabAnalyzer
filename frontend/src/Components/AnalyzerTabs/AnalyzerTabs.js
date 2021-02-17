import React, {useState} from 'react';
import Tabs from 'react-bootstrap/Tabs';
import Tab from 'react-bootstrap/Tab';
import {Col, Row} from 'react-bootstrap';
import './AnalyzerTabs.css';

const AnalyzerTabs = () => {
    const [key, setKey] = useState('home');

    const changeTab = (k) => {
        setKey(k);
    }

    return (
        <Tab.Container fluid>
            <Row className="justify-content-md-center">
                <Col>
                    <Tabs
                        id="controlled-tab-example"
                        activeKey={key}
                        onSelect={(k) => changeTab(k)}
                    >
                        <Tab eventKey={"scores"} title="Scores" >
                            {/* Add Link to the tab it belongs to 
                                <LINK />
                            */}
                            Scores
                        </Tab>
                        <Tab eventKey={"graphs"} title="Graphs">
                            {/* Add Link to the tab it belongs to 
                                <LINK />
                            */}
                            Graphs
                        </Tab>
                        <Tab eventKey={"merge-requests"} title="Merge Requests">
                            {/* Add Link to the tab it belongs to 
                                <LINK />
                            */}
                            Merge Requests
                        </Tab>
                        <Tab eventKey={"commits"} title="Commits">
                            {/* Add Link to the tab it belongs to 
                                <LINK />
                            */}
                            Commits
                        </Tab>
                    </Tabs>
                </Col>
            </Row>
        </Tab.Container>
    );
}

export const TABS = {
    SCORES: 'scores',
    GRAPHS: 'graphs',
    MERGE_REQUESTS: 'merge-requests',
    COMMITS: 'commits'
}

export default AnalyzerTabs 
