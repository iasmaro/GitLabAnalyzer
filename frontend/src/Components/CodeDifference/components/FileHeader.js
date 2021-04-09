import React from 'react';
import Button from 'react-bootstrap/Button';

import './FileHeader.css';
import FileHeaderModal from './FileHeaderModal';


const FileHeader = (props) => {
    const { 
        isOpen, 
        fileName, 
        linesAdded, 
        linesRemoved, 
        diffScore, 
        extension, 
        linesMoved, 
        addLine, 
        syntaxLinesAdded, 
        deleteLine, 
        configInfo, 
        spaceLinesAdded, 
        meaningfulLinesAdded,
        commentsLinesAdded, 
        handleShow, 
        handleClose, 
        show } = props || {};


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
            </div>
            <Button variant="info" onClick={handleShow} size='md' className="code-diff-score">
                <span> Score: </span>
                <span>{diffScore}</span>
            </Button>
            {show && <FileHeaderModal 
                status={show} 
                fileName={fileName} 
                toggleModal={handleClose}
                extension={extension}
                linesMoved={linesMoved}
                addLine={addLine}
                syntaxLinesAdded={syntaxLinesAdded}
                deleteLine={deleteLine}
                configInfo={configInfo}
                spaceLinesAdded={spaceLinesAdded}
                meaningfulLinesAdded={meaningfulLinesAdded}
                commentsLinesAdded={commentsLinesAdded}
                linesRemoved={linesRemoved}/>
            }
        </>         
    );
};

export default FileHeader;