import React from 'react';
import Table from 'react-bootstrap/Table';

import { message } from 'Constants/constants';

import Repo from './Repo';
import './RepoList.css';

<<<<<<< HEAD
const RepoList = ({repos}) => {
        return (
            <div className = 'list-container'>
                <Table striped bordered hover variant="dark">
                    <thead>
                        <tr>
                            <th colSpan='3'>Repositories</th>
                        </tr>
                    </thead>
                    <thead>
                        <tr>
                            <th>Name</th>
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
=======
const RepoList = (props) => {
    const { repos } = props || {};

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
                        <td colSpan={3} >{message.TOKEN_NOT_SET}</td>
                    )
                    : !repos.length ? (
                        <td colSpan={3} >{message.NO_REPOS}</td>
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
>>>>>>> master

export default RepoList