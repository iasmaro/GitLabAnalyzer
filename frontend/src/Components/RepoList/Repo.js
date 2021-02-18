import React, {useState} from 'react';
import RepoModal from '../RepoModal/RepoModal';

const Repo = (props) => {

    const [show, setShow] = useState(false);

    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    return (
        <tr>
            <td>{props.repo.name}</td>
            <td>{props.repo.date}</td>
            <button onClick={handleShow}>Analyze</button>
            {show && <RepoModal name={props.repo.name} members={props.repo.members} status={show} toggleModal={handleClose}/>}
        </tr>
    );
};

export default Repo