import React from 'react';
import Table from 'react-bootstrap/Table';

import { message } from 'Constants/constants';
import { useSortableDataObject, getClassNamesFor } from 'Utils/sortTables';
import Commit from './Commit';
import './CommitsList.css';

const CommitsList = (props) => {
    const { commits, setCodeDiffs } = props || {};
    const { items, requestSortObject, sortConfig  } = useSortableDataObject(commits);

    const handleClick = (diffs) => {
        if(setCodeDiffs) {
            setCodeDiffs(diffs);
        }
    }

    return (
        <div className = 'commits-list-container'>
            <Table striped bordered hover variant="light">
                <thead>
                        <tr>
                            <th colSpan='6' className='commitTitle'>Commits</th>
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
                        <td colSpan='4' >{message.NO_COMMITS}</td>
                    )
                    :
                    items.map((commit, i) => (
                        <Commit key={i} commit={commit} handleClick={handleClick} />
                    ))}

                </tbody>
            </Table>
        </div>
    );
};

export default CommitsList