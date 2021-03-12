import React, {useState} from 'react';
import {Button} from 'react-bootstrap';
import Spinner from 'react-bootstrap/Spinner';

import RepoModal from 'Components/RepoModal/RepoModal';
import getMembersAndAliases from 'Utils/getMembersAndAliases';
import { useUserState } from 'UserContext';
import { utcToLocal } from 'Components/RepoModal/Utils/getDates';

const Repo = (props) => {
    const { repo } = props || {};
    const [members, setMembers] = useState([]);
    const [aliases, setAliases] = useState([]);
    const [show, setShow] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const username = useUserState();
    
    const handleShow = () => {
        setIsLoading(true);
        getMembersAndAliases(username, repo.projectId).then((data) => {
            setMembers(data.members);
            setAliases(data.aliases);
            setIsLoading(false);
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
            {isLoading ? <Spinner animation="border" className="spinner" /> : show && <RepoModal name={repo?.projectName} id={repo?.projectId} members={members} aliases={aliases} createdAt={repo?.createdAt} status={show} toggleModal={handleClose}/>}
        </tr>
    );
};

export default Repo