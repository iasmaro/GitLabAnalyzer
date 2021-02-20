import React from 'react';

import { useUserState } from 'UserContext';
import Dashboard from 'Pages/Dashboard/Dashboard';
import About from 'Pages/About/About';

const Home = () => {
    const isLoggedIn = useUserState();

    return isLoggedIn ? (
        <Dashboard />
        ) : (
        <About />
    )
}

export default Home;