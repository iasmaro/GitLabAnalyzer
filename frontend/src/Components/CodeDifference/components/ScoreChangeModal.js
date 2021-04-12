import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

const ScoreChangeModal = (props) => {
    const { status, handleClose, changeScore } = props || {};

    const [newScore, setNewScore] = useState();
    const [showError, setShowError] = useState(false);

    const handleChange = (event) => {
        const { value } = event?.target;
        setNewScore(value);
    }

    const handleClick = () => {
        if (newScore < 0) {
            setShowError(true);
        } else {
            changeScore(newScore);
        }
    }

    return (
        <Modal
            show={status}
            onHide={handleClose}
            backdrop="static"
            keyboard={false}
            dialogClassName="custom-modal"
            scrollable={true}
        >
            <Modal.Header closeButton>
                <Modal.Title>Change Score</Modal.Title>
            </Modal.Header>

            <Modal.Body className="repo-modal-body">
                <Form.Label>New Score</Form.Label>
                <Form.Control
                    type="number"
                    name="Score"
                    placeholder="New Score"
                    value={newScore}
                    onChange={handleChange}
                />
                {showError && <p className='error-message'>Please add a score</p>}
            </Modal.Body>

            <Modal.Footer>
                <Button onClick={handleClose} variant="secondary">Cancel</Button>
                <Button variant="success" onClick={handleClick}>Change</Button>
            </Modal.Footer>

        </Modal>
    );
};

export default ScoreChangeModal;