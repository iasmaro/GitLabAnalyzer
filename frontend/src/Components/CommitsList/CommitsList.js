import React from 'react';
import Table from 'react-bootstrap/Table';

import Commit from './Commit';
import './CommitsList.css';

const CommitsList = ({commits}) => {
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
                        {commits.map((commit) => (
                            <Commit key={commit.id} commit={commit}/>
                        ))}
                    </tbody>
                </Table>
            </div>
        );
    };

export default CommitsList