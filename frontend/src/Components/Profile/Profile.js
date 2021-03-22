import React, { useState } from 'react';
import { Form, Button, Col, Row } from 'react-bootstrap';

import { SCHEME } from 'Constants/constants';
import updateUser from 'Utils/updateUser';
import saveUser from 'Utils/saveUser';

import './Profile.css';

const tokenPlaceHolder = 'Enter access token';
const serverPlaceHolder = 'Enter GitLab server url';

const Profile = (props) => {
    const { username, givenToken, givenGitlabServer } = props || {};
    //Adapted from: https://www.code-boost.com/video/ultimate-react-todo-list/
    const [token, setToken] = useState(givenToken);
    const [gitlabServer, setGitlabServer] = useState(givenGitlabServer);
    const [isInvalid, setInvalid] = useState(false);
    const [message, setMessage] = useState("");

    const displayMessage = (successful) => {
        let snackbar = document.getElementById("snackbar");
        if (successful) {
            setMessage("Successfully updated profile");
            snackbar.style='color:green';
        }
        else {
            setMessage("There was an error updating the profile");
            snackbar.style='color:red';
        }
        snackbar.className = "show";
        setTimeout(function(){ snackbar.className = snackbar.className.replace("show", ""); }, 3000);
    }
    
    const handleSubmit = (event) => {
        const invalid = gitlabServer.substring(0,7) !== SCHEME.HTTP && gitlabServer.substring(0,8) !== SCHEME.HTTPS;
        setInvalid(invalid);
        event.preventDefault();
        if (!invalid) {
            if (givenToken || givenGitlabServer) {
                updateUser(username, token, gitlabServer).then(response => {
                    displayMessage(response);
                });
            } else {
                saveUser(username, token, gitlabServer).then(response => {
                    displayMessage(response);
                });
            }
        }

    }

    const handleTokenChange = (event) => {
        setToken(event.target.value)
    }

    const handleGitlabServerChange = (event) => {
        setGitlabServer(event.target.value)
    }
    return (
        <>
            <Row>
                <Col>
                    <Form.Label className="profile-title">Access Tokens for: {username}@sfu.ca</Form.Label>
                </Col>
            </Row>
            <Form className="profile-form" onSubmit={handleSubmit}>                
                <Row>
                    <Col sm="9">
                        <Form.Label>Access token</Form.Label>
                        <Form.Control required type="text" placeholder={givenToken ? token : tokenPlaceHolder} value={token} onChange={handleTokenChange}/>
                    </Col>
                </Row>
                <Row>
                    <Col sm="9">
                        <Form.Group>
                            <Form.Label>GitLab server</Form.Label>
                            <Form.Control required type="text" isInvalid={isInvalid} placeholder={givenGitlabServer ? gitlabServer : serverPlaceHolder} value={gitlabServer} onChange={handleGitlabServerChange}/>
                            <Form.Control.Feedback type="invalid">
                                URL must start with either http:// or https://
                            </Form.Control.Feedback>
                        </Form.Group>
                    </Col>
                </Row>
                <Row>
                    <Col sm="9">
                        <Button variant="success" type="submit">Save Changes</Button>
                    </Col>
                </Row>
            </Form>
            <div id="snackbar">{message}</div>
        </>
    )
}

export default Profile;