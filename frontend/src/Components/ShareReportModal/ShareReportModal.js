import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

const ShareReportModal = (props) => {
    const { status, toggleModal, shareReports } = props || {};

    const [userId, setUserId] = useState('');
    const [showError, setShowError] = useState(false);

    const handleChange = (event) => {
        const { value } = event?.target;
        setUserId(value);
    }

    const handleClick = () => {
        if (userId) {
            setShowError(false);
            shareReports && shareReports(userId);
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
                <Modal.Title>Share Reports</Modal.Title>
            </Modal.Header>

            <Modal.Body className="repo-modal-body">
                <Form.Label>User ID</Form.Label>
                <Form.Control
                    type="text"
                    name="userId"
                    placeholder="User ID"
                    value={userId}
                    onChange={handleChange}
                />
                {showError && <p className='error-message'>Please add a user ID</p>}
            </Modal.Body>

            <Modal.Footer>
                <Button onClick={toggleModal} variant="secondary">Cancel</Button>
                <Button variant="success" onClick={handleClick}>Share</Button>
            </Modal.Footer>

        </Modal>
    );
};

export default ShareReportModal;