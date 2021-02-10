import React from 'react';
import './Navbar.css';
import {Nav, Navbar} from 'react-bootstrap';

class NavbarComponent extends React.Component {

    render() {
        return (
            <Navbar variant='primary' expand="sm">
                <Navbar.Brand href="/">GitLab Analyzer</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse className="justify-content-end" id="basic-navbar-nav">
                    <Nav className="nav">
                        <Nav.Link href="./">Sign In</Nav.Link>
                        <Nav.Link href="./About">About</Nav.Link>
                        <Nav.Link href="./Dashboard">Dashboard</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        )   
    }
}

export default NavbarComponent