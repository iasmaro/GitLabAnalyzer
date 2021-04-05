import React, { useState, useEffect } from 'react';
import { Redirect } from 'react-router-dom';
import Spinner from 'react-bootstrap/Spinner';

import { useUserState } from 'UserContext';
import AnalyzerTabs from 'Components/AnalyzerTabs/AnalyzerTabs';
import AnalysisDropDown from 'Components/AnalyzerInfo/AnalysisDropDown';
import AnalysisSpecifications from 'Components/AnalyzerInfo/AnalysisSpecifications';
import analyzeAll from 'Utils/analyzeAll';
import getProjectMembers from 'Utils/getProjectMembers';

import './Analysis.css'

const Analysis = (props) => {
    const { location } = props || {};
    const { state } = location || {};
    const { data } = state || {};
    const { projectId, configuration, startDate, endDate, namespace, projectName } = data || {};
    const [isLoading, setIsLoading] = useState(true);
    const [mergeRequests, setMergeRequests] = useState();
    const [commits, setCommits] = useState();
    const [issueComments, setIssueComments] = useState();
    const [mergeRequestComments, setmergeRequestComments] = useState();
    const [members, setMembers] = useState([]);
    const [student, setStudent] = useState();
    const [analysis, setAnalysis] = useState();
    const username = useUserState();

    useEffect(() => {
        getProjectMembers(username, projectId).then((data) => {
            setMembers(data);
        });
    }, [username, projectId]);

    useEffect(() => {
        if (members.length > 0 && !analysis) {
            setStudent(members[0]);
            setAnalysis(analyzeAll(members, username, projectId));
        }
    }, [analysis, projectId, members, username]);

    useEffect(() => {
        const activeAnalysis = analysis && analysis[student];
        activeAnalysis?.mergeRequests.then((data) => {
            setMergeRequests(data);
            setIsLoading(false);
        });
        activeAnalysis?.commits.then((data) => {
            setCommits(data);
        });
        activeAnalysis?.issueComments.then((data) => {
            setIssueComments(data);
        });
        activeAnalysis?.mergeRequestComments.then((data) => {
            setmergeRequestComments(data);
        });
    }, [student, analysis]);
    
    if (!data) {
        return(<Redirect to={{ pathname: '/' }} />);
    }

    return (
        <div className="analysis-page">
            <AnalysisSpecifications startDate={startDate} endDate={endDate} configuration={configuration} namespace={namespace} projectName={projectName} />
            <div className="analysis-header">
                <AnalysisDropDown members={members} student={student} setStudent={setStudent} data={data} setIsLoading={setIsLoading} />
            </div>
            {isLoading ? <Spinner animation="border" className="spinner" /> : <AnalyzerTabs mergerequests={mergeRequests} projectId={projectId} commits={commits} issueComments={issueComments} mergeRequestComments={mergeRequestComments}/>}
        </div>
    )
}

export default Analysis;