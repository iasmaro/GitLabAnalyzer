import React, { useState, useEffect } from 'react';
import Accordion from 'react-bootstrap/Accordion';
import Card from 'react-bootstrap/Card';
import { parseDiff, Diff, tokenize } from 'react-diff-view';
import 'react-diff-view/style/index.css';
import refractor from 'refractor';

import getLanguageFromFile from 'Utils/getLanguageFromFile';

import FileHeader from './components/FileHeader';
import './CodeDifference.css';
import './syntaxHighlighter/prism.css';


const CodeDifference = (props) => {
    const { diff, view, collapseAll, expandAll, configInfo, changeCommitScore, index } = props || {};
    const [isActive, setIsActive] = useState('1');
    const [show, setShow] = useState(false);
    const [score, setScore] = useState(0);
    const [showScoreModal, setShowScoreModal] = useState(false);

    useEffect(() => {
        if (diff) {
            const modifiedScore = diff.scoreDTO?.modifiedScore !== -1 ? diff.scoreDTO?.modifiedScore : null;
            const diffScore = modifiedScore || diff.scoreDTO?.score || 0;
            setScore(diffScore);
        }
    }, [diff]);

    useEffect(() => {
        if (collapseAll) {
            setIsActive('0');
        }
    }, [collapseAll]);
    
    useEffect(() => {
        if (expandAll) {
            setIsActive('1');
        }
    }, [expandAll]);

    if (!diff || !diff.newPath || !diff.codeDiff) {
        return null;
    }
    const diffText = `--- ${diff.oldPth}\n+++ ${diff.newPath}\n${diff.codeDiff}`;

    const linesAdded = diff.codeDiff.match(/\n\+/g)?.length || 0;
    const linesRemoved = diff.codeDiff.match(/\n-/g)?.length || 0;
    const fileName = diff.newPath;
    const diffScore =  diff.scoreDTO?.score || 0;
    const updatedScore = score !== diffScore;
    const extension = diff.extension;
    const linesMoved = diff.scoreDTO?.linesMoved || 0;
    const syntaxLinesAdded = diff.scoreDTO?.syntaxLinesAdded || 0;
    const spaceLinesAdded = diff.scoreDTO?.spaceLinesAdded || 0;
    const meaningfullLinesAdded = diff.scoreDTO?.meaningFullLinesAdded || 0;
    const meaningfullLinesRemoved = diff.scoreDTO?.meaningFullLinesRemoved;
    const commentsLinesAdded = diff.scoreDTO?.commentLinesAdded || 0;

    const language = getLanguageFromFile(fileName);

    const files = parseDiff(diffText, {nearbySequences: 'zip'});
    const options = {
        highlight: true,
        refractor: refractor,
        language
    };

    const handleClick = () => {
        if (!show && !showScoreModal){
            if (isActive === '1') {
                setIsActive('0');
            } else {
                setIsActive('1');
            }
        }
    };
    
    const handleShow = () => setShow(true);

    const handleClose = () => {
        setShow(false);
        setShowScoreModal(false);
    };

    const handleShowScoreModal = () => setShowScoreModal(true);

    const changeScore = (newScore) => {
        if (changeCommitScore) {
            changeCommitScore(newScore - score, index);
        }
        setScore(newScore);
        setShowScoreModal(false);
    }

    const isOpen = isActive === '1';
    return (
        <Accordion defaultActiveKey="1" activeKey={isActive} >
            <Card>
                <Card.Header>
                    <Accordion.Toggle as="div" eventKey="1" onClick={handleClick} className="diff-toggle">
                        <FileHeader 
                            isOpen={isOpen} 
                            linesAdded={linesAdded} 
                            linesRemoved={linesRemoved} 
                            fileName={fileName} 
                            diffScore={score}
                            updatedScore={updatedScore}
                            extension={extension}
                            linesMoved={linesMoved}
                            addLine={linesAdded}
                            syntaxLinesAdded={syntaxLinesAdded}
                            deleteLine={meaningfullLinesRemoved}
                            configInfo={configInfo}
                            spaceLinesAdded={spaceLinesAdded}
                            meaningfulLinesAdded={meaningfullLinesAdded}
                            commentsLinesAdded = {commentsLinesAdded}
                            handleShow={handleShow}
                            handleClose={handleClose}
                            handleShowScoreModal={handleShowScoreModal}
                            changeScore={changeScore}
                            show={show}
                            showScoreModal={showScoreModal}
                        />
                    </Accordion.Toggle>
                </Card.Header>
                <Accordion.Collapse eventKey="1">
                    <Card.Body>
                        {files.map(({hunks, type}, i) => <Diff key={i} hunks={hunks || []} diffType={type} viewType={view || 'unified'} tokens={tokenize(hunks, options)} />)}
                    </Card.Body>
                </Accordion.Collapse>
            </Card>
        </Accordion>
    );
};

export default CodeDifference;