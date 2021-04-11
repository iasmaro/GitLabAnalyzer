import React from 'react';
import { Modal, Button } from 'react-bootstrap';

import CodeDiffScoreBreakdown from './CodeDiffScoreBreakdown';

const FileHeaderModal = (props) => {

    const { show, handleClose, fileName } = props || {};

    return (
        <>
            <Modal
                show={show}
                onHide={handleClose}
                backdrop="static"
                keyboard={false}
                size="xl"
                className="custom-modal"
                scrollable={true}       
            >
                
                <Modal.Header closeButton>
                    <Modal.Title>Code Diff Score Breakdown: {fileName}</Modal.Title>
                </Modal.Header>

                <Modal.Body className="repo-modal-body">
                    <CodeDiffScoreBreakdown {...props} />
                </Modal.Body>

                <Modal.Footer>
                    <Button onClick={handleClose} variant="secondary">Close</Button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default FileHeaderModal