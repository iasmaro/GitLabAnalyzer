import { React, useState, useEffect } from 'react';
import Table from 'react-bootstrap/Table';

import { message } from 'Constants/constants';

import Commit from './Commit';
import './CommitsList.css';

const CommitsList = (props) => {
    const { commits, setCodeDiffs } = props || {};
    const [selectedRowIndex, setSelectedRowIndex] = useState(-1);

    useEffect(() => {
        setSelectedRowIndex(-1);
    }, [commits]);

    const handleClick = (diffs, index) => {
        if(setCodeDiffs) {
            setCodeDiffs(diffs);
        }
        setSelectedRowIndex(index);
    }

    return (
        <div className = 'commits-list-container'>
            <Table bordered hover variant="light">
                <thead>
                        <tr>
                            <th colSpan='6' className='commitTitle'>Commits</th>
                        </tr>
                </thead>
                <thead>
                    <tr className='commits-headers'>
                        <th>Date</th>
                        <th>Message</th>
                        <th>Score</th>
                        <th>Author</th>
                        <th colSpan='2'>Lines Changed</th>
                    </tr>
                </thead>
                <tbody>
                    {!commits?.length ? (
                        <td colSpan='4' >{message.NO_COMMITS}</td>
                    )
                    :
                    commits.map((commit, index) => (
                        <Commit 
                            key={index} 
                            commit={commit} 
                            handleClick={handleClick} 
                            index={index}
                            selected={index === selectedRowIndex}/>
                    ))}

                </tbody>
            </Table>
        </div>
    );
};

export default CommitsList