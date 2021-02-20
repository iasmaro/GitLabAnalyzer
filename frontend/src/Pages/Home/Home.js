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
            <div className='main-header'>
                <h1>We make marking CS projects faster and more pleasant.</h1>
                <p>
                    GitLab Analyzer is an analysis tool for assessing user contributions to a GitLab project.
                    Made to be used as a grading aid for instructors, the tool supports analysis for the work
                    of students between a specified start/end date.  
                </p>
            </div>
            <div className='column'>
                <h3>The Team</h3>
                <p>
                    GitLab Analyzer is brought to you by a diverse group of eight CS students studying at 
                    Simon Fraser University and together we are team Haumea.
                </p>
                <p>
                    <strong>Backend Team:</strong> Andrew, Austin, Minh, Viet
                </p>
                <p>
                    <strong>Frontend Team:</strong> Doris, Ivan, Neil, Sterling
                </p>
            </div>
            <div className='column'>
                <h3>Get Started</h3>
                    <p>
                        Sign in with your SFU account and add your GitLab token to begin analyzing!  
                    </p>
                <h3>Current Features</h3>
                <ul>
                    <li>Limit analysis for a user between specified dates</li>
                    <li>Display list of all merge requests</li>
                    <li>Display list of commits within a specific merge request</li>
                    <li>Display list of all commits</li>
                </ul>
                <p>
                    More features coming.
                </p>
            </div>
        </div>
    )
}

export default Home;