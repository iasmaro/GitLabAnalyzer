import React, {useState} from 'react';
import Tabs from 'react-bootstrap/Tabs';
import Tab from 'react-bootstrap/Tab';
import {Col, Row} from 'react-bootstrap';
import './AnalyzerTabs.css';

function AnalyzerTabs() {
    const [key, setKey] = useState('home');

    return (
        <Tab.Container fluid>
            <Row className="justify-content-md-center">
                <Col>
                    <Tabs
                        id="controlled-tab-example"
                        activeKey={key}
                        onSelect={(k) => setKey(k)}
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
                        <Tab eventKey={"merge-request"} title="Merge Request">
                            {/* Add Link to the tab it belongs to 
                                <LINK />
                            */}
                            Merge Request
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

export default AnalyzerTabs 
