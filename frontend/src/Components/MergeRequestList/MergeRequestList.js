import React, { useState } from 'react';
import { Table } from 'react-bootstrap';
import Spinner from 'react-bootstrap/Spinner';

import { useUserState } from 'UserContext';
import { message } from 'Constants/constants';
import CommitsList from 'Components/CommitsList/CommitsList';
import getCommitsInMR from 'Utils/getCommitsInMR';

import './MergeRequestList.css';
import MergeRequest from './MergeRequest';


const MergeRequestList = (props) => {
    const { mergerequests, projectId } = props || {};
    const [selectedMR, setSelectedMR] = useState();
    const [isLoading, setIsLoading] = useState(true);
    const [commits, setCommits] = useState();
    const username = useUserState();
    const handleClick = (id) => {
        setSelectedMR(id);
        getCommitsInMR(username, projectId, id).then((data) => {
            setCommits(data);
            setIsLoading(false);
        });
    }
    return (
        <div className="merge-request-list-container">
            <div className="left">
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
                            <th></th>
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
            <div className="right">
                {selectedMR && isLoading && <Spinner animation="border" className="spinner" />}
                {selectedMR && !isLoading && <CommitsList commits={commits} />}
            </div>
        </div>
    )
}


export default MergeRequestList
