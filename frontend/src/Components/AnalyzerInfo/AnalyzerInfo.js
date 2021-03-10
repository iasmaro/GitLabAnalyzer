import React, { useState, useEffect } from 'react';

import AnalyzerStudent from 'Components/AnalyzerInfo/AnalyzerStudent';

import getProjectMembers from 'Utils/getProjectMembers';

import { useUserState } from 'UserContext';

const AnalyzeInfo = (props) => {
    const { memberId, projectId, startDate, endDate, configuration } = props || {};

    const [members, setMembers] = useState([]);
    const [student, setStudent] = useState("Select a student");

    const username = useUserState();

    useEffect(() => {
        getProjectMembers(username, projectId).then((data) => {
            setMembers(data);
        });
    }, [username, projectId]);

    useEffect(() => {
        if (members.length > 0) {
            setStudent(members[0]);
        }
    }, [members]);

    return (
        <>
            <AnalyzerStudent members={members} student={student} setStudent={setStudent}/>
            <p>{memberId}, {projectId}, {startDate}, {endDate}, {configuration}</p>
        </>
    );
}

export default AnalyzeInfo 
