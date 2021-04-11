import React, { useState } from 'react';
import { Button, Spinner } from 'react-bootstrap';
import Checkbox from '@material-ui/core/Checkbox';

import RepoAnalyzeModal from 'Components/RepoAnalyzeModal/RepoAnalyzeModal';
import RepoMapAliasModal from 'Components/RepoMapAliasModal/RepoMapAliasModal';
import { utcToLocal } from 'Components/Utils/formatDates';
import getMembersAndAliasesFromGitLab from 'Utils/getMembersAndAliasesFromGitLab';
import getMembersAndAliasesFromDatabase from 'Utils/getMembersAndAliasesFromDatabase';
import getConfigurations from 'Utils/getConfigurations';
import { useUserState } from 'UserContext';
import getEndDate from 'Utils/getEndDate';
import getStartDate from 'Utils/getStartDate';

import './RepoList.css';

const Repo = (props) => {
    const { repo, handleCheckboxChange } = props || {};
    const [configs, setConfigs] = useState([]);
    const [showAnalyzeModal, setShowAnalyzeModal] = useState(false);
    const [showAliasMappingModal, setShowAliasMappingModal] = useState(false);
    const [isLoadingGitLabCall, setIsLoadingGitLabCall] = useState(false);
    const [isLoadingDatabaseCall, setIsLoadingDatabaseCall] = useState(false);
    const [isLoadingStartDate, setIsLoadingStartDate] = useState(false);
    const [isLoadingEndDate, setIsLoadingEndDate] = useState(false);
    const [members, setMembers] = useState([]);
    const [aliases, setAliases] = useState([]);
    const [databaseMapping, setDatabaseMapping] = useState([]);
    const [startDate, setStartDate] = useState();
    const [endDate, setEndDate] = useState();
    const username = useUserState();
    const [checked, setChecked] = useState(false);
    
    const handleShowAnalyzeModal = () => {
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

    const handleShowAliasMappingModal = () => {
        setIsLoadingGitLabCall(true);
        setIsLoadingDatabaseCall(true);
        getMembersAndAliasesFromGitLab(username, repo.projectId).then((data) => {
            setMembers(data.members);
            setAliases(data.aliases);
            setIsLoadingGitLabCall(false);
        });
        getMembersAndAliasesFromDatabase(username, repo.projectId).then((data) => {
            setDatabaseMapping(data);
            setIsLoadingDatabaseCall(false);
        });
        setShowAliasMappingModal(true);
    }

    const handleCloseAnalyzeModal = () => setShowAnalyzeModal(false);

    const handleCloseAliasMappingModal = () => setShowAliasMappingModal(false);

    return (
        <tr>
            <td>
                <Checkbox
                    checked={checked}
                    color='default'
                    onClick={() => handleCheckboxChange && handleCheckboxChange(repo, checked, setChecked)}
                />
            </td>
            <td>{repo?.projectName}</td>
            <td>{repo?.namespace}</td>
            <td>{utcToLocal(repo?.updatedAt)}</td>
            <td>
                <Button variant="outline-dark" className='repo-list-button' onClick={handleShowAliasMappingModal}> Map Aliases </Button>
                { (isLoadingGitLabCall || isLoadingDatabaseCall) ? <Spinner animation="border" className="spinner" /> :
                showAliasMappingModal && <RepoMapAliasModal 
                                            name={repo?.projectName} 
                                            members={members} 
                                            aliases={aliases} 
                                            databaseMapping={databaseMapping} 
                                            status={showAliasMappingModal} 
                                            toggleModal={handleCloseAliasMappingModal} />}
            </td>
            <td>
                <Button variant="dark" className='repo-list-button' onClick={handleShowAnalyzeModal}> Analyze </Button>
                { (isLoadingStartDate || isLoadingEndDate) ? <Spinner animation="border" className="spinner" /> :
                showAnalyzeModal && <RepoAnalyzeModal 
                                            name={repo?.projectName} 
                                            id={repo?.projectId}                                         
                                            configs={configs} 
                                            status={showAnalyzeModal} 
                                            toggleModal={handleCloseAnalyzeModal} 
                                            start={startDate} 
                                            end={endDate}
                                            namespace={repo?.namespace} />}
            </td>
        </tr>
    );
};

export default Repo;