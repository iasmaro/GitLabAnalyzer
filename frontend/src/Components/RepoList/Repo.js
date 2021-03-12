import React, {useState} from 'react';
import {Button} from 'react-bootstrap'

import RepoModal from 'Components/RepoModal/RepoModal';
import getMembersAndAliases from 'Utils/getMembersAndAliases';
import { useUserState } from 'UserContext';
import { utcToLocal } from 'Components/RepoModal/Utils/getDates';


const Repo = (props) => {
    const { repo } = props || {};
    const [members, setMembers] = useState([]);
    const [aliases, setAliases] = useState([]);
    const [show, setShow] = useState(false);
    const username = useUserState();
    
    const handleShow = () => {
        // getMembersAndAliases(username, repo.projectId).then((data) => {
        //     setMembers(data.members);
        //     setAliases(data.aliases);
        // });
        // console.log(members);
        // console.log(aliases);
        
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
            {show && <RepoModal name={repo?.projectName} id={repo?.projectId} createdAt={repo?.createdAt} members={members} aliases={aliases} status={show} toggleModal={handleClose}/>}
        </tr>
    );
};

export default Repo