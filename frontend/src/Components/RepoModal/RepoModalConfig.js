import React from 'react';
import { Dropdown, DropdownButton, Row, Col } from 'react-bootstrap';

import FormattedDateTimePicker from "Components/FormattedDateTimePicker";
import { modal } from "Constants/constants";

import { useUserState } from 'UserContext';
import updateUser from 'Utils/updateUser';
import deleteActiveConfig from 'Utils/deleteActiveConfig';

const RepoModalConfig = (props) => {
    
    const {defaultConfig, configs, config, setConfig} = props;
    const username = useUserState();

    const selectConfig = (configuration) => {
        if (configuration === 'default') {
            deleteActiveConfig(username);
        }
        else {
            updateUser(username, '', '', configuration);
        }
        setConfig(configuration);
    };

    return (
    <Row className='configuration'>
        <Col lg='2'>
            {modal.CONFIG}
        </Col>
        <Col lg='3'>
            <DropdownButton variant="secondary" id="dropdown-basic-button" title={config}>
                <Dropdown.Item as="button" onClick={() => selectConfig(defaultConfig)}>{defaultConfig}</Dropdown.Item>
                {configs.map((config) => (
                    <Dropdown.Item key={config} as="button" onClick={() => selectConfig(config)}>{config}</Dropdown.Item>
                ))}
            </DropdownButton>
        </Col>
        <Col lg='5'>
            <FormattedDateTimePicker setStartDate={modal.START_DATE} setEndDate={modal.END_DATE} readOnly={true}/>
        </Col>
    </Row>
    );
};

export default RepoModalConfig;