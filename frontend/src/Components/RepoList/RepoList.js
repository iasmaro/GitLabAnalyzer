import React from 'react';
import Table from 'react-bootstrap/Table';

import { message } from 'Constants/constants';

import Repo from './Repo';
import './RepoList.css';

const RepoList = (props) => {
    const { repos } = props || {};

    return (
        <div className = 'list-container'>
            <Table striped bordered hover variant="light">
                <thead>
                    <tr>
                    <th colSpan='4' className='repoTitle'>Repositories</th>
                    </tr>
                </thead>
                <thead>
                    <tr className="repo-headers">
                        <th>Repo</th>
                        <th>Namespace</th>
                        <th>Last Modified</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {!repos ? (
                        <tr>
                            <td colSpan={3} >{message.TOKEN_NOT_SET}</td>
                        </tr>
                    )
                    : !repos.length ? (
                        <tr>
                            <td colSpan={3} >{message.NO_REPOS}</td>
                        </tr>
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