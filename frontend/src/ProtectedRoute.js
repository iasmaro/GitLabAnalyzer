import React from 'react';
import { Redirect } from 'react-router-dom';

import { useUserState } from 'UserContext';

const ProtectedRoute = (props) => {
    const { Component } = props || {};

    const isLoggedIn = useUserState();
    
    return isLoggedIn ? (
        <Component />
    ) : (
        <Redirect to={{ pathname: '/' }} />
    );
}

export default ProtectedRoute;