import React, {useState} from 'react';
import Button from 'react-bootstrap/Button';
import FormCheckInput from 'react-bootstrap/esm/FormCheckInput';
import { AiOutlineDelete } from 'react-icons/ai';
import { Redirect } from "react-router-dom";

import { utcToLocal } from 'Components/Utils/formatDates';

import './ReportsList.css';

const Report = (props) => {
    const { report, addReport, removeReport, deleteReport, username } = props || {};

    const [redirect, setRedirect] = useState(false);

    if (!report) {
        return null;
    }

    // temporary fix for getting the configuration name
    const reportNameList = report.reportName?.split('_');
    const endDate = reportNameList.pop();
    const startDate = reportNameList.pop();
    const projectId = reportNameList[0];
    const configName = report.reportName?.replace(`${projectId}_`, '').replace(`${report.projectName}_`, '').replace(`_${startDate}`, '').replace(`_${endDate}`, '');

    const handleChange = (e) => {
        const isChecked = e?.target?.checked;
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
        const projectId = report.reportName?.split('_')[0];
        const data = {
            configuration: report.configName || configName,
            startDate: report.start,
            endDate: report.end,
            projectName: report.projectName,
            reportName: report.reportName,
            projectId: projectId,
            creator: report.creator
        }

        return(<Redirect to={{pathname: '/Analysis', state: { data }}} />);
    }

    const deletable = username === report.creator;

    return (
        <tr>
            
            <td><FormCheckInput onChange={handleChange} /></td>
            <td>{report.projectName}</td>
            <td>{utcToLocal(report.start)}</td>
            <td>{utcToLocal(report.end)}</td>
            <td>{report.configName || configName}</td>
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