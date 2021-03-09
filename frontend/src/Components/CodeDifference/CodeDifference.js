import React, {useState, useEffect} from 'react';
import Accordion from 'react-bootstrap/Accordion';
import Card from 'react-bootstrap/Card';
import { parseDiff, Diff, tokenize } from 'react-diff-view';
import 'react-diff-view/style/index.css';
import refractor from 'refractor';

import getLanguageFromFile from 'Utils/getLanguageFromFile';

import FileHeader from './components/FileHeader';
import './CodeDifference.css';
import './prism.css';


const CodeDifference = (props) => {
    const { diff, view, collapseAll, expandAll } = props || {};
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

    if (!diff || !diff["new_path"] || !diff["diff"]) {
        return null;
    }
    const diffText = `--- ${diff["old_path"]}\n+++ ${diff["new_path"]}\n${diff["diff"]}`;

    const linesAdded = diff["diff"].match(/\n\+/g)?.length || 0;
    const linesRemoved = diff["diff"].match(/\n-/g)?.length || 0;
    const fileName = diff["new_path"];

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
                        <FileHeader isOpen={isOpen} linesAdded={linesAdded} linesRemoved={linesRemoved} fileName={fileName} />
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