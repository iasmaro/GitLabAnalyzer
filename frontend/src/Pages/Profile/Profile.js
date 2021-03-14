import React, { useState, useEffect } from 'react';
import Spinner from 'react-bootstrap/Spinner';

import { useUserState } from 'UserContext';
import getToken from 'Utils/getToken';
import getGitlabServer from 'Utils/getGitlabServer';
import Profile from 'Components/Profile/Profile'

import './Profile.css';

const ProfilePage = () => {
    const [token, setToken] = useState('');
    const [gitlabServer, setGitlabServer] = useState('');
    const [isLoadingToken, setIsLoadingToken] = useState(true);
    const [isLoadingGitlabServer, setIsLoadingGitlabServer] = useState(true);
    const username = useUserState();
    useEffect(() => {
        getToken(username).then((data) => {
            setToken(data);
            setIsLoadingToken(false);
        });
        getGitlabServer(username).then((data) => {
            setGitlabServer(data);
            setIsLoadingGitlabServer(false);
        });
    }, [username]);

    return (
        <div className="profile-page">
            {(isLoadingToken || isLoadingGitlabServer) ? <Spinner animation="border" className="spinner" /> : <Profile username={username} givenToken={token} givenGitlabServer={gitlabServer}/>}
        </div>
    )
}

export default ProfilePage;