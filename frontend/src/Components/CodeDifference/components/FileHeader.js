import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';

import './FileHeader.css';
import FileHeaderModal from './FileHeaderModal';


const FileHeader = (props) => {
    const { isOpen, fileName, linesAdded, linesRemoved, diffScore } = props || {};

    const[showScore, setShowScore] = useState(false);

    const handleShow = () => setShow(true);
    const handleClose = () => {
        setShow(false);
        setTimeout(() => {
            setShowScore(!showScore);
        }, 200);
    }
    const [show, setShow] = useState(false);

    return (
        <>
            {isOpen ? <span className="chevron chevron-bottom" /> : <span class="chevron chevron-right" />}
            <Button variant="link" className="file-title">
                <b>{fileName}</b>
            </Button>
            <div className="diff-stats">
                <div className="lines-added">
                    <span>+{linesAdded}</span>
                </div>
                <div className="lines-removed">
                    <span>-{linesRemoved}</span>
                </div>
                <div className="file-score">
                    <Button variant="info" onClick={handleShow} size='sm' className="code-diff-score">
                        <span> Score: </span>
                        <span>{diffScore}</span>
                    </Button>
                </div>
                {show && <FileHeaderModal status={show} fileName={fileName} toggleModal={handleClose} />}
            </div>
        </>         
    );
};

export default FileHeader;