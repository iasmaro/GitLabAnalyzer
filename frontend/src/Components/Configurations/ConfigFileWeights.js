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
            
            {Object.entries(fileTypes?.fileFactor).map(([fileType, value]) => 
                <div key={fileType} className='file-weightings'>
                    <thead>
                        <tr>
                            <th>{fileType}</th>   
                        </tr>
                    </thead>
                    <tbody>
                        
                        <tr>
                            <td>Weight: </td>
                            <td>{value}</td>
                        </tr>
                    </tbody>

                    {fileTypes?.commentTypes[fileType].map((comments) =>
                        <tbody>
                            {comments?.endType && (
                                <>
                                    <tr>
                                        <td>Start Comment</td>
                                        <td>{comments?.startType}</td>
                                    </tr>
                                
                                    <tr>
                                        <td>End Comment</td>
                                        <td>{comments?.endType}</td>
                                    </tr>
                                </>
                                
                            )}
                            {!comments?.endType && (
                                <tr>
                                    <td>Single Line Comment</td>
                                    <td>{comments?.startType}</td>
                                </tr>
                            )}
                        </tbody>
                    )}

                </div>
            )} 
        </Table>

    );
}

export default ConfigFileWeights;