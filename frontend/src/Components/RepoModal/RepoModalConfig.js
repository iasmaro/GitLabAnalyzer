import React, { useState } from 'react';
import { Dropdown, DropdownButton, Row, Col } from 'react-bootstrap';

import FormattedDateTimePicker from "Components/FormattedDateTimePicker";
import { modal } from "Constants/constants";

const RepoModalConfig = (props) => {
    
    const { config, setConfig } = props || {};
    /*Default times are both at the current date and time*/
    const startDate = useState(new Date());
    const endDate = useState(new Date());

    const selectConfig = (config) => {
        // TO-DO: Update start & end date with dates in the selected config
        setConfig(config);
    };

    

    return (
    <Row className='configuration'>
        <Col lg='2'>
            {modal.CONFIG}
        </Col>
        <Col lg='3'>
            <DropdownButton variant="secondary" id="dropdown-basic-button" title={config}>
                <Dropdown.Item onClick={() => selectConfig(modal.CONFIG_OPTION)}>{modal.CONFIG_OPTION}</Dropdown.Item>
            </DropdownButton>
        </Col>
        <Col lg='5'>
            <FormattedDateTimePicker startName={modal.START_DATE} endName={modal.END_DATE} setStartDate={startDate} setEndDate={endDate} readOnly={true}/>
        </Col>
    </Row>
    );
};

export default RepoModalConfig;