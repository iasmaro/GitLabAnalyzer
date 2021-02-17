import React from 'react';
import {Modal} from 'react-bootstrap'

const RepoModal = ({status, toggleModal}) => {
    return (
        <Modal
            show={status}
            onHide={toggleModal}
            backdrop="static"
            keyboard={false}
        >
            <Modal.Header closeButton>
                <Modal.Title>Modal is working!</Modal.Title>
            </Modal.Header>

        </Modal>
    )
}

export default RepoModal