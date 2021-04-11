import React from 'react';
import Button from 'react-bootstrap/Button';
import FormCheckInput from 'react-bootstrap/esm/FormCheckInput';
import { AiOutlineDelete } from 'react-icons/ai';

import { utcToLocal } from 'Components/Utils/formatDates';
import { useUserState } from 'UserContext';

import './ReportsList.css';

const Repo = (props) => {
    const { report, addReport, removeReport } = props || {};
    const username = useUserState();

    if (!report) {
        return null;
    }

    const handleChange = (e) => {
        const isChecked = e?.target?.checked;
        if (isChecked) {
            addReport && addReport(report.reportName);
        } else {
            removeReport && removeReport(report.reportName);
        }
    }

    const deletable = username === report.creator;

    return (
        <tr>
            
            <td><FormCheckInput onChange={handleChange} /></td>
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