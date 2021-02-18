import React from 'react';
import { Row, Col, Form } from 'react-bootstrap'

const RepoModalDates = ({name, date, setDate}) => {

    const handleChange = (e) => {
        e.preventDefault();
        let name= e.target.name;
        let value= e.target.value;
        const newDate = {
            ...date,
            [name]: value
        }
        setDate(newDate)
        console.log(date)
    }

    return (
        <Form>
            <Row>
                <Col>
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
    )
}

export default RepoModalDates;