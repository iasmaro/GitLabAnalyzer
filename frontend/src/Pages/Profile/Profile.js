import React from 'react';
import './Profile.css';
import {Form, Button, Col, Row} from 'react-bootstrap';

function Profile() {
    return (
        <Form className='profile-form'>
            <Form.Group as={Row} controlId="formPlaintextEmail">
                <Form.Label column sm="3">Account</Form.Label>
                <Col sm="5">
                <Form.Control type="text" placeholder="username@sfu.ca" readOnly />
                </Col>
            </Form.Group>

            <Form.Group as={Row} controlId="formPlaintextPassword">
                <Form.Label column sm="3">Access Token</Form.Label>
                <Col sm="5">
                    <Form.Control type="text" placeholder="1bnlkmcadsf89134iromlkdasfnjk431l13f98hadcvmkqlf34jknbfkn31oifnmkldmaskdnfg132i4junf9" readOnly />
                </Col>
                <Col>
                    <Button variant="danger" type="submit">Remove</Button>
                </Col>
                

            </Form.Group>
        </Form>
    )
}

export default Profile;