import React from 'react';
import { useUserState } from 'UserContext';
import { Modal, Button } from 'react-bootstrap';

const FileHeaderModal = (prop) => {

    const {status, toggleModal, fileName} = prop || {};
   
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
                    <Modal.Title>{fileName} Score Breakdown</Modal.Title>
                </Modal.Header>

                <Modal.Body className="repo-modal-body">
                    TEMP
                </Modal.Body>

                <Modal.Footer>
                    <Button onClick={toggleModal} variant="secondary">Cancel</Button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default FileHeaderModal