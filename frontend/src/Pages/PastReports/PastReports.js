import React, { useState, useEffect } from 'react';
import Spinner from 'react-bootstrap/Spinner';

import { useUserState } from 'UserContext';
import ReportsList from 'Components/ReportsList/ReportsList';
import getRepos from 'Utils/getRepos';
import { reports } from 'Mocks/mockReports';

import './PastReports.css';

const PastReports = () => {
    const [repos, setRepos] = useState();
    const [isLoading, setIsLoading] = useState(true);
    const username = useUserState();
    useEffect(() => {
        getRepos(username).then((data) => {
            setRepos(reports);
            setIsLoading(false);
        });
    }, [username]);

    return (
        <div className='past-reports'>
            {isLoading ? <Spinner animation="border" className="spinner" /> : <ReportsList reports={repos}/>}
        </div>
    );
}

export default PastReports;