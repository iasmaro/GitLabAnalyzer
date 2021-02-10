import React from 'react';
import './Dashboard.css';
import RepoList from "../.././Components/RepoList/RepoList";

function Dashboard() {
    const repos = [
        {
            id: 1,
            name: 'Test Repository 1',
            date: 'May 1'
        },
        {
            id: 2,
            name: 'Test Repository 2',
            date: 'Sept 2'
        },
        {
            id: 3,
            name: 'Test Repository 3',
            date: 'March 18'
        }
    ]
    return (
        <div className='dashboard'>
            <h1>Dashboard</h1>
            {<RepoList repos={repos}/>}
        </div>
    )
}

export default Dashboard;