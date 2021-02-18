import React from 'react';
import {Dropdown, DropdownButton, Row, Col} from 'react-bootstrap'

import { modal } from "Constants/constants"

const RepoModalStudent = ({members, student, setStudent}) => {

    const selectStudent = (student) => {
        setStudent(student)
    }

    return (
        <Row className='students'>
            <Col sm='2'>
                {modal.STUDENT}
            </Col>
            <Col sm='10'>
                <DropdownButton id="dropdown-basic-button" title={student}>
                {members.map((member) => (
                    <Dropdown.Item key={member} as="button" onClick={() => selectStudent(member)}>{member}</Dropdown.Item>
                ))}
                </DropdownButton>
            </Col>
        </Row>
    )
}

export default RepoModalStudent