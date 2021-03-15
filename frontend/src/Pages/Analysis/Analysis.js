import React, { useState, useEffect } from 'react';
import { Redirect } from 'react-router-dom';
import Spinner from 'react-bootstrap/Spinner';

import { useUserState } from 'UserContext';
import AnalyzerTabs from 'Components/AnalyzerTabs/AnalyzerTabs';
import AnalysisDropDown from 'Components/AnalyzerInfo/AnalysisDropDown';
import getMergeRequests from 'Utils/getMergeRequests';
import analyzeAll from 'Utils/analyzeAll';
import getAllCommits from 'Utils/getAllCommits';
import getProjectMembers from 'Utils/getProjectMembers';

import './Analysis.css'

const Analysis = (props) => {
    const { location } = props || {};
    const { state } = location || {};
    const { data } = state || {};
    const [isLoading, setIsLoading] = useState(true);
    const [mergeRequests, setMergeRequests] = useState();
    const [commits, setCommits] = useState();
    const [members, setMembers] = useState([]);
    const [student, setStudent] = useState();
    const [analysis, setAnalysis] = useState();
    const username = useUserState();

    useEffect(() => {
        getProjectMembers(username, data.projectId).then((data) => {
            setMembers(data);
        });
    }, [username, data.projectId]);

    useEffect(() => {
        if (members.length > 0 && !analysis) {
            setStudent(members[0]);
            setAnalysis(analyzeAll(members, username, data.projectId));
        }
    }, [analysis, data.projectId, members, username]);

    useEffect(() => {
        const activeAnalysis = analysis && analysis[student];
        activeAnalysis?.mergeRequests.then((data) => {
            setMergeRequests(data);
            setIsLoading(false);
        });
        activeAnalysis?.commits.then((data) => {
            setCommits(data);
        });
    }, [student, analysis]);
    
    if (!data) {
        return(<Redirect to={{ pathname: '/' }} />);
    }

    return (
        <div className="analysis-page">
            <div className="analysis-header">
                <AnalysisDropDown members={members} student={student} setStudent={setStudent} data={data} setIsLoading={setIsLoading} />
            </div>
            {isLoading ? <Spinner animation="border" className="spinner" /> : <AnalyzerTabs mergerequests={mergeRequests} projectId={data.projectId} commits={commits} />}
        </div>
    )
}

export default Analysis;