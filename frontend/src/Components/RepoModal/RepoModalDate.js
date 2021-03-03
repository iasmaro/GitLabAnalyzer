import React from 'react';
import { Row, Col, Form } from 'react-bootstrap';

const RepoModalDates = (props) => {
    
    const {name, date, setDate} = props;
    
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
                    <Form.Control name="Year" placeholder={date?.Year} onChange={handleChange}/>
                </Col>
                <Col>
                    <Form.Control name="Month" placeholder={date?.Month} onChange={handleChange}/>
                </Col>
                <Col>
                    <Form.Control name="Day" placeholder={date?.Day} onChange={handleChange}/>
                </Col>
                <Col>
                    <Form.Control name="Hours" placeholder={date?.Hours} onChange={handleChange}/>
                </Col>
                <Col>
                    <Form.Control name="Minutes" placeholder={date?.Minutes} onChange={handleChange}/>
                </Col>
                <Col>
                    <Form.Control name="Seconds" placeholder={date?.Seconds} onChange={handleChange}/>
                </Col>
            </Row>
        </Form>
    );
};

export default RepoModalDates;