import React from 'react';

import RepoList from 'Components/RepoList/RepoList';

import './Dashboard.css';
import{repos} from './mockRepos'

const Dashboard = (props) => {
    return (
        <div className='dashboard'>
            <RepoList repos={repos}/>
        </div>
    )
}

export default Dashboard;