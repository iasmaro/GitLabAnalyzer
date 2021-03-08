import React from 'react';
import Button from 'react-bootstrap/Button';

import './FileHeader.css';


const FileHeader = (props) => {
    const { isOpen, fileName, linesAdded, linesRemoved } = props || {};

    return (
        <>
            {isOpen? <span className="chevron chevron-bottom" /> : <span class="chevron chevron-right" />}
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
        </>         
    );
};

export default FileHeader;