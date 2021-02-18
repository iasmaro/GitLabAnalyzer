import React, { useState } from 'react';
import { Col, Row, Tabs, Tab } from 'react-bootstrap';
import { TABS } from 'Constants/constants';
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
                        <Tab eventKey={"scores"} title={TABS.SCORES} >
                            {/* Add Link to the tab it belongs to 
                                <LINK />
                            */}
                            {TABS.SCORES}
                        </Tab>
                        <Tab eventKey={"graphs"} title={TABS.GRAPHS}>
                            {/* Add Link to the tab it belongs to 
                                <LINK />
                            */}
                            {TABS.GRAPHS}
                        </Tab>
                        <Tab eventKey={"merge-requests"} title={TABS.MERGE_REQUESTS}>
                            {/* Add Link to the tab it belongs to 
                                <LINK />
                            */}
                            {TABS.MERGE_REQUESTS}
                        </Tab>
                        <Tab eventKey={"commits"} title={TABS.COMMITS}>
                            {/* Add Link to the tab it belongs to 
                                <LINK />
                            */}
                            {TABS.COMMITS}
                        </Tab>
                    </Tabs>
                </Col>
            </Row>
        </Tab.Container>
    );
}

export default AnalyzerTabs 
