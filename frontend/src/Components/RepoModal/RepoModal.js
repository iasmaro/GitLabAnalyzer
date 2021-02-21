import React, { useState } from 'react';
import { Modal, Button} from 'react-bootstrap';

import RepoModalDates from "./RepoModalDate";
import RepoModalStudent from './RepoModalStudent';
import RepoModalConfig from './RepoModalConfig';
import { modal } from 'Constants/constants';
import './RepoModal.css';
import { createStartDate } from './Utils/getDates';
import { createEndDate } from './Utils/getDates';

const RepoModal = (props) => {

    const {name, createdAt, members, status, toggleModal} = props;

    /*Default times are the beginning of unix time to the current date and time*/
    const defaultStartDate = createStartDate(createdAt);
    const defaultEndDate = createEndDate();

    const [config, setConfig] = useState("Select a configuration");
    const [student, setStudent] = useState("Select a student");
    const [startDate, setStartDate] = useState(defaultStartDate);
    const [endDate, setEndDate] = useState(defaultEndDate);

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
                <RepoModalConfig config={config} setConfig={setConfig} />
                <RepoModalStudent members={members} student={student} setStudent={setStudent} />

                <RepoModalDates name={modal.START_DATE} date={startDate} setDate={setStartDate} />
                <RepoModalDates name={modal.END_DATE} date={endDate} setDate ={setEndDate} />

            </Modal.Body>

            <Modal.Footer>
                <Button onClick={toggleModal} variant="secondary">Cancel</Button>
                {/* TODO: Hookup the analyze button to send this data and begin analysis */}
                <Button variant="success">Analyze</Button>
            </Modal.Footer>

        </Modal>
    );
};

export default RepoModal