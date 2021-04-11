import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';
import { Redirect } from "react-router-dom";

import updateUser from 'Utils/updateUser';
import { defaultConfig } from 'Mocks/mockConfigs.js';
import { useUserState } from 'UserContext';
import { modal } from 'Constants/constants';

import RepoAnalyzeModalConfig from './RepoAnalyzeModalConfig';
import RepoAnalyzeModalDateTimePicker from './RepoAnalyzeModalDateTimePicker';
import './RepoAnalyzeModal.css';

const RepoAnalyzeModal = (props) => {
    const { name, id, configs, status, toggleModal, start, end, namespace } = props || {};    
    const [config, setConfig] = useState("Select a configuration");
    const [redirect, setRedirect] = useState(false);
    const [showError, setShowError] = useState(false);
    const [startDate, setStartDate] = useState(start || new Date());
    const [endDate, setEndDate] = useState(end || new Date());
    const username = useUserState();

    const handleClick = () => {
        if (config !== "Select a configuration") {
            setShowError(false);
            setRedirect(true);
        }
        else {
            setShowError(true)
        }
    }

    if (redirect) {
        const data = {
            configuration: config,
            projectId: id,
            startDate: startDate,
            endDate: endDate,
            namespace: namespace,
            projectName: name
        }
        if (startDate !== start || endDate !== end) {
            updateUser(username, '', '', startDate, endDate);
        }
        return(<Redirect to={{pathname: '/Analysis', state: { data }}} />);
    }

    return (
        <Modal
            show={status}
            onHide={toggleModal}
            backdrop="static"
            keyboard={false}
            dialogClassName="custom-modal"
            scrollable={true}
        >
            <Modal.Header closeButton>
                <Modal.Title>{name}</Modal.Title>
            </Modal.Header>

            <Modal.Body className="repo-modal-body">
                <RepoAnalyzeModalConfig defaultConfig={defaultConfig.fileName} configs={configs} config={config} setConfig={setConfig} setShowError={setShowError} />
                {showError && <p className='error-message'>Please select a configuration</p>}
                <RepoAnalyzeModalDateTimePicker 
                    startName={modal.START_DATE} 
                    endName={modal.END_DATE} 
                    startDate={startDate}
                    endDate={endDate}
                    setStartDate={setStartDate} 
                    setEndDate={setEndDate}
                />
            </Modal.Body>

            <Modal.Footer>
                <Button onClick={toggleModal} variant="secondary">Cancel</Button>
                <Button variant="success" onClick={handleClick}>Analyze</Button>
            </Modal.Footer>

        </Modal>
    );
};

export default RepoAnalyzeModal;