import React from 'react';
import { Table } from 'react-bootstrap';

import './ConfigDetails.css'
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
                    {Object.entries(configInfo?.codeWeightings[0]).map(([key, value]) => 
                    <tr>
                        <td>{key}</td>
                        <td>{value}</td>
                    </tr>)}
                </tbody>

            </Table>


            <Table className='file-weightings-body'>             
                <thead>
                    <tr>
                        <th colSpan='4'>File Weightings</th>
                    </tr>
                </thead>
                {Object.entries(configInfo?.fileWeightings[0]).map((fileWeighting) => 
                    <div className='file-weightings'>
                        <thead>
                            <tr>
                                <th>{fileWeighting[0]}</th>
                                
                            </tr>
                        </thead>
                        {(Object.entries(fileWeighting[1]).map(([weighting, startComment]) =>
                            <tbody>
                                <tr>
                                    <td>{weighting}</td>
                                    <td>{startComment}</td>
                                </tr>
                            </tbody>
                    ))}
                    </div>
                )} 
            </Table>
        </div>

    );
}

export default ConfigDetails;