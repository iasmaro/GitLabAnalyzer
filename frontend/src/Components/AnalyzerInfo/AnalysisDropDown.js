import React from 'react';
import { Dropdown, DropdownButton } from 'react-bootstrap';

const AnalysisDropDown = (props) => {

    const { members, student, setStudent } = props || {};

    if (!members) {
        return null;
    }

    const selectStudent = (student) => {
        setStudent(student);
    };

    return (
        <DropdownButton variant="secondary" id="dropdown-basic-button" title={student}>
            {members.map((member) => (
                <Dropdown.Item key={member} as="button" onClick={() => selectStudent(member)}>{member}</Dropdown.Item>
            ))}
        </DropdownButton>
    );
};

export default AnalysisDropDown;