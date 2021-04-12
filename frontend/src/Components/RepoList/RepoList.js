import React, { useState } from 'react';
import { Table, Badge, Tooltip, OverlayTrigger, Button, Spinner, Alert } from 'react-bootstrap';
import { FaInfo } from 'react-icons/fa';

import { message } from 'Constants/constants';
import { useSortableDataObject, getClassNamesFor } from 'Utils/sortTables';
import filterRepos from 'Utils/filterRepos';
import getConfigurations from 'Utils/getConfigurations';
import { useUserState } from 'UserContext';
import getEndDate from 'Utils/getEndDate';
import getStartDate from 'Utils/getStartDate';
import BatchRepoAnalyzeModal from 'Components/RepoAnalyzeModal/BatchRepoAnalyzeModal';

import Repo from './Repo';
import SearchBar from 'Components/SearchBar/SearchBar';
import './RepoList.css';

const RepoList = (props) => {
    const { repos } = props || {};
    const [searchWord, setSearchWord] = useState('');
    const { items, requestSortObject, sortConfig  } = useSortableDataObject(repos);
    const namespaceTooltip = <Tooltip>Namespace refers to the user name, group name, or subgroup name associated with the repository.</Tooltip>;
    const batchAnalyzeTooltip = <Tooltip>Select repos to batch process and check the 'Past Reports' page for processed reports.</Tooltip>;
    const [reposBatch, setReposBatch] = useState(new Set());
    const [batchAnalyzeDisabled, setBatchAnalyzeDisabled] = useState(true);
    const [isLoadingStartDate, setIsLoadingStartDate] = useState(false);
    const [isLoadingEndDate, setIsLoadingEndDate] = useState(false);    
    const [configs, setConfigs] = useState([]);
    const [startDate, setStartDate] = useState();
    const [endDate, setEndDate] = useState();
    const [showAnalyzeModal, setShowAnalyzeModal] = useState(false);
    const [uncheck, setUncheck] = useState(0);
    const [showAlertMessage, setShowAlertMessage] = useState(false);
    const username = useUserState();

    const addRepo = (repo) => {
        reposBatch.add(repo.projectId);
        reposBatch.size > 0 ? setBatchAnalyzeDisabled(false) : setBatchAnalyzeDisabled(true);
        setReposBatch(reposBatch);
    }

    const removeRepo = (repo) => {
        reposBatch.delete(repo.projectId);
        reposBatch.size > 0 ? setBatchAnalyzeDisabled(false) : setBatchAnalyzeDisabled(true);
        setReposBatch(reposBatch);
    }

    const handleShowBatchRepoAnalyzeModal = () => {
        setIsLoadingStartDate(true);
        setIsLoadingEndDate(true);
        getConfigurations(username).then((data) => {
            setConfigs(data);
        });
        getStartDate(username).then((data) => {
            setStartDate(data);
            setIsLoadingStartDate(false);
        });
        getEndDate(username).then((data) => {
            setEndDate(data);
            setIsLoadingEndDate(false);
        });
        setShowAnalyzeModal(true);
    }
    
    const handleCloseAnalyzeModal = () => {
        setShowAnalyzeModal(false);
        setUncheck(uncheck + 1);
        setReposBatch(new Set());
        setBatchAnalyzeDisabled(true);
    }

    const showAlert = () => {
        setShowAlertMessage(true);
        setTimeout(() => {
            setShowAlertMessage(false);;
        }, 4000);
    }

    return (
        <div className = 'list-container'>
            <Alert show={showAlertMessage} variant='warning'>
                !! Check 'Past Reports' page to access batch processed reports !! 
            </Alert>
            <Table striped borderless hover variant='light'>
                <thead>
                    <tr className='table-header'>
                        <th colSpan='3' className='repoTitle'>Repositories</th>
                        <th colSpan='3' className='repoTitle'><SearchBar searchWord={searchWord} setSearchWord={setSearchWord} /></th>
                    </tr>
                </thead>
                <thead>
                    <tr className="repo-headers">
                        <th>
                            <Button variant="dark" className='batch-process-button' disabled={batchAnalyzeDisabled} onClick={() => handleShowBatchRepoAnalyzeModal()}>Batch Process</Button> {' '}
                            <OverlayTrigger placement='right' overlay={batchAnalyzeTooltip}>
                                <Badge variant="dark">
                                    <FaInfo/>
                                </Badge>
                            </OverlayTrigger>
                            { (isLoadingStartDate || isLoadingEndDate) ? <Spinner animation="border" className="spinner-repo-list" /> :
                            showAnalyzeModal && <BatchRepoAnalyzeModal                                        
                                                        configs={configs} 
                                                        status={showAnalyzeModal} 
                                                        toggleModal={handleCloseAnalyzeModal} 
                                                        start={startDate} 
                                                        end={endDate}
                                                        reposBatch={reposBatch} 
                                                        showAlert={showAlert}/>}
                        </th>
                        <th className={getClassNamesFor(sortConfig, 'projectName')} onClick={() => requestSortObject('projectName')}>Name</th>
                        <th className={getClassNamesFor(sortConfig, 'namespace')} onClick={() => requestSortObject('namespace')}>Namespace {' '}
                            <OverlayTrigger placement='right' overlay={namespaceTooltip}>
                                <Badge variant="dark">
                                    <FaInfo/>
                                </Badge>
                            </OverlayTrigger>
                        </th>
                        <th className={getClassNamesFor(sortConfig, 'updatedAt')} onClick={() => requestSortObject('updatedAt')}>Last Modified</th>
                        <th colSpan='2'></th>
                    </tr>
                </thead>
                <tbody>
                    {!repos ? (
                        <tr>
                            <td colSpan={4} >{message.TOKEN_NOT_SET}</td>
                        </tr>
                    )
                    : !repos.length ? (
                        <tr>
                            <td colSpan={4} >{message.NO_REPOS}</td>
                        </tr>
                    )
                    :
                    items.filter((repo)=>filterRepos(repo, searchWord)).map((repo) => (
                        <Repo key={repo?.projectId} repo={repo} addRepo={addRepo} removeRepo={removeRepo} uncheck={uncheck} />
                    ))}
                </tbody>
            </Table>
        </div>
    );
};

export default RepoList;