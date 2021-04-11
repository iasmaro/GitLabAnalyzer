import React, { useState } from 'react';
import { Button, Spinner } from 'react-bootstrap';
import { AiOutlineDelete } from 'react-icons/ai';

import { utcToLocal } from 'Components/Utils/formatDates';
import { useUserState } from 'UserContext';

import './ReportsList.css';

const Repo = (props) => {
    const { report } = props || {};
    const username = useUserState();

    if (!report) {
        return null;
    }

    const deletable = username === report.creator;

    return (
        <tr>
            <td>{report.projectName}</td>
            <td>{utcToLocal(report.start)}</td>
            <td>{utcToLocal(report.end)}</td>
            <td>{report.configName}</td>
            <td>{report.creator}</td>
            <td>
                <Button variant="dark">View</Button>
            </td>
            <td>
                <Button variant="danger" disabled={!deletable}><AiOutlineDelete /></Button>
            </td>
        </tr>
    );
};

export default Repo;