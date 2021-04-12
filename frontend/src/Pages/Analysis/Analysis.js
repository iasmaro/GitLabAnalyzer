import React, { useState, useEffect } from 'react';
import { Redirect } from 'react-router-dom';
import Spinner from 'react-bootstrap/Spinner';

import { useUserState } from 'UserContext';
import getReportDetails from 'Utils/getReportDetails';
import AnalyzerTabs from 'Components/AnalyzerTabs/AnalyzerTabs';
import AnalysisDropDown from 'Components/AnalyzerInfo/AnalysisDropDown';
import AnalysisSpecifications from 'Components/AnalyzerInfo/AnalysisSpecifications';
import analyzeAll from 'Utils/analyzeAll';
import getProjectMembers from 'Utils/getProjectMembers';
import getConfigurationInfo from 'Utils/getConfigurationInfo';
import getMembersAndAliasesFromDatabase from 'Utils/getMembersAndAliasesFromDatabase';

import './Analysis.css';

const Analysis = (props) => {
    const { location } = props || {};
    const { state } = location || {};
    const { data } = state || {};
    const { projectId, configuration, startDate, endDate, namespace, projectName, reportName, creator } = data || {};

    const [isLoading, setIsLoading] = useState(true);
    const [mergeRequests, setMergeRequests] = useState();
    const [commitsGraph, setCommitsGraph] = useState();
    const [MRsGraph, setMRsGraph] = useState();
    const [codeReviewsGraph, setCodeReviewsGraph] = useState();
    const [issueCommentsGraph, setIssueCommentsGraph] = useState();
    const [commits, setCommits] = useState();
    const [issueComments, setIssueComments] = useState();
    const [mergeRequestComments, setMergeRequestComments] = useState();
    const [members, setMembers] = useState([]);
    const [student, setStudent] = useState();
    const [analysis, setAnalysis] = useState();
    const [configInfo, setConfigInfo] = useState();
    const [databaseMembersAndAliases, setDatabaseMembersAndAliases] = useState([]);
    const [diffs, setDiffs] = useState();
    const [activeCommits, setActiveCommits] = useState();
    const username = useUserState();

    useEffect(() => {
        getProjectMembers(creator || username, projectId).then((data) => {
            setMembers(data);
            setStudent(data && data[0]);
        });
        getMembersAndAliasesFromDatabase(creator || username, projectId).then((data) => {
            setDatabaseMembersAndAliases(data);
        });
    }, [username, projectId, creator]);

    useEffect(() => {
        if (reportName) {
            getReportDetails(reportName).then((data) => {
                setAnalysis(data);
                setIsLoading(false);
                if (!members && data) {
                    setMembers(Object.keys(data.mergeRequestListByMemberId));
                    setStudent(Object.keys(data.mergeRequestListByMemberId)[0])
                }
            });
        } else {
            analyzeAll(username, projectId).then((data) => {
                setAnalysis(data);
                setIsLoading(false);
            });
        }
    }, [projectId, username, reportName, members]);

    useEffect(() => {
        if (analysis && student) {
            setMergeRequests(analysis.mergeRequestListByMemberId && analysis.mergeRequestListByMemberId[student]);
            setCommits(analysis.commitListByMemberId && analysis.commitListByMemberId[student]);
            setCommitsGraph(analysis.commitGraphListByMemberId && analysis.commitGraphListByMemberId[student]);
            setMRsGraph(analysis.mrgraphListByMemberId && analysis.mrgraphListByMemberId[student]);
            setCodeReviewsGraph(analysis.codeReviewGraphListByMemberId && analysis.codeReviewGraphListByMemberId[student]);
            setIssueCommentsGraph(analysis.issueGraphListByMemberId && analysis.issueGraphListByMemberId[student]);
            setIssueComments(analysis.issueCommentListByMemberId && analysis.issueCommentListByMemberId[student]);
            setMergeRequestComments(analysis.mrcommentListByMemberId && analysis.mrcommentListByMemberId[student]);
        }
    }, [student, analysis]);

    useEffect(() => {
        getConfigurationInfo(creator || username, configuration).then((data) => {
            setConfigInfo(data);
        });
    },[username, configuration, creator]);

    if (!data) {
        return(<Redirect to={{ pathname: '/' }} />);
    }

    return (
        <div className="analysis-page">
            <AnalysisSpecifications startDate={startDate} endDate={endDate} configuration={configuration} namespace={namespace || analysis?.namespace} projectName={projectName} />
            {isLoading ? <Spinner animation="border" className="spinner" /> : 
            <>
                <div className="analysis-header">
                    <AnalysisDropDown members={members} student={student} setStudent={setStudent} data={data} setDiffs={setDiffs} setActiveCommits={setActiveCommits}/>
                </div>
                <AnalyzerTabs
                    activeCommits={activeCommits}
                    setActiveCommits={setActiveCommits}
                    diffs={diffs}
                    setDiffs={setDiffs}
                    mergerequests={mergeRequests}
                    projectId={projectId}
                    commits={commits}
                    commitsGraph={commitsGraph}
                    MRsGraph={MRsGraph}
                    codeReviewsGraph={codeReviewsGraph}
                    issueCommentsGraph={issueCommentsGraph}
                    student={student}
                    databaseMembersAndAliases={databaseMembersAndAliases}
                    issueComments={issueComments}
                    mergeRequestComments={mergeRequestComments}
                    configInfo={configInfo}/>
            </>}
        </div>
    )
}

export default Analysis;