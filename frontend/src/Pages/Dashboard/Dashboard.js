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
        },
        {
            id: 4,
            name: 'Test Repository 4',
            date: 'Feb 5'
        },
        {
            id: 5,
            name: 'Test Repository 5',
            date: 'Sept 23'
        },
        {
            id: 6,
            name: 'Test Repository 6',
            date: 'Oct 31'
        },
        {
            id: 7,
            name: 'Test Repository 7',
            date: 'April 20'
        },
        {
            id: 8,
            name: 'Test Repository 8',
            date: 'June 3'
        }
    ]
    return (
        <div className='dashboard'>
            {<RepoList repos={repos}/>}
        </div>
    )
}

export default Dashboard;