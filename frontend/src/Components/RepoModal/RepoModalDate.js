import React, { useState } from 'react';
import { Row, Col, Form } from 'react-bootstrap'

import { modal } from "Constants/constants"

const RepoModalDates = (props) => {
    const [startDate, setStartDate] = useState({})
    const [endDate, setEndDate] = useState({})

    return (
        <Form>
            <Row>
                <Col>
                    {modal.START_DATE}
                </Col>
                <Col>
                    <Form.Control placeholder="Year"></Form.Control>
                </Col>
                <Col>
                    <Form.Control placeholder="Month"></Form.Control>
                </Col>
                <Col>
                    <Form.Control placeholder="Day"></Form.Control>
                </Col>
                <Col>
                    <Form.Control placeholder="Hours"></Form.Control>
                </Col>
                <Col>
                    <Form.Control placeholder="Minutes"></Form.Control>
                </Col>
                <Col>
                    <Form.Control placeholder="Seconds"></Form.Control>
                </Col>
            </Row>

            <Row>
                <Col>
                    {modal.END_DATE}
                </Col>
                <Col>
                    <Form.Control placeholder="Year"></Form.Control>
                </Col>
                <Col>
                    <Form.Control placeholder="Month"></Form.Control>
                </Col>
                <Col>
                    <Form.Control placeholder="Day"></Form.Control>
                </Col>
                <Col>
                    <Form.Control placeholder="Hours"></Form.Control>
                </Col>
                <Col>
                    <Form.Control placeholder="Minutes"></Form.Control>
                </Col>
                <Col>
                    <Form.Control placeholder="Seconds"></Form.Control>
                </Col>
            </Row>
        </Form>
    )
}

export default RepoModalDates;