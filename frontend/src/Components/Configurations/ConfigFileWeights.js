import React from 'react';
import { Table } from 'react-bootstrap';

import './ConfigDetails.css'

const ConfigFileWeights = (props) => {
    const {fileTypes} = props || {};
    return (
        <Table striped bordered hover variant="light">             
            <thead>
                <tr>
                    <th colSpan='4'>File Weightings</th>
                </tr>
            </thead>
            {Object.entries(fileTypes).map(([fileType, info]) => 
                <div key={fileType} className='file-weightings'>
                    <thead>
                        <tr>
                            <th>{fileType}</th>
                            
                        </tr>
                    </thead>
                    {(Object.entries(info).map(([property, value]) =>
                        <tbody key={property}>
                            <tr>
                                <td>{property}</td>
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