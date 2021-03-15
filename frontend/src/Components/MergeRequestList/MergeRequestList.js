import React from 'react';
import { Table } from 'react-bootstrap';

import { message } from 'Constants/constants';

import './MergeRequestList.css';
import MergeRequest from './MergeRequest';


const MergeRequestList = (props) => {
    const { mergerequests, setCommit, setCodeDiffs } = props || {};
    const handleClick = (commits, diffs) => {
        if(setCodeDiffs) {
            setCodeDiffs(diffs);
        }
        if(setCommit) {
            setCommit(commits);
        }
    }
    return (
        <div className="merge-request-list-container">
                <Table striped bordered hover variant="light">
                    <thead>
                        <tr>
                            <th colSpan='8' className='mrTitle'>Merge Requests</th>
                        </tr>
                    </thead>
                    <thead>
                        <tr>
                            <th>Date Merged</th>
                            <th>Title</th>
                            <th>MR Score</th>
                            <th>Commits Score</th>
                            <th># Commits</th>
                            <th>Lines +</th>
                            <th>Lines -</th>
                        </tr>
                    </thead>
                    <tbody>
                        {!mergerequests?.length ? (
                            <td colSpan='8' >{message.NO_MERGE_REQUEST}</td>
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
