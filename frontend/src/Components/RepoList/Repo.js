import React, {useState} from 'react';
import RepoModal from './RepoModal';

const Repo = (props) => {

    const [show, setShow] = useState(false);
    
    const handleShow = () => setShow(true);
    const handleHide = () => setShow(false);

    return (
        <tr>
            <td>{props.repo.name}</td>
            <td>{props.repo.date}</td>
            <button onClick={handleShow}>Analyze</button>
            {show && <RepoModal status={show} toggleModal={handleHide}/>}
        </tr>
    );
};

export default Repo