import React, { useState } from 'react';
import { Table } from 'react-bootstrap';

import { message } from 'Constants/constants';
import { useSortableDataObject, getClassNamesFor } from 'Utils/sortTables';
import './MergeRequestList.css';
import MergeRequest from './MergeRequest';

const MergeRequestList = (props) => {
    const { mergerequests, setCommit, setCodeDiffs, setDiffsTitle } = props || {};
    const [selectedRowIndex, setSelectedRowIndex] = useState(-1);    
    const { items, requestSortObject, sortConfig  } = useSortableDataObject(mergerequests);
    const handleClick = (commits, diffs, index, diffsTitle, mergeRequestLink) => {
        if(setCodeDiffs) {
            setCodeDiffs(diffs, mergeRequestLink);
        }
        if(setCommit) {
            setCommit(commits);
        }
        setSelectedRowIndex(index);
        setDiffsTitle(diffsTitle);
    }
    return (
        <div className='merge-request-list-container'>
            <Table bordered hover variant='light'>
                <thead>
                    <tr>
                        <th colSpan='5' className='mrTitle'>Merge Requests</th>
                        <th colSpan='2' className='mrTitle'>Total Merge Requests: {mergerequests?.length || 0}</th>
                    </tr>
                </thead>
                <thead>
                    <tr className='mr-headers'>
                        <th className={getClassNamesFor(sortConfig, 'mergedDate')} onClick={() => requestSortObject('mergedDate')}>Date Merged</th>
                        <th className={getClassNamesFor(sortConfig, 'mergeRequestTitle')} onClick={() => requestSortObject('mergeRequestTitle')}>Title</th>
                        <th className={getClassNamesFor(sortConfig, 'mrscore')} onClick={() => requestSortObject('mrscore')}>MR Score</th>
                        <th className={getClassNamesFor(sortConfig, 'sumOfCommitScore')} onClick={() => requestSortObject('sumOfCommitScore')}>Commits Score</th>
                        <th className={getClassNamesFor(sortConfig, 'commitDTOList')} onClick={() => requestSortObject('commitDTOList')}># Commits</th>
                        <th className={getClassNamesFor(sortConfig, 'linesAdded')} onClick={() => requestSortObject('linesAdded')}>Lines +</th>
                        <th className={getClassNamesFor(sortConfig, 'linesRemoved')} onClick={() => requestSortObject('linesRemoved')}>Lines -</th>
                    </tr>
                </thead>
                <tbody>
                    {!mergerequests?.length ? (
                        <td colSpan='8' >{message.NO_MERGE_REQUEST}</td>
                    )
                    :
                    items.map((mergerequest, index) => (
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
