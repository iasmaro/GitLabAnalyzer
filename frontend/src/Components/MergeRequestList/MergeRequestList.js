import React from 'react';
import { Table } from 'react-bootstrap';

import { useUserState } from 'UserContext';
import { message } from 'Constants/constants';
import getCommitsInMR from 'Utils/getCommitsInMR';

import './MergeRequestList.css';
import MergeRequest from './MergeRequest';


const MergeRequestList = (props) => {
    const { mergerequests, projectId, setCommit, setCodeDiffs } = props || {};
    const username = useUserState();
    const handleClick = (id, diffs) => {
        if(setCodeDiffs) {
            setCodeDiffs(diffs);
        }
        if(setCommit) {
            setCommit(getCommitsInMR(username, projectId, id));
        }
    }
    return (
        <div className="merge-request-list-container">
                <Table striped bordered hover variant="light">
                    <thead>
                        <tr>
                            <th colSpan='8'className='mrTitle'>Merge Requests</th>
                        </tr>
                    </thead>
                    <thead>
                        <tr>
                            <th>Merge #</th>
                            <th>Create Date</th>
                            <th>Merge Date</th>
                            <th>Update Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        {!mergerequests?.length ? (
                            <td colSpan={8} >{message.NO_MERGE_REQUEST}</td>
                        )
                        :
                        mergerequests.map((mergerequest) => (
                            <MergeRequest key={mergerequest?.mergeId} mergerequest={mergerequest} handleClick={handleClick} />
                        ))}

                    </tbody>
                </Table>
        </div>
    )
}


export default MergeRequestList
