import React from 'react';
import { Nav, Navbar } from 'react-bootstrap';

const FullNavbar = ()=> {

    return (
        <Navbar variant='primary' expand="sm">
            <Navbar.Brand href="/">
                <img
                    alt=""
                    src="/Haumea2.jpg"
                    width="50"
                    height="30"
                />  GitLab Analyzer
            </Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse className="justify-content-end" id="basic-navbar-nav">
                <Nav className="nav">
                    <Nav.Link href="/">Repositories</Nav.Link>
                    <Nav.Link href="/Configurations">Configurations</Nav.Link>
                    <Nav.Link href="/Reports">Past Reports</Nav.Link>
                    <Nav.Link href="/Profile">Profile</Nav.Link>
                    <Nav.Link href="/Logout">Logout</Nav.Link>
                </Nav>
            </Navbar.Collapse>
        </Navbar>
    )   
}

export default FullNavbar