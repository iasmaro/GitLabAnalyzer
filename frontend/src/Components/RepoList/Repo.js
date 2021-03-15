import React, { useState } from 'react';
import { Button, Spinner } from 'react-bootstrap';

import RepoModal from 'Components/RepoModal/RepoModal';
import { utcToLocal } from 'Components/RepoModal/getDates';
import getMembersAndAliasesFromGitLab from 'Utils/getMembersAndAliasesFromGitLab';
import getMembersAndAliasesFromDatabase from 'Utils/getMembersAndAliasesFromDatabase';
import getConfigurations from 'Utils/getConfigurations';
import { useUserState } from 'UserContext';

const Repo = (props) => {
    const { repo } = props || {};
    const [configs, setConfigs] = useState([]);
    const [show, setShow] = useState(false);
    const [isLoadingGitLabCall, setIsLoadingGitLabCall] = useState(false);
    const [isLoadingDatabaseCall, setIsLoadingDatabaseCall] = useState(false);
    const [members, setMembers] = useState([]);
    const [aliases, setAliases] = useState([]);
    const [databaseMapping, setDatabaseMapping] = useState([]);
    const username = useUserState();
    
    const handleShow = () => {
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
        getConfigurations(username).then((data) => {
            setConfigs(data);
        });
        setShow(true);
    }

    const handleClose = () => setShow(false);

    return (
        <tr>
            <td>{repo?.projectName}</td>
            <td>{utcToLocal(repo?.updatedAt).toString()}</td>
            <td>
                <Button variant="dark" onClick={handleShow}> Analyze </Button>
            </td>
            {(isLoadingGitLabCall || isLoadingDatabaseCall) ? <Spinner animation="border" className="spinner" /> :
             show && <RepoModal name={repo?.projectName} id={repo?.projectId} members={members} aliases={aliases} databaseMapping={databaseMapping} createdAt={repo?.createdAt} configs={configs} status={show} toggleModal={handleClose}/>}
        </tr>
    );
};

export default Repo;