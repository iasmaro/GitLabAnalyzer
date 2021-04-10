import React from 'react';
import { useUserState } from 'UserContext';
import { Modal, Button } from 'react-bootstrap';

import { ConfigLabels } from 'Constants/constants';
import ConfigForm from 'Components/Configurations/ConfigurationForm/ConfigForm';


const ConfigModal = (prop) => {

    const { status, toggleModal, setMessage, configInfo } = prop || {};

    const username = useUserState();

    
    return (
        <>
            <Modal
                show={status}
                onHide={toggleModal}
                backdrop="static"
                keyboard={false}
                size="xl"
                className="custom-modal"
            >
                <Modal.Header closeButton>
                    <Modal.Title>{ConfigLabels.CONFIGURATION_FORM}</Modal.Title>
                </Modal.Header>

                <Modal.Body className="repo-modal-body">
                    <ConfigForm username={username} toggleModal={toggleModal} setMessage={setMessage} configInfo={configInfo}/>
                </Modal.Body>

                <Modal.Footer>
                    <Button onClick={toggleModal} variant="secondary">Cancel</Button>
                </Modal.Footer>
            </Modal>
        </>

       
    );
};

export default ConfigModal