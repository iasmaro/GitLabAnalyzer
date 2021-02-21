import React, { useState } from 'react';
import { Table } from 'react-bootstrap';

import { message } from 'Constants/constants';
import { commits } from 'Mocks/mockCommits';
import CommitsList from 'Components/CommitsList/CommitsList';

import './MergeRequestList.css';
import MergeRequest from './MergeRequest';


const MergeRequestList = (props) => {
    const { mergerequests } = props || {};
    const [selectedMR, setSelectedMR] = useState();
    const handleClick = (id) => {
        setSelectedMR(id);
    }
    return (
        <div className="merge-request-list-container">
            <div className="left">
                <Table striped bordered hover variant="dark">
                    <thead>
                        <tr>
                        <th colSpan='8'>Merge Requests</th>
                        </tr>
                    </thead>
                    <thead>
                        <tr>
                            <th>Merge #</th>
                            <th>Create Date</th>
                            <th>Merge Date</th>
                            <th>Update Date</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {!mergerequests?.length ? (
                            <td colSpan={8} >{message.NO_MERGE_REQUEST}</td>
                        )
                        :
                        mergerequests.map((mergerequest) => (
                            <MergeRequest key={mergerequest?.projectId} mergerequest={mergerequest} handleClick={handleClick} />
                        ))}

                    </tbody>
                </Table>
            </div>
            <div className="right">
                {selectedMR && <CommitsList commits={commits} />}
            </div>
        </div>
    )
}


export default MergeRequestList
