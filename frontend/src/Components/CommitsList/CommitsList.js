import React from 'react';
import Table from 'react-bootstrap/Table';

import { message } from 'Constants/constants';

import Commit from './Commit';
import './CommitsList.css';

const CommitsList = (props) => {
    const { commits } = props || {};
    return (
        <div className = 'commits-list-container'>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Commit ID</th>
                        <th>Date</th>
                        <th>Author</th>
                        <th>Message</th>
                    </tr>
                </thead>
                <tbody>
                    {!commits.length ? (
                        <td colSpan={4} >{message.NO_COMMITS}</td>
                    )
                    :
                    commits.map((commit) => (
                        <Commit key={commit?.projectId} commit={commit}/>
                    ))}

                </tbody>
            </Table>
        </div>
    );
    };

export default CommitsList