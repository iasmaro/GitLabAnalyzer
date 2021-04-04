import React, { useState } from 'react';
import { Table, Badge, Tooltip, OverlayTrigger } from 'react-bootstrap';

import { message } from 'Constants/constants';
import { useSortableDataObject, getClassNamesFor } from 'Utils/sortTables';
import filterRepos from 'Utils/filterRepos'

import Repo from './Repo';
import RepoSearchBar from './RepoSearchBar';
import './RepoList.css';

const RepoList = (props) => {
    const { repos } = props || {};
    const [searchWord, setSearchWord] = useState('');
    const { items, requestSortObject, sortConfig  } = useSortableDataObject(repos);
    const namespaceTooltip = <Tooltip>Namespace refers to the user name, group name, or subgroup name associated with the repository.</Tooltip>;
    
    return (
        <div className = 'list-container'>
            <Table striped borderless hover variant="light">
                <thead>
                    <tr className='table-header'>
                        <th colSpan='2' className='repoTitle'>Repositories</th>
                        <th colSpan='2' className='repoTitle'><RepoSearchBar searchWord={searchWord} setSearchWord={setSearchWord} /></th>
                    </tr>
                </thead>
                <thead>
                    <tr className="repo-headers">
                        <th className={getClassNamesFor(sortConfig, 'projectName')} onClick={() => requestSortObject('projectName')}>Name</th>
                        <th className={getClassNamesFor(sortConfig, 'namespace')} onClick={() => requestSortObject('namespace')}>Namespace {' '}
                            <OverlayTrigger placement='right' overlay={namespaceTooltip}>
                                <Badge pill variant="dark">
                                    i
                                </Badge>
                            </OverlayTrigger>
                        </th>
                        <th className={getClassNamesFor(sortConfig, 'updatedAt')} onClick={() => requestSortObject('updatedAt')}>Last Modified</th>
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
                    items.filter((repo)=>filterRepos(repo, searchWord)).map((repo) => (
                        <Repo key={repo?.projectId} repo={repo}/>
                    ))}
                </tbody>
            </Table>
        </div>
    );
};

export default RepoList