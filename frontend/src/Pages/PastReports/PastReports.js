import React, { useState, useEffect } from 'react';
import Spinner from 'react-bootstrap/Spinner';

import { useUserState } from 'UserContext';
import ReportsList from 'Components/ReportsList/ReportsList';
import getReports from 'Utils/getReports';

import './PastReports.css';

const PastReports = () => {
    const [repos, setRepos] = useState();
    const [isLoading, setIsLoading] = useState(true);
    const username = useUserState();
    useEffect(() => {
        getReports(username).then((data) => {
            setRepos(data);
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