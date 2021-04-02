import React, { useState } from 'react';
import { Table } from 'react-bootstrap';

import { message } from 'Constants/constants';

import './MergeRequestList.css';
import MergeRequest from './MergeRequest';


const MergeRequestList = (props) => {
    const { mergerequests, setCommit, setCodeDiffs } = props || {};
    const [selectedRowIndex, setSelectedRowIndex] = useState(-1);    
    const handleClick = (commits, diffs, index) => {
        if(setCodeDiffs) {
            setCodeDiffs(diffs);
        }
        if(setCommit) {
            setCommit(commits);
        }
        setSelectedRowIndex(index);
    }
    return (
        <div className='merge-request-list-container'>
            <Table bordered hover variant='light'>
                <thead>
                    <tr>
                        <th colSpan='7' className='mrTitle'>Merge Requests</th>
                    </tr>
                </thead>
                <thead>
                    <tr className='mr-headers'>
                        <th>Date Merged</th>
                        <th>Title</th>
                        <th>MR Score</th>
                        <th>Commits Score</th>
                        <th># Commits</th>
                        <th colSpan='2'>Lines Changed</th>
                    </tr>
                </thead>
                <tbody>
                    {!mergerequests?.length ? (
                        <td colSpan='8' >{message.NO_MERGE_REQUEST}</td>
                    )
                    :
                    mergerequests.map((mergerequest, index) => (
                        <MergeRequest 
                            key={mergerequest?.mergeId} 
                            mergerequest={mergerequest} 
                            handleClick={handleClick} 
                            index = {index}
                            selected={index === selectedRowIndex}/>
                    ))}

                </tbody>
            </Table>
        </div>
    )
}


export default MergeRequestList
