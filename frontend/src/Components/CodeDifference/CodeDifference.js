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
    const { diff, view, collapseAll, expandAll, configInfo } = props || {};
    const [isActive, setIsActive] = useState('1');

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
    const diffScore = diff.diffScore || 0;
    const extension = diff.extension;
    const linesMoved = diff.linesMoved || 0;
    const addLine = diff.linesAdded || 0;
    const syntaxLinesAdded = diff.syntaxLinesAdded || 0;
    const deleteLine = diff.linesRemoved || 0;
    const spaceLinesAdded = diff.spaceLinesAdded || 0;

    const language = getLanguageFromFile(fileName);

    const files = parseDiff(diffText, {nearbySequences: 'zip'});
    const options = {
        highlight: true,
        refractor: refractor,
        language
    };

    const handleClick = () => {
        if (isActive === '1') {
            setIsActive('0');
        } else {
            setIsActive('1');
        }
    };

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
                            diffScore={diffScore} 
                            extension={extension}
                            linesMoved={linesMoved}
                            addLine={addLine}
                            syntaxLinesAdded={syntaxLinesAdded}
                            deleteLine={deleteLine}
                            configInfo={configInfo}
                            spaceLinesAdded={spaceLinesAdded}
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