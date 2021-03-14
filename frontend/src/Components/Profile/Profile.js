import React, { useState } from 'react';
import { Form, Button, Col, Row } from 'react-bootstrap';

import updateUser from 'Utils/updateUser';
import saveUser from 'Utils/saveUser';

import './Profile.css';

const Profile = (props) => {
    const { username, givenToken, givenGitlabServer } = props || {};
    //Adapted from: https://www.code-boost.com/video/ultimate-react-todo-list/
    const [token, setToken] = useState(givenToken);
    const [gitlabServer, setGitlabServer] = useState(givenGitlabServer);
    const handleSubmit = (event) => {
        event.preventDefault();
        if (givenToken || givenGitlabServer) {
            updateUser(username, token, gitlabServer);
        } else {
            saveUser(username, token, gitlabServer);
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
                        <Form.Control required type="text" placeholder={givenToken ? token : "Enter access token"} value={token} onChange={handleTokenChange}/>
                    </Col>
                </Row>
                <Row>
                    <Col sm="9">
                        <Form.Group>
                            <Form.Label>GitLab server</Form.Label>
                            <Form.Control required type="text" placeholder={givenGitlabServer ? gitlabServer : "Enter GitLab server url"} value={gitlabServer} onChange={handleGitlabServerChange}/>
                        </Form.Group>
                    </Col>
                </Row>
                <Row>
                    <Col sm="9">
                        <Button variant="success" type="submit">Save Changes</Button>
                    </Col>
                </Row>
            </Form>
        </>
    )
}

export default Profile;