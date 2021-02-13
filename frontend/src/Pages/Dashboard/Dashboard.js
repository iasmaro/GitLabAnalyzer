import React from 'react';

import './Dashboard.css';
import RepoList from '../.././Components/RepoList/RepoList';
import{repos} from './mockRepos'

function Dashboard() {
    return (
        <div className='dashboard'>
            {<RepoList repos={repos}/>}
        </div>
    )
}

export default Dashboard;