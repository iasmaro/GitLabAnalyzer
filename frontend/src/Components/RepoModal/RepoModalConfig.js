import React from 'react';
import {Dropdown, DropdownButton, Row, Col} from 'react-bootstrap'

import { modal } from "Constants/constants"

const RepoModalConfig = ({config, setConfig}) => {

    const selectConfig = (config) => {
        setConfig(config)
    }
    
    return (
    <Row className='configuration'>
        <Col sm='2'>
            {modal.CONFIG}
        </Col>
        <Col sm='10'>
            <DropdownButton id="dropdown-basic-button" title={config}>
                <Dropdown.Item onClick={() => selectConfig(modal.CONFIG_OPTION)}>{modal.CONFIG_OPTION}</Dropdown.Item>
            </DropdownButton>
        </Col>
    </Row>
    )
}

export default RepoModalConfig