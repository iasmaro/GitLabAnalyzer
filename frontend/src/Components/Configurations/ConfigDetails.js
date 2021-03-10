import React from 'react';
import { Table } from 'react-bootstrap';

import './ConfigDetails.css'
import ConfigFileWeights from './ConfigFileWeights';
const ConfigDetails = (props) => {
    const {configInfo} = props || {};
    return (
        <div className = 'config-details-container'>
            <Table striped bordered hover variant="light">
                <thead>
                    <tr>
                        <th colSpan='4'>{configInfo?.configName}</th>
                    </tr>
                </thead>
                <thead>
                    <tr>
                        <th>Start Date</th>
                        <th>End Date</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>{configInfo?.startDate}</td>
                        <td>{configInfo?.endDate}</td>
                    </tr>
                </tbody>

                <thead>
                    <tr>
                        <th>Code Modifications</th>
                        <th>Weight</th>
                    </tr>
                </thead>
                <tbody>
                    {configInfo?.codeWeightings.length > 0 && Object.entries(configInfo?.codeWeightings[0]).map(([key, value]) => 
                        <tr key={key}>
                            <td>{key}</td>
                            <td>{value}</td>
                        </tr>
                    )}
                </tbody>
            </Table>

            <ConfigFileWeights fileWeightings={configInfo?.fileWeightings}/>
        </div>

    );
}

export default ConfigDetails;