import React from 'react';
import { Row, Col, Form } from 'react-bootstrap';

const RepoModalDates = ({name, date, setDate}) => {

    const handleChange = (e) => {
        e.preventDefault();
        const newDate = {
            ...date,
            [e.target.name]: e.target.value
        };
        setDate(newDate);
    };

    return (
        <Form>
            <Row className="dates">
                <Col sm="2">
                    {name}
                </Col>
                <Col>
                    <Form.Control name="Year" placeholder="Year" onChange={handleChange}></Form.Control>
                </Col>
                <Col>
                    <Form.Control name="Month" placeholder="Month" onChange={handleChange}></Form.Control>
                </Col>
                <Col>
                    <Form.Control name="Day" placeholder="Day" onChange={handleChange}></Form.Control>
                </Col>
                <Col>
                    <Form.Control name="Hours" placeholder="Hours" onChange={handleChange}></Form.Control>
                </Col>
                <Col>
                    <Form.Control name="Minutes" placeholder="Minutes" onChange={handleChange}></Form.Control>
                </Col>
                <Col>
                    <Form.Control name="Seconds" placeholder="Seconds" onChange={handleChange}></Form.Control>
                </Col>
            </Row>
        </Form>
    );
};

export default RepoModalDates;