import React, { useState } from 'react';
import { Button } from 'react-bootstrap';
import Spinner from 'react-bootstrap/Spinner';

import RepoModal from 'Components/RepoModal/RepoModal';
import { utcToLocal } from 'Components/RepoModal/Utils/getDates';
import getMembersAndAliasesFromGitLab from 'Utils/getMembersAndAliasesFromGitLab';
import getMembersAndAliasesFromDatabase from 'Utils/getMembersAndAliasesFromDatabase';
import { useUserState } from 'UserContext';

const Repo = (props) => {

    // TESTING WITH MOCK DATA
    const mockMembers = ['anne', 'billy', 'chris', 'dan', 'emily', 'fred'];
    const mockAliases = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm'];
    const mockDatabaseMapping = [ {alias:['a', 'm'], memberId:'anne'}, {alias:['f', 'l'], memberId:'fred'} ];

    const { repo } = props || {};
    // TEMPORARILY COMMENTED FOR TESTING WITH MOCK DATA
    // const [members, setMembers] = useState([]);
    // const [aliases, setAliases] = useState([]);
    // const [databaseMapping, setDatabaseMapping] = useState([]);
    const [members, setMembers] = useState(mockMembers);
    const [aliases, setAliases] = useState(mockAliases);
    const [databaseMapping, setDatabaseMapping] = useState(mockDatabaseMapping);
    const [show, setShow] = useState(false);
    const [isLoadingGitLabCall, setIsLoadingGitLabCall] = useState(false);
    const [isLoadingDatabaseCall, setIsLoadingDatabaseCall] = useState(false);
    const username = useUserState();
    
    const handleShow = () => {
        // TEMPORARILY COMMENTED FOR TESTING WITH MOCK DATA
        // setIsLoadingGitLabCall(true);
        // setIsLoadingDatabaseCall(true);
        // getMembersAndAliasesFromGitLab(username, repo.projectId).then((data) => {
        //     setMembers(data.members);
        //     setAliases(data.aliases);
        //     setIsLoadingGitLabCall(false);
        // });
        // getMembersAndAliasesFromDatabase(username, repo.projectId).then((data) => {
        //     setDatabaseMapping(data);
        //     setIsLoadingDatabaseCall(false);
        // });
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
             show && <RepoModal name={repo?.projectName} id={repo?.projectId} members={members} aliases={aliases} databaseMapping={databaseMapping} createdAt={repo?.createdAt} status={show} toggleModal={handleClose}/>}
        </tr>
    );
};

export default Repo