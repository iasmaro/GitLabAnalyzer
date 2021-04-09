import React, { useState } from 'react';
import Button from 'react-bootstrap/Button'

import CodeDifference from 'Components/CodeDifference/CodeDifference';

import DiffsTitle from './DiffsTitle'
import './CodeDifferenceList.css';


const CodeDifferenceList = (props) => {
    const { diffs, diffsTitle } = props || {};
    const [view, setView] = useState('unified');
    const [collapse, setCollapse] = useState(0);
    const [expand, setExpand] = useState(0);

    if (!diffs) {
        return null;
    }

    const handleChangeView = () => {
        if (view === 'unified') {
            setView('split');
        } else {
            setView('unified');
        }
    }

    const collapseAll = () => {
        setCollapse(collapse + 1);
    }

    const expandAll = () => {
        setExpand(expand + 1);
    }

    return (
        <>
            <div className="button-container">
                <Button className="view-button" variant="dark" onClick={handleChangeView} >{view === 'unified' ? 'Side-by-side' : 'Inline'}</Button>
                <Button variant="dark" onClick={expandAll} >Expand All</Button>
                <Button variant="dark" onClick={collapseAll} >Collapse All</Button>
            </div>
            <DiffsTitle diffsTitle={diffsTitle} />
            <div className="code-diff-list">
                {diffs.map((diff, i) => <CodeDifference key={i} diff={diff} view={view} expandAll={expand} collapseAll={collapse} />)}
            </div>
        </>
    );
};

export default CodeDifferenceList;