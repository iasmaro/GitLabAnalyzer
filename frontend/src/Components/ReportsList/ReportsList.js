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
                        <th colSpan='4' className='report-title'>Reports</th>
                        <th colSpan='4' className='report-title'><RepoSearchBar searchWord={searchWord} setSearchWord={setSearchWord} /></th>
                    </tr>
                </thead>
                <thead>
                    <tr className="report-headers">
                        <th className={getClassNamesFor(sortConfig, 'projectName')} onClick={() => requestSortObject('projectName')}>Repo Name</th>
                        <th className={getClassNamesFor(sortConfig, 'start')} onClick={() => requestSortObject('start')}>Start Date</th>
                        <th className={getClassNamesFor(sortConfig, 'end')} onClick={() => requestSortObject('end')}>End Date</th>
                        <th className={getClassNamesFor(sortConfig, 'configName')} onClick={() => requestSortObject('configName')}>Configuration</th>
                        <th className={getClassNamesFor(sortConfig, 'creator')} onClick={() => requestSortObject('creator')}>Creator</th>
                        <th colSpan='2'></th>
                    </tr>
                </thead>
                <tbody>
                    {!reports || !reports.length ? (
                        <tr>
                            <td colSpan={3} >{message.TOKEN_NOT_SET}</td>
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