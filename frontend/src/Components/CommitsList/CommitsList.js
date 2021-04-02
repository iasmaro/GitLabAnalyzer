import React, { useState, useEffect } from 'react';
import Table from 'react-bootstrap/Table';

import { message } from 'Constants/constants';
import { useSortableDataObject, getClassNamesFor } from 'Utils/sortTables';
import Commit from './Commit';
import './CommitsList.css';

const CommitsList = (props) => {
    const { commits, setCodeDiffs } = props || {};
    const [selectedRowIndex, setSelectedRowIndex] = useState(-1);
    const { items, requestSortObject, sortConfig  } = useSortableDataObject(commits);

    useEffect(() => {
        setSelectedRowIndex(-1);
    }, [commits]);

    const handleClick = (diffs, index) => {
        if(setCodeDiffs) {
            setCodeDiffs(diffs);
        }
        setSelectedRowIndex(index);
    }

    return (
        <div className = 'commits-list-container'>
            <Table bordered hover variant="light">
                <thead>
                        <tr>
                            <th colSpan='4' className='commitTitle'>Commits</th>
                            <th colSpan='2' className='mrTitle'>Total Commits: {commits?.length || 0}</th>
                        </tr>
                </thead>
                <thead>
                    <tr className='commits-headers'>
                        <th className={getClassNamesFor(sortConfig, 'commitDate')} onClick={() => requestSortObject('commitDate')}>Date</th>
                        <th className={getClassNamesFor(sortConfig, 'commitMessage')} onClick={() => requestSortObject('commitMessage')}>Message</th>
                        <th className={getClassNamesFor(sortConfig, 'commitScore')} onClick={() => requestSortObject('commitScore')}>Score</th>
                        <th className={getClassNamesFor(sortConfig, 'commitAuthor')} onClick={() => requestSortObject('commitAuthor')}>Author</th>
                        <th className={getClassNamesFor(sortConfig, 'linesAdded')} onClick={() => requestSortObject('linesAdded')}>Lines +</th>
                        <th className={getClassNamesFor(sortConfig, 'linesRemoved')} onClick={() => requestSortObject('linesRemoved')}>Lines -</th>
                    </tr>
                </thead>
                <tbody>
                    {!commits?.length ? (
                        <td colSpan='6' >{message.NO_COMMITS}</td>
                    )
                    :
                    items.map((commit, index) => (
                        <Commit 
                            key={index} 
                            commit={commit} 
                            handleClick={handleClick} 
                            index={index}
                            selected={index === selectedRowIndex}/>
                    ))}

                </tbody>
            </Table>
        </div>
    );
};

export default CommitsList