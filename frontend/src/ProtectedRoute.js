import React from 'react';
import { Redirect } from 'react-router-dom';

import { useUserState } from './UserContext';

const ProtectedRoute = (props) => {
    const { Component } = props || {};
    const isAuthenticated = useUserState();
    return isAuthenticated ? (
        <Component />
    ) : (
        <Redirect to={{ pathname: '/' }} />
    );
}

export default ProtectedRoute;