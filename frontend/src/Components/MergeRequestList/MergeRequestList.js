import React, { useState } from 'react';
import { Col, Row, Table, Container } from 'react-bootstrap';
import './MergeRequestList.css';
import { message } from 'Constants/constants';

import CommitsList from 'Components/CommitsList/CommitsList';
import MergeRequest from './MergeRequestList';
import { commits } from 'Mocks/mockCommits';


const MergeRequestList = (props) => {
    const { mergerequests } = props || {};
    return (
        <Container>
            <Row>
                <Col>
                    <Table striped bordered hover>
                        <thead>
                            <tr>
                            <th colSpan='8'>Merge Requests</th>
                            </tr>
                        </thead>
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Create Date</th>
                                <th>Member Name</th>
                                <th>Member Score</th>
                                <th>Merge Date</th>
                                <th>Update Date</th>
                                <th>Merege Request Score</th>
                                <th></th>
                            </tr>
                        </thead>
                        {/*CHANGE THIS */}
                        <tbody>
                            {!mergerequests?.length ? (
                                <td colSpan={8} >{message.NO_MERGE_REQUEST}</td>
                            )
                            :
                            mergerequests.map((mergerequest) => (
                                <MergeRequest key={mergerequest?.projectId} mergerequest={mergerequest}/>
                            ))}

                        </tbody>
                    </Table>
                </Col>
                <Col><CommitsList commits={commits}/></Col>
        </Row>
        </Container>
    )
}


export default MergeRequestList
