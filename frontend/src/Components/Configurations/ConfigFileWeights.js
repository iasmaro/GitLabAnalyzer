import React from 'react';
import { Table } from 'react-bootstrap';

import './ConfigDetails.css'
const ConfigFileWeights = (props) => {
    const {fileWeightings} = props || {};
    return (
        <Table striped bordered hover variant="light">             
            <thead>
                <tr>
                    <th colSpan='4'>File Weightings</th>
                </tr>
            </thead>
            
            {fileWeightings.length > 0 && Object.entries(fileWeightings[0]).map((fileWeighting) => 
                <div key={fileWeighting} className='file-weightings'>
                    <thead>
                        <tr>
                            <th>{fileWeighting[0]}</th>
                            
                        </tr>
                    </thead>
                    {(Object.entries(fileWeighting[1]).map(([key, value]) =>
                        <tbody key={key}>
                            <tr>
                                <td>{key}</td>
                                <td>{value}</td>
                            </tr>
                        </tbody>
                    ))}
                </div>
            )} 
        </Table>

    );
}

export default ConfigFileWeights;