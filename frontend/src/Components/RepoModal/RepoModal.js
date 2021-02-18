import React, { useState } from 'react';
import { Modal, Dropdown, DropdownButton, Button, Row, Col, Form } from 'react-bootstrap'

import { modal } from "Constants/constants"
import RepoModalDates from "./RepoModalDate"

const RepoModal = ({name, members, status, toggleModal}) => {

    const [config, setConfig] = useState("Select a configuration");
    const [student, setStudent] = useState("Select a student");

    const selectConfig = (config) => {
        setConfig(config)
    }

    const selectStudent = (student) => {
        setStudent(student)
    }

    return (
        <Modal
            show={status}
            onHide={toggleModal}
            backdrop="static"
            keyboard={false}
            size="lg"
        >
            <Modal.Header closeButton>
                <Modal.Title>{name}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Row className='configuration'>
                    <Col sm='2'>
                        {modal.CONFIG}
                    </Col>
                    <Col sm='10'>
                        <DropdownButton id="dropdown-basic-button" title={config}>
                            <Dropdown.Item onClick={() => selectConfig(modal.CONFIG_OPTION)}>{modal.CONFIG_OPTION}</Dropdown.Item>
                        </DropdownButton>
                    </Col>
                </Row>

                <Row className='students'>
                    <Col sm='2'>
                        {modal.STUDENT}
                    </Col>
                    <Col sm='10'>
                        <DropdownButton id="dropdown-basic-button" title={student}>
                        {members.map((member) => (
                            <Dropdown.Item as="button" onClick={() => selectStudent(member)}>{member}</Dropdown.Item>
                        ))}
                        </DropdownButton>
                    </Col>
                </Row>

                <RepoModalDates></RepoModalDates>
            </Modal.Body>

            <Modal.Footer>
                <Button onClick={toggleModal} variant="secondary">Cancel</Button>
                <Button variant="success">Analyze</Button>
            </Modal.Footer>

        </Modal>
    )
}

export default RepoModal