import React, {useState} from 'react';
import {Button} from 'react-bootstrap'

import RepoModal from 'Components/RepoModal/RepoModal';
import getProjectMembers from 'Utils/getProjectMembers';
import { useUserState } from 'UserContext';
import { utcToLocal } from 'Components/RepoModal/Utils/getDates';


const Repo = (props) => {
    const { repo } = props || {};
    const [members, setMembers] = useState([]);
    const [show, setShow] = useState(false);
    const username = useUserState();
    
    const handleShow = () => {
        getProjectMembers(username, repo.projectId).then((data) => {
            setMembers(data);
            setShow(true);
        });
    }

    const handleClose = () => setShow(false);

    return (
        <tr>
            <td>{repo?.projectName}</td>
            <td>{utcToLocal(repo?.updatedAt).toString()}</td>
            <td>
                <Button variant="dark" onClick={handleShow}> Analyze </Button>
            </td>
            {show && <RepoModal name={repo?.projectName} createdAt={repo?.createdAt} members={members} status={show} toggleModal={handleClose}/>}
        </tr>
    );
};

export default Repo