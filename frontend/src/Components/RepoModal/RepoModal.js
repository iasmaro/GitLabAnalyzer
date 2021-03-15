import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';
import { Redirect } from "react-router-dom";

import RepoModalConfig from './RepoModalConfig';
import { modal } from 'Constants/constants';
import './RepoModal.css';
import FormattedDateTimePicker from "Components/FormattedDateTimePicker";

const RepoModal = (props) => {

    const { name, id, status, toggleModal } = props || {};

    const [config, setConfig] = useState("Select a configuration");
    
    /*Default times are both at the current date and time*/
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());
    const [redirect, setRedirect] = useState(false);

    const handleClick = () => {
        if (config !== "Select a configuration") {
            setRedirect(true);
        }
    }

    if (redirect) {
        const data = {
            start: startDate.toISOString(),
            end: endDate.toISOString(),
            projectId: id,
            configuration: config,
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
                <RepoModalConfig config={config} setConfig={setConfig} />
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