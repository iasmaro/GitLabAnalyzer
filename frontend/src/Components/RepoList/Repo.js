import React, {useState} from 'react';
import {Button} from 'react-bootstrap'
import RepoModal from 'Components/RepoModal/RepoModal';

const Repo = (props) => {
    const { repo } = props || {};
    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);
    return (
        <tr>
            <td>{repo?.projectName}</td>
            <td>{repo?.updatedAt}</td>
            <td>
                <Button variant="light" onClick={handleShow}>Analyze</Button>
            </td>
            {show && <RepoModal name={repo?.projectName} members={["Member1, Member2, Member3"]} status={show} toggleModal={handleClose}/>}
        </tr>
    );
};

export default Repo