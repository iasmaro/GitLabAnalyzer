import React, {useState} from 'react';
import {Form, Button, Col, Row} from 'react-bootstrap';

import updateToken from 'Utils/updateToken';
import addToken from 'Utils/addToken';

import './Profile.css';

const Profile = (props) => {
    const { username, givenToken } = props || {};
    //Adapted from: https://www.code-boost.com/video/ultimate-react-todo-list/
    const [savedToken, setSavedToken] = useState(givenToken);
    const [token, setToken] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
        if (savedToken) {
            updateToken(username, token);
        } else {
            addToken(username, token);
        }
        setSavedToken(token)
        setToken('')
    }

    const handleChange = (event) => {
        setToken(event.target.value)
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
                        <Form.Control required type="text" placeholder="Enter access token" value={token} onChange={handleChange}/>
                    </Col>
                    <Col sm="3">
                        <Button variant="success" type="submit">Save Token</Button>
                    </Col>
                </Row>
            </Form>               
            {savedToken && <Row className="token">
                <Col sm="9">
                    {savedToken}
                </Col>
            </Row>}
        </>
    )
}

export default Profile;