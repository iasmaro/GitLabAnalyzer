import React, { useState } from 'react';
import { Table } from 'react-bootstrap';

import { message } from 'Constants/constants';
import { useSortableDataObject, getClassNamesFor } from 'Utils/sortTables';
import filterRepos from 'Utils/filterRepos'

import Report from './Report';
import RepoSearchBar from './RepoSearchBar';
import './ReportsList.css';

const ReportsList = (props) => {
    const { reports } = props || {};
    const [searchWord, setSearchWord] = useState('');
    const { items, requestSortObject, sortConfig  } = useSortableDataObject(reports);
    
    return (
        <div className = 'list-container'>
            <Table striped borderless hover variant="light">
                <thead>
                    <tr className='table-header'>
                        <th colSpan='4' className='repoTitle'>Reports</th>
                        <th colSpan='4' className='repoTitle'><RepoSearchBar searchWord={searchWord} setSearchWord={setSearchWord} /></th>
                    </tr>
                </thead>
                <thead>
                    <tr className="repo-headers">
                        <th className={getClassNamesFor(sortConfig, 'projectName')} onClick={() => requestSortObject('projectName')}>Repo Name</th>
                        <th className={getClassNamesFor(sortConfig, 'updatedAt')} onClick={() => requestSortObject('updatedAt')}>Start Date</th>
                        <th className={getClassNamesFor(sortConfig, 'updatedAt')} onClick={() => requestSortObject('updatedAt')}>End Date</th>
                        <th className={getClassNamesFor(sortConfig, 'updatedAt')} onClick={() => requestSortObject('updatedAt')}>Configuration</th>
                        <th className={getClassNamesFor(sortConfig, 'updatedAt')} onClick={() => requestSortObject('updatedAt')}>Creator</th>
                        <th colSpan='2'></th>
                    </tr>
                </thead>
                <tbody>
                    {!reports ? (
                        <tr>
                            <td colSpan={3} >{message.TOKEN_NOT_SET}</td>
                        </tr>
                    )
                    : !reports.length ? (
                        <tr>
                            <td colSpan={3} >{message.NO_REPOS}</td>
                        </tr>
                    )
                    :
                    items.filter((report)=>filterRepos(report, searchWord)).map((report, key) => (
                        <Report key={key} report={report}/>
                    ))}
                </tbody>
            </Table>
        </div>
    );
};

export default ReportsList;