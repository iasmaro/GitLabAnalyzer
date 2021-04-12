import React, { useState, useEffect } from 'react';
import Spinner from 'react-bootstrap/Spinner';

import { useUserState } from 'UserContext';
import RepoList from 'Components/RepoList/RepoList';
import getRepos from 'Utils/getRepos';

import './Dashboard.css';

const Dashboard = () => {
    const [repos, setRepos] = useState();
    const [isLoading, setIsLoading] = useState(true);
    const username = useUserState();
    useEffect(() => {
        getRepos(username).then((data) => {
            setRepos(data);
            setIsLoading(false);
        });
    }, [username]);

    return (
        <div className='dashboard'>
            {isLoading ? <Spinner animation="border" className="spinner" /> : <RepoList repos={repos}/>}
        </div>
    );
}

export default Dashboard;