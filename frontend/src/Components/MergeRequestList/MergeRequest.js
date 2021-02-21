import React from 'react';
import {  Button } from 'react-bootstrap';

const MergeRequest = (props) => {
    const { mergerequest, handleClick } = props || {};
    return (
        <tr>
            <td>{mergerequest?.mergeId}</td>
            <td>{mergerequest?.createdDate}</td>
            <td>{mergerequest?.updatedDate}</td>
            <td>{mergerequest?.mergedDate}</td>
            <td>
                <Button variant="light" onClick={() => {handleClick(mergerequest?.mergeId)}}>View</Button>
            </td>
        </tr>
    );
};

export default MergeRequest