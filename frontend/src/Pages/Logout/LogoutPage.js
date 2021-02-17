import React from 'react';
import { Redirect } from 'react-router-dom';

import logout from 'Utils/logout';

import { useUserDispatch } from '../../UserContext';


const LogoutPage = () => {
    logout(useUserDispatch());
    return (<Redirect to={{ pathname: '/' }} />);
}

export default LogoutPage;