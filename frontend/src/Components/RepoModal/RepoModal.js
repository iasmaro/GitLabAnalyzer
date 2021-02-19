import React, { useState } from 'react';
import { Modal, Button} from 'react-bootstrap';

import RepoModalDates from "./RepoModalDate";
import RepoModalStudent from './RepoModalStudent';
import RepoModalConfig from './RepoModalConfig';
import { modal } from 'Constants/constants';
import './RepoModal.css';

const RepoModal = ({name, members, status, toggleModal}) => {

    const [config, setConfig] = useState("Select a configuration");
    const [student, setStudent] = useState("Select a student");
    const [startDate, setStartDate] = useState({Year:'', Month:'', Day:'', Hours:'0', Minutes:'0', Seconds:'0'});
    const [endDate, setEndDate] = useState({Year:'', Month:'', Day:'', Hours:'0', Minutes:'0', Seconds:'0'});

    return (
        <Modal
            show={status}
            onHide={toggleModal}
            backdrop="static"
            keyboard={false}
            size="xl"
            className="custom-modal"
        >
            <Modal.Header closeButton>
                <Modal.Title>{name}</Modal.Title>
            </Modal.Header>
            <Modal.Body className="repo-modal-body">
                <RepoModalConfig config={config} setConfig={setConfig}></RepoModalConfig>
                <RepoModalStudent members={members} student={student} setStudent={setStudent}></RepoModalStudent>

                <RepoModalDates name={modal.START_DATE} date={startDate} setDate={setStartDate}></RepoModalDates>
                <RepoModalDates name={modal.END_DATE} date={endDate} setDate ={setEndDate}></RepoModalDates>

            </Modal.Body>

            <Modal.Footer>
                <Button onClick={toggleModal} variant="secondary">Cancel</Button>
                {/* TODO: Hookup the analyze button to send this data and begin analysis */}
                <Button variant="success" onClick={console.log(startDate, endDate, student, config)}>Analyze</Button>
            </Modal.Footer>

        </Modal>
    );
};

export default RepoModal