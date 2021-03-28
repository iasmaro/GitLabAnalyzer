import React from 'react';
import Table from 'react-bootstrap/Table';

import { message } from 'Constants/constants';
import { useSortableData, getClassNamesFor } from 'Utils/sortTables';

import Repo from './Repo';
import './RepoList.css';

const RepoList = (props) => {
    const { repos } = props || {};
    const { items, requestSort, sortConfig  } = useSortableData(repos);

    return (
        <div className = 'list-container'>
            <Table striped bordered hover variant="light">
                <thead>
                    <tr>
                    <th colSpan='3' className='repoTitle'>Repositories</th>
                    </tr>
                </thead>
                <thead>
                    <tr className="repo-headers">
                        <th className={getClassNamesFor('projectName', sortConfig)} onClick={() => requestSort('projectName')}>Name</th>
                        <th className={getClassNamesFor('updatedAt', sortConfig)} onClick={() => requestSort('updatedAt')}>Last Modified</th>
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
                    items.map((repo) => (
                        <Repo key={repo?.projectId} repo={repo}/>
                    ))}
                </tbody>
            </Table>
        </div>
    );
};

export default RepoList