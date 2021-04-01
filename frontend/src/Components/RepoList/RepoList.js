import React, { useState } from 'react';
import Table from 'react-bootstrap/Table';

import { message } from 'Constants/constants';

import Repo from './Repo';
import './RepoList.css';
import RepoSearchBar from './RepoSearchBar';
import { utcToLocal } from 'Components/Utils/formatDates';

const RepoList = (props) => {
    const { repos } = props || {};
    const [searchWord, setSearchWord] = useState('');

    const filterRepos = ((repo)=>{
        if (searchWord === '') {
            return repo
        } else if (repo?.projectName.toLowerCase().includes(searchWord.toLowerCase())) {
            return repo
        }
        else if (utcToLocal(repo?.updatedAt).toLowerCase().includes(searchWord.toLowerCase())) {
            return repo
        }
    });

    return (
        <div className = 'list-container'>
            <Table striped borderless hover variant="light">
                <thead>
                    <tr className='table-header'>
                        <th colSpan='2' className='repoTitle'>Repositories</th>
                        <th colSpan='1' className='repoTitle'><RepoSearchBar searchWord={searchWord} setSearchWord={setSearchWord}></RepoSearchBar></th>
                    </tr>
                </thead>
                <thead>
                    <tr className='repo-headers'>
                        <th>Name</th>
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
                    repos.filter((repo)=>filterRepos(repo)).map((repo) => (
                        <Repo key={repo?.projectId} repo={repo}/>
                    ))}
                </tbody>
            </Table>
        </div>
    );
};

export default RepoList