import React from 'react';
import {Nav, Navbar} from 'react-bootstrap';
import './Navbar.css';

const NavbarComponent =(prop)=> {

    return (
        <Navbar variant='primary' expand="sm">
            <Navbar.Brand href="/">GitLab Analyzer</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse className="justify-content-end" id="basic-navbar-nav">
                <Nav className="nav">
                    <Nav.Link href="./Pages/Login/Login">Sign In</Nav.Link>
                    <Nav.Link href="./Pages/About/About">About</Nav.Link>
                </Nav>
            </Navbar.Collapse>
        </Navbar>
    )   
}

export default NavbarComponent