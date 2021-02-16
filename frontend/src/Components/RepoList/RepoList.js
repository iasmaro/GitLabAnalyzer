import React from 'react';
import Table from 'react-bootstrap/Table';

import Repo from './Repo';
import './RepoList.css';

const RepoList = (props) => {
    const { repos } = props || {};
    const tokensNotSetMessage = 'Please set your gitlab token in the Profile page';
    const noReposMessage = 'It seems that you do not have any repositories at this moment';
    return (
        <div className = 'list-container'>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Repo Name</th>
                        <th>Last Modified</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {!repos ? (
                        <td colSpan={3} >{tokensNotSetMessage}</td>
                    )
                    : !repos.length ? (
                        <td colSpan={3} >{noReposMessage}</td>
                    )
                    :
                    repos.map((repo) => (
                        <Repo key={repo?.projectId} repo={repo}/>
                    ))}
                </tbody>
            </Table>
        </div>
    );
};

export default RepoList