import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';
import { Redirect } from "react-router-dom";

import RepoModalStudent from './RepoModalStudent';
import RepoModalConfig from './RepoModalConfig';
import RepoModalMatchAlias from './RepoModalMatchAlias';
import { modal } from 'Constants/constants';
import './RepoModal.css';

const RepoModal = (props) => {

    // const {name, id, members, status, toggleModal} = props || {};

    // Temporary for testing:
    const {name, id, status, toggleModal} = props || {};
    const aliases = ['Batman', 'Superman', 'Cat Woman', 'Hulk', 'Iron Man', 'Aquaman', 'Vision'];
    const members = ['brucewayne', 'tonystark', 'clarkkent', 'selinakyle', 'brucebanner']

    const [config, setConfig] = useState("Select a configuration");
    const [student, setStudent] = useState("Select a student");
    
    /*Default times are both at the current date and time*/
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());
    const [redirect, setRedirect] = useState(false);

    const handleClick = () => {
        if (student !== "Select a student") {
            setRedirect(true);
        }
    }

    if (redirect) {
        const data = {
            memberId: student,
            start: startDate.toISOString(),
            end: endDate.toISOString(),
            projectId: id,
        }
        return(<Redirect to={{pathname: '/Analysis', state: { data }}} />);
    }

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

                <RepoModalMatchAlias aliases={aliases} memberIds={members}/>

            </Modal.Body>

            <Modal.Footer>
                <Button onClick={toggleModal} variant="secondary">Cancel</Button>
                {/* TODO: Hookup the analyze button to send this data and begin analysis */}
                <Button variant="success" onClick={handleClick}>Analyze</Button>
            </Modal.Footer>

        </Modal>
    );
};

export default RepoModal