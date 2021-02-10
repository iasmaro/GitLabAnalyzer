import React from 'react';
import Repo from './Repo';
import './RepoList.css';

const RepoList = ({ repos }) => {
    return (
        <div className='repo-container'>
            <ul className='repo-list'>
                {repos.map((repo) => (
                    <Repo key={repo.id} repo={repo}/>
                ))}
            </ul>
        </div>
    );
};

export default RepoList