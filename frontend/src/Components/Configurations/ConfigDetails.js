import React from 'react';
import { Table, Button } from 'react-bootstrap';

import './ConfigDetails.css'
import ConfigFileWeights from './ConfigFileWeights';

const ConfigDetails = (props) => {
    const { configInfo, handleEdit } = props || {};
    return (
        <div className = 'config-details-container'>
            {configInfo?.fileName !== 'default' && <Button className="edit-config-btn" onClick={handleEdit}>Edit</Button>}
            <Table striped bordered hover variant="light">
                <thead>
                    <tr>
                        <th colSpan='4'>{configInfo?.fileName}</th>
                    </tr>
                </thead>
                <thead>
                    <tr>
                        <th>Code Modifications</th>
                        <th>Weight</th>
                    </tr>
                </thead>
                <tbody>
                    {configInfo && Object.entries(configInfo?.editFactor).map(([codeScore, value]) => 
                        <tr key={codeScore}>
                            <td>{codeScore}</td>
                            <td>{value}</td>
                        </tr>
                    )}
                </tbody>
            </Table>

            <ConfigFileWeights fileTypes={configInfo}/>
        </div>

    );
}

export default ConfigDetails;