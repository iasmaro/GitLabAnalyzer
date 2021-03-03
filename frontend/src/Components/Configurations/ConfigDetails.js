import React from 'react';
import { Table } from 'react-bootstrap';

const ConfigDetails = (props) => {
    const {configInfo} = props || {};
    return (
        <div className = 'commits-list-container'>
            <Table striped bordered hover variant="light">
                <thead>
                    <tr>
                        <th colSpan='4'className='commitTitle'>{configInfo[0]?.configName}</th>
                    </tr>
                </thead>
                <thead>
                    <tr>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Code Weightings</th>
                        <th>File Weightings</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>{configInfo[0]?.startDate}</td>
                        <td>{configInfo[0]?.endDate}</td>
                        <td>{configInfo[0]?.configName}</td>
                        <td>{configInfo[0]?.configName}</td>
                    </tr>
                </tbody>
            </Table>
        </div>
    );
}

export default ConfigDetails;