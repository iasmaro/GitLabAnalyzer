import React, {useState} from 'react';
import './Profile.css';
import {Form, Button, Col, Row} from 'react-bootstrap';

function Profile() {

    //Adapted from: https://www.code-boost.com/video/ultimate-react-todo-list/
    const [tokens, setTokens] = useState([]);
    const [token, setToken] = useState("");

    const handleSubmit = (event) => {
        event.preventDefault();
    
        const newToken = {
            id: new Date().getTime(),
            text: token
        }
        setTokens([...tokens].concat(newToken))
        setToken("")
    }

    const handleDelete = (id) => {
        const updatedTokens = [...tokens].filter((token) => token.id !== id);
        setTokens(updatedTokens);
    }

    return (
        <div className="profile-page">

            <Row>
                <Col>
                    <Form.Label className="profile-title">Access Tokens for username@sfu.ca</Form.Label>
                </Col>
            </Row>

            <Form className="profile-form" onSubmit={handleSubmit}>                
                <Row>
                    <Col sm="9">
                        <Form.Control required type="text" placeholder="Enter access token" value={token} onChange={(event) => setToken(event.target.value)}/>
                    </Col>
                    <Col sm="3">
                        <Button variant="success" type="submit">Save Token</Button>
                    </Col>
                </Row>
            </Form>
                        
            {tokens.map((token) => 
                <Row className="token" key={token.id}>
                    <Col sm="9">
                        {token.text}
                    </Col>
                    <Col sm="3">
                        <Button variant="danger" type="submit" onClick={() => handleDelete(token.id)}>Delete</Button>
                    </Col>
                    
                </Row>
            )}
        </div>
    )
}

export default Profile;