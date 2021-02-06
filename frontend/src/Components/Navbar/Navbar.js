import React from 'react';
import './Navbar.css';

class Navbar extends React.Component {

    render() {
        return (
            <nav className="nav-bar">
                <h1 className="nav-bar-logo">GitLab Analyzer</h1>

                <ul className="nav-bar-links">
                    <li> 
                        <a href="./">Sign in</a>
                    </li>
                    <li>
                        <a href="./About">About</a>
                    </li>
                </ul>
            </nav>
        )  
    }
}

export default Navbar