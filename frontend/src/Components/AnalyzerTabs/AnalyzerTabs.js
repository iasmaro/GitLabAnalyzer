import React, { useState } from 'react';
import { Col, Row, Tabs, Tab } from 'react-bootstrap';

import { TABS } from 'Constants/constants';
import MergeRequestTab from 'Components/MergeRequestTab/MergeRequestTab';
import CommitsTab from 'Components/CommitsTab/CommitsTab';

import './AnalyzerTabs.css';

const AnalyzerTabs = (props) => {
    const [key, setKey] = useState('merge-requests');

    const changeTab = (k) => {
        setKey(k);
    }

    return (
        <Tab.Container>
            <Row className="tab-container" >
                <Col>
                    <Tabs activeKey={key} onSelect={(k) => changeTab(k)} >
                        <Tab eventKey={"merge-requests"} title={TABS.MERGE_REQUESTS}>
                            <MergeRequestTab {...props} />
                        </Tab>
                        <Tab eventKey={"commits"} title={TABS.COMMITS}>
                            <CommitsTab {...props} />
                        </Tab>
                    </Tabs>
                </Col>
            </Row>
        </Tab.Container>
    );
}

export default AnalyzerTabs 
