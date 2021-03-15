import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';
import { Redirect } from "react-router-dom";

import RepoModalConfig from './RepoModalConfig';
import { modal } from 'Constants/constants';
import { defaultConfig } from 'Mocks/mockConfigs.js';
import './RepoModal.css';
import FormattedDateTimePicker from "Components/FormattedDateTimePicker";

const RepoModal = (props) => {

    const {name, id, configs, status, toggleModal} = props || {};

    const [config, setConfig] = useState("default");
    
    /*Default times are both at the current date and time*/
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());
    const [redirect, setRedirect] = useState(false);

    const handleClick = () => {
        setRedirect(true);
    }

    if (redirect) {
        const data = {
            configuration: config,
            start: startDate.toISOString(),
            end: endDate.toISOString(),
            projectId: id,
        }
        return(<Redirect to={{pathname: '/Analysis', state: { data }}} />);
    }

    return (
        <Modal
            show={status}
            onHide={toggleModal}
            backdrop="static"
            keyboard={false}
            size="xl"
            className="custom-modal"
        >
            <Modal.Header closeButton>
                <Modal.Title>{name}</Modal.Title>
            </Modal.Header>
            <Modal.Body className="repo-modal-body">
                <RepoModalConfig defaultConfig={defaultConfig.fileName} configs={configs} config={config} setConfig={setConfig} />

                <FormattedDateTimePicker startName={modal.START_DATE} endName={modal.END_DATE} setStartDate={setStartDate} setEndDate={setEndDate}/>
            </Modal.Body>

            <Modal.Footer>
                <Button onClick={toggleModal} variant="secondary">Cancel</Button>
                {/* TODO: Hookup the analyze button to send this data and begin analysis */}
                <Button variant="success" onClick={handleClick}>Analyze</Button>
            </Modal.Footer>

        </Modal>
    );
};

export default RepoModal