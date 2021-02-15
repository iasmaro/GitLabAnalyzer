import React from 'react';

import { useUserState } from '../../UserContext';

import DefaultNavbar from './components/DefaultNavbar';
import FullNavbar from './components/FullNavbar';
import './Navbar.css';

const Navbar = ()=> {
    const isLoggedIn = useUserState();

    return isLoggedIn ? (
        <FullNavbar />
    ) : (
        <DefaultNavbar />
    )
}

export default Navbar