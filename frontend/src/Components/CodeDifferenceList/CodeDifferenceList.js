import React, {useState} from 'react';
import Button from 'react-bootstrap/Button'

import CodeDifference from 'Components/CodeDifference/CodeDifference';

import './CodeDifferenceList.css';


const CodeDifferenceList = (props) => {
    const { diffs } = props || {};
    const [view, setView] = useState('unified');

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

    return (
        <>
            <div className="button-container">
                <Button className="view-button" variant="dark" onClick={handleChangeView} >{view === 'unified' ? 'Side-by-side' : 'Inline'}</Button>
                <Button variant="dark" >Expand All</Button>
                <Button variant="dark" >Collapse All</Button>
            </div>
            <div className="code-diff-list">
                {diffs.map((diff, i) => <CodeDifference key={i} diff={diff} view={view} />)}
            </div>
        </>
    );
};

export default CodeDifferenceList;