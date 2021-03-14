import React from 'react';
import {Dropdown, DropdownButton, Row, Col} from 'react-bootstrap';

import { modal } from "Constants/constants";

const RepoModalConfig = (props) => {
    
    const {defaultConfig, configs, config, setConfig} = props;

    const selectConfig = (config) => {
        setConfig(config);
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