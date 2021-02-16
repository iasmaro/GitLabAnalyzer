import React, {useState} from 'react';
import {Form, Button, Col, Row} from 'react-bootstrap';
import './Profile.css';

const Profile = (props) => {
    const { username, givenToken } = props || {};
    //Adapted from: https://www.code-boost.com/video/ultimate-react-todo-list/
    const [savedToken, setSavedToken] = useState(givenToken);
    const [token, setToken] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
        setSavedToken(token)
        setToken('')
    }

    const handleChange = (event) => {
        setToken(event.target.value)
    }

    const handleDelete = () => {
        setSavedToken('');
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
                <Col sm="3">
                    <Button variant="danger" type="submit" onClick={handleDelete}>Delete</Button>
                </Col>
            </Row>}
        </>
    )
}

export default Profile;