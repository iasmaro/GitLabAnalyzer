import React, { useState } from 'react';
import { Col, Row, Tabs, Tab } from 'react-bootstrap';

import { TABS } from 'Constants/constants';
import MergeRequestList from 'Components/MergeRequestList/MergeRequestList';
import CommitsList from 'Components/CommitsList/CommitsList';

import './AnalyzerTabs.css';

const AnalyzerTabs = (props) => {
    const [key, setKey] = useState('merge-requests');

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
                        <Tab eventKey={"merge-requests"} title={TABS.MERGE_REQUESTS}>
                            <MergeRequestList {...props} />
                        </Tab>
                        <Tab eventKey={"commits"} title={TABS.COMMITS}>
                            <CommitsList {...props} />
                        </Tab>
                    </Tabs>
                </Col>
            </Row>
        </Tab.Container>
    );
}

export default AnalyzerTabs 
