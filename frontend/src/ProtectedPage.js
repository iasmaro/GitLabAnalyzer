import React from 'react';

import { useUserState } from './UserContext';

const ProtectedPage= (props) => {
    const { Component, Other } = props || {};
    const isAuthenticated = useUserState();
    console.log(isAuthenticated);
    return isAuthenticated ? (
        <Other />
    ) : (
        <Component />
    );
}

export default ProtectedPage;