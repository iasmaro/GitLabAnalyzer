import React, { useState, useEffect } from 'react';
import Spinner from 'react-bootstrap/Spinner';

import { useUserState } from 'UserContext';
import getToken from 'Utils/getToken';
import Profile from 'Components/Profile/Profile'

import './Profile.css';

const ProfilePage = () => {
    const [token, setToken] = useState();
    const [isLoading, setIsLoading] = useState(true);
    const username = useUserState();
    useEffect(() => {
        getToken(username).then((data) => {
            setToken(data);
            setIsLoading(false);
        });
    }, [username]);

    return (
        <div className="profile-page">
            {isLoading ? <Spinner animation="border" className="spinner" givenToken={token} /> : <Profile username={username} />}
        </div>
    )
}

export default ProfilePage;