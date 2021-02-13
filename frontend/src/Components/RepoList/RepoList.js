import React from 'react';
import Table from 'react-bootstrap/Table';

import Repo from './Repo';
import './RepoList.css';

const RepoList = ({repos}) => {
        return (
            <div className = 'list-container'>
                <Table striped bordered hover variant="dark">
                    <thead>
                        <tr>
                            <th>Repo Name</th>
                            <th>Last Modified</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {repos.map((repo) => (
                            <Repo key={repo.id} repo={repo}/>
                        ))}
                    </tbody>
                </Table>
            </div>
        );
    };

export default RepoList