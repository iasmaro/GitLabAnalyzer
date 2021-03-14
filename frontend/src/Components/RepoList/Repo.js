import React, {useState} from 'react';
import {Button} from 'react-bootstrap'

import RepoModal from 'Components/RepoModal/RepoModal';
import { useUserState } from 'UserContext';
import { utcToLocal } from 'Components/RepoModal/Utils/getDates';
import getConfigurations from 'Utils/getConfigurations';


const Repo = (props) => {
    const { repo } = props || {};
    const [configs, setConfigs] = useState([]);
    const [show, setShow] = useState(false);
    const username = useUserState();
    
    const handleShow = () => {
        getConfigurations(username).then((data) => {
            setConfigs(data);
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
            {show && <RepoModal name={repo?.projectName} id={repo?.projectId} createdAt={repo?.createdAt} configs={configs} status={show} toggleModal={handleClose}/>}
        </tr>
    );
};

export default Repo