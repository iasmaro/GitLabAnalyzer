import React from 'react';
import { Modal, Button } from 'react-bootstrap';

import CodeDiffScoreBreakdown from './CodeDiffScoreBreakdown';

const FileHeaderModal = (prop) => {

    const { 
        status, 
        toggleModal, 
        fileName, 
        extension, 
        linesMoved, 
        addLine, 
        syntaxLinesAdded, 
        deleteLine, 
        configInfo, 
        spaceLinesAdded, 
        meaningfulLinesAdded, 
        commentsLinesAdded,
        linesRemoved } = prop || {};

    return (
        <>
            <Modal
                show={status}
                onHide={toggleModal}
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
                    <CodeDiffScoreBreakdown 
                        extension={extension}
                        linesMoved={linesMoved}
                        addLine={addLine}
                        syntaxLinesAdded={syntaxLinesAdded}
                        deleteLine={deleteLine}
                        configInfo={configInfo}
                        spaceLinesAdded={spaceLinesAdded}
                        meaningfulLinesAdded={meaningfulLinesAdded}
                        commentsLinesAdded={commentsLinesAdded}
                        linesRemoved={linesRemoved}
                    />
                </Modal.Body>

                <Modal.Footer>
                    <Button onClick={toggleModal} variant="secondary">Close</Button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default FileHeaderModal