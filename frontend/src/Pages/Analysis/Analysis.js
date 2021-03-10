import React, { useState, useEffect } from 'react';
import { Redirect } from 'react-router-dom';
import Spinner from 'react-bootstrap/Spinner';

import { useUserState } from 'UserContext';
import AnalyzerTabs from 'Components/AnalyzerTabs/AnalyzerTabs';
import AnalyzerInfo from 'Components/AnalyzerInfo/AnalyzerInfo';
import getMergeRequests from 'Utils/getMergeRequests';
import getAllCommits from 'Utils/getAllCommits';

import './Analysis.css'

const Analysis = (props) => {
    const { location } = props || {};
    const { state } = location || {};
    const { data } = state || {};
    const [isLoading, setIsLoading] = useState(true);
    const [mergeRequests, setMergeRequests] = useState();
    const [commits, setCommits] = useState();
    const username = useUserState();
    useEffect(() => {
        getMergeRequests(username, data.memberId, data.start, data.end, data.projectId).then((data) => {
            setMergeRequests(data);
            setIsLoading(false);
        });
        getAllCommits(username, data.memberId, data.start, data.end, data.projectId).then((data) => {
            setCommits(data);
        });
    }, [username, data]);
    
    if (!data) {
        return(<Redirect to={{ pathname: '/' }} />);
    }

    return (
        <div className="analysis-page">
            <AnalyzerInfo memberId={data.memberId} projectId={data.projectId} startDate={data.start} endDate={data.end} configuration={data.configuration}/>
            {isLoading ? <Spinner animation="border" className="spinner" /> : <AnalyzerTabs mergerequests={mergeRequests} projectId={data.projectId} commits={commits} />}
        </div>
    )
}

export default Analysis;

