import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Checkbox from '@material-ui/core/Checkbox';
import { AiOutlineDelete } from 'react-icons/ai';
import { Redirect } from "react-router-dom";

import { utcToLocal } from 'Components/Utils/formatDates';

import './ReportsList.css';

const Report = (props) => {
    const { report, addReport, removeReport, deleteReport, username } = props || {};

    const [redirect, setRedirect] = useState(false);
    const [checked, setChecked] = useState(false);

    if (!report) {
        return null;
    }

    const handleChange = () => {
        const isChecked = !checked;
        setChecked(isChecked);
        if (isChecked) {
            addReport && addReport(report.reportName);
        } else {
            removeReport && removeReport(report.reportName);
        }
    }

    const viewReport = () => {
        setRedirect(true);
    }

    if (redirect) {
        const data = {
            configuration: report.configName,
            startDate: report.start,
            endDate: report.end,
            projectName: report.projectName,
            reportName: report.reportName,
            projectId: report.projectId,
            creator: report.creator
        }

        return(<Redirect to={{pathname: '/Analysis', state: { data }}} />);
    }

    const deletable = username === report.creator;

    return (
        <tr>
            
            <td><Checkbox color='default' checked={checked} onClick={handleChange} /></td>
            <td>{report.projectName}</td>
            <td>{utcToLocal(report.start)}</td>
            <td>{utcToLocal(report.end)}</td>
            <td>{report.configName}</td>
            <td>{report.creator}</td>
            <td>
                <Button variant="dark" onClick={viewReport}>View</Button>
            </td>
            <td>
                <Button variant="danger" disabled={!deletable} onClick={() => deleteReport && deleteReport(report.reportName)} ><AiOutlineDelete /></Button>
            </td>
        </tr>
    );
};

export default Report;