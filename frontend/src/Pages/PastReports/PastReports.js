import React, { useState, useEffect } from 'react';
import Spinner from 'react-bootstrap/Spinner';

import { useUserState } from 'UserContext';
import ReportsList from 'Components/ReportsList/ReportsList';
import getReports from 'Utils/getReports';

import './PastReports.css';

const PastReports = () => {
    const [reports, setReports] = useState();
    const [isLoading, setIsLoading] = useState(true);
    const username = useUserState();
    useEffect(() => {
        getReports(username).then((data) => {
            setReports(data);
            setIsLoading(false);
        });
    }, [username]);

    return (
        <div className='past-reports'>
            {isLoading ? <Spinner animation="border" className="spinner" /> : <ReportsList reports={reports}/>}
        </div>
    );
}

export default PastReports;