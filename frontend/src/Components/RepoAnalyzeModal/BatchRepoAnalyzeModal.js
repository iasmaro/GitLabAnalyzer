import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';

import updateUser from 'Utils/updateUser';
import analyzeAll from 'Utils/analyzeAll';
import { defaultConfig } from 'Mocks/mockConfigs.js';
import { useUserState } from 'UserContext';
import { modal } from 'Constants/constants';

import RepoAnalyzeModalConfig from './RepoAnalyzeModalConfig';
import RepoAnalyzeModalDateTimePicker from './RepoAnalyzeModalDateTimePicker';
import './RepoAnalyzeModal.css';

const BatchRepoAnalyzeModal = (props) => {
    const { configs, status, toggleModal, start, end, reposBatch } = props || {};    
    const [config, setConfig] = useState("Select a configuration");
    const [showError, setShowError] = useState(false);
    const [startDate, setStartDate] = useState(start || new Date());
    const [endDate, setEndDate] = useState(end || new Date());
    const username = useUserState();

    const handleClick = () => {
        if (config !== "Select a configuration") {
            setShowError(false);
            if (startDate !== start || endDate !== end) {
                updateUser(username, '', '', startDate, endDate);
            } 
            for (let repo of reposBatch) {
                analyzeAll(username, repo);
            }
            toggleModal();
        }
        else {
            setShowError(true);
        }
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
                <Modal.Title>Batch Analysis</Modal.Title>
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

export default BatchRepoAnalyzeModal;