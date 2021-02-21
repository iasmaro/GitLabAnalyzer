import React from 'react';
import {  Button } from 'react-bootstrap';

const MergeRequest = (props) => {
    const { mergerequest } = props || {};
    return (
        <tr>
            <td>1</td>
            <td>{mergerequest?.createdDate}</td>
            <td>{mergerequest?.mergeId}</td>
            <td>{mergerequest?.memberScore}</td>
            <td>{mergerequest?.updatedDate}</td>
            <td>{mergerequest?.mergedDate}</td>
            <td>{mergerequest?.mrscore}</td>
            <td>
                <Button variant="dark">View</Button>
            </td>
        </tr>
    );
};

export default MergeRequest