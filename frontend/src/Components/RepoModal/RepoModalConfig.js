import React from 'react';
import {Dropdown, DropdownButton, Row, Col} from 'react-bootstrap';

import { modal } from "Constants/constants";

import { useUserState } from 'UserContext';
import updateUser from 'Utils/updateUser';

const RepoModalConfig = (props) => {
    
    const {defaultConfig, configs, config, setConfig} = props;

    const username = useUserState();

    const selectConfig = (configuration) => {
        if (configuration === 'default') {
            updateUser(username, '', '', '');
        }
        else {
            updateUser(username, '', '', configuration);
        }
        setConfig(configuration);
    };

    return (
    <Row className='configuration'>
        <Col sm='2'>
            {modal.CONFIG}
        </Col>
        <Col sm='8'>
            <DropdownButton variant="secondary" id="dropdown-basic-button" title={config}>
                <Dropdown.Item as="button" onClick={() => selectConfig(defaultConfig)}>{defaultConfig}</Dropdown.Item>
                {configs.map((config) => (
                    <Dropdown.Item key={config} as="button" onClick={() => selectConfig(config)}>{config}</Dropdown.Item>
                ))}
            </DropdownButton>
        </Col>
    </Row>
    );
};

export default RepoModalConfig;