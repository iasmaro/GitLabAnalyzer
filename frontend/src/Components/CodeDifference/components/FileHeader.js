import React from 'react';
import Button from 'react-bootstrap/Button';
import { FiEdit } from 'react-icons/fi';

import FileHeaderModal from './FileHeaderModal';
import ScoreChangeModal from './ScoreChangeModal'
import './FileHeader.css';


const FileHeader = (props) => {
    const { 
        isOpen, 
        fileName, 
        linesAdded,
        updatedScore,
        linesRemoved, 
        diffScore,
        handleShow,
        showScoreModal,
        handleShowScoreModal,
        show } = props || {};

    
    
    const scoreClass= updatedScore ? 'code-diff-score-updated' : 'code-diff-score';

    return (
        <>
            {isOpen ? <span className="chevron chevron-bottom" /> : <span class="chevron chevron-right" />}
            <Button variant="link" className="file-title">
                <b>{fileName}</b>
            </Button>
            <div className="diff-stats">
                <Button variant="white" onClick={handleShowScoreModal} ><FiEdit /></Button>
                <div className="lines-added">
                    <span>+{linesAdded}</span>
                </div>
                <div className="lines-removed">
                    <span>-{linesRemoved}</span>
                </div>
            </div>
            <Button variant="white" onClick={handleShow} className={scoreClass}>
                <span>{updatedScore? '*' : ''}Score: </span>
                <span>{diffScore}</span>
            </Button>
            {show && <FileHeaderModal {...props}/>}
            {showScoreModal && <ScoreChangeModal {...props} status={showScoreModal} />}
        </>         
    );
};

export default FileHeader;