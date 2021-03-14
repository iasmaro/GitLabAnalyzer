import React, {useState} from 'react';
import {Form, Button, Col, Row} from 'react-bootstrap';

import updateUser from 'Utils/updateUser';
import saveUser from 'Utils/saveUser';

import './Profile.css';

const Profile = (props) => {
    const { username, givenToken, givenGitlabServer } = props || {};
    //Adapted from: https://www.code-boost.com/video/ultimate-react-todo-list/
    const [savedToken, setSavedToken] = useState(givenToken);
    const [token, setToken] = useState(givenToken);
    const [savedGitlabServer, setSavedGitlabServer] = useState(givenGitlabServer);
    const [gitlabServer, setGitlabServer] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
        if (savedToken && savedGitlabServer) {
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
                        <Form.Control required type="text" placeholder={savedToken? token : "Enter access token"} value={token} onChange={handleTokenChange}/>
                    </Col>
                </Row>
                <Row>
                    <Col sm="9">
                        <Form.Control required type="text" placeholder={savedGitlabServer? gitlabServer : "Enter GitLab server url"} value={gitlabServer} onChange={handleGitlabServerChange}/>
                    </Col>
                </Row>
                <Row>
                    <Col sm="9">
                        <Button variant="success" type="submit">Save Changes</Button>
                    </Col>
                </Row>
            </Form>               
            {/* {savedToken && <Row className="token">
                <Col sm="9">
                    {savedToken}
                </Col>
            </Row>}
            {savedGitlabServer && <Row className="token">
                <Col sm="9">
                    {savedGitlabServer}
                </Col>
            </Row>} */}
        </>
    )
}

export default Profile;