import React from 'react';
import Table from 'react-bootstrap/Table';

import { message } from 'Constants/constants';

import Commit from './Commit';
import './CommitsList.css';

const CommitsList = (props) => {
    const { commits, setCodeDiffs } = props || {};

    const handleClick = (diffs) => {
        if(setCodeDiffs) {
            setCodeDiffs(diffs);
        }
    }

    return (
        <div className = 'commits-list-container'>
            <Table striped bordered hover variant="light">
                <thead>
                        <tr>
                            <th colSpan='4'className='commitTitle'>Commits</th>
                        </tr>
                </thead>
                <thead>
                    <tr>
                        <th>Commit ID</th>
                        <th>Date</th>
                        <th>Author</th>
                    </tr>
                </thead>
                <tbody>
                    {!commits?.length ? (
                        <td colSpan={4} >{message.NO_COMMITS}</td>
                    )
                    :
                    commits.map((commit, i) => (
                        <Commit key={i} commit={commit} handleClick={handleClick} />
                    ))}

                </tbody>
            </Table>
        </div>
    );
    };

export default CommitsList