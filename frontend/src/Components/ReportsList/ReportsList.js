import React, { useState } from 'react';
import { Table, Button } from 'react-bootstrap';

import { message } from 'Constants/constants';
import { useSortableDataObject, getClassNamesFor } from 'Utils/sortTables';
import { useUserState } from 'UserContext';
import filterRepos from 'Utils/filterRepos';
import ShareReportModal from 'Components/ShareReportModal/ShareReportModal';

import Report from './Report';
import SearchBar from 'Components/SearchBar/SearchBar';
import './ReportsList.css';

const ReportsList = (props) => {
    const { reports } = props || {};
    const [searchWord, setSearchWord] = useState('');
    const [show, setShow] = useState(false);
    const [showSuccessMessage, setShowSuccessMessage] = useState(false);
    const [showFailureMessage, setShowFailureMessage] = useState(false);
    const [selectedReports, setSelectedReports] = useState([]);
    const { items, requestSortObject, sortConfig  } = useSortableDataObject(reports);

    const username = useUserState();

    const addReport = (name) => {
        const updatedSelectedReports = selectedReports.slice();
        updatedSelectedReports.push(name);
        setSelectedReports(updatedSelectedReports);
    };
    
    const removeReport = (name) => {
        const updatedSelectedReports = selectedReports.slice();
        const indexOfReport = updatedSelectedReports.indexOf(name);
        if (indexOfReport !== -1) {
            updatedSelectedReports.splice(indexOfReport, 1);
            setSelectedReports(updatedSelectedReports);
        } 
    };

    const shareReports = () => {
        setShow(true);
    }

    const closeShare = () => {
        setShow(false);
        setShowSuccessMessage(true);
        setTimeout(function () { setShowSuccessMessage(false); }, 3000);
    }
    
    const areSelected = selectedReports.length > 0;

    return (
        <>
            {showSuccessMessage && <div id="report-snackbar-success">Successfully Shared</div>}
            {showFailureMessage && <div id="report-snackbar-failure">One Or More Reports Could Not Be Shared</div>}
            <div className = 'report-list-container'>
                <Table striped borderless hover variant="light">
                    <thead>
                        <tr className='table-header'>
                            <th colSpan='5' className='report-title'>Reports</th>
                            <th colSpan='3' className='report-title'><SearchBar searchWord={searchWord} setSearchWord={setSearchWord} /></th>
                        </tr>
                    </thead>
                    <thead>
                        <tr className="report-headers">
                            <th className="checkbox-header" />
                            <th className={getClassNamesFor(sortConfig, 'projectName')} onClick={() => requestSortObject('projectName')}>Repo Name</th>
                            <th className={getClassNamesFor(sortConfig, 'start')} onClick={() => requestSortObject('start')}>Start Date</th>
                            <th className={getClassNamesFor(sortConfig, 'end')} onClick={() => requestSortObject('end')}>End Date</th>
                            <th className={getClassNamesFor(sortConfig, 'configName')} onClick={() => requestSortObject('configName')}>Configuration</th>
                            <th className={getClassNamesFor(sortConfig, 'creator')} onClick={() => requestSortObject('creator')}>Creator</th>
                            <th colSpan='2' />
                        </tr>
                    </thead>
                    <tbody>
                        {!reports || !reports.length ? (
                            <tr>
                                <td colSpan={7} >{message.NO_REPORTS}</td>
                            </tr>
                        )
                        :
                        items.filter((report)=>filterRepos(report, searchWord)).map((report, key) => (
                            <Report key={key} report={report} addReport={addReport} removeReport={removeReport} username={username} />
                        ))}
                    </tbody>
                </Table>
            </div>
            <Button variant="dark" className="report-share-btn" disabled={!areSelected} onClick={shareReports} >Share</Button>
            {show && <ShareReportModal toggleModal={closeShare} status={show} />}
        </>
    );
};

export default ReportsList;