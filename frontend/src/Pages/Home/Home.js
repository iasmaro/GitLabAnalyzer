import React from 'react';

import { useUserState } from 'UserContext';
import Dashboard from 'Pages/Dashboard/Dashboard';

import './Home.css';


const Home = () => {
    const isLoggedIn = useUserState();

    return isLoggedIn ? (
        <Dashboard />
        ) : (
        <div className='home-page'>
            <h1>Sign In</h1>
        </div>
    )
}

export default Home;