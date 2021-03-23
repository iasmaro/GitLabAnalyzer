import React, { useState } from 'react';
import { Form, Col, Row, Container, Button, Table } from 'react-bootstrap';

import {ConfigLabels, initialConfigState} from 'Constants/constants'
import FormattedDateTimePicker from "Components/DateTimePicker/FormattedDateTimePicker";
import saveConfig from "Utils/saveConfig";

import NewFileTypeCofig from './NewFileTypeConfig';
import DefaultFileTypeConfig from './DefaultFileTypeConfig';
import ScoreWeightConfig from './ScoreWeightConfig';

import './ConfigForm.css';

const ConfigForm = (props) => {
    const { username, toggleModal } = props || {};

    const [state, setstate] = useState(initialConfigState);
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());
    const [inputList, setInputList] = useState([{ FILE_EXTENSION: state.FILE_EXTENSION, SINGLE_COMMENT: state.SINGLE_COMMENT, MULTI_START_COMMENT: state.MULTI_LINE_COMMENT_START, MULTI_END_COMMENT: state.MULTI_LINE_COMMENT_END, WEIGHT: state.WEIGHT }]);

    const handleInputChange = event =>{
        const {name, value} = event.target
        setstate({
            ...state,
            [name]: value
        })

    }

    const handleAddClick = (event) => {
        event.preventDefault();
        setInputList([...inputList, { 
            FILE_EXTENSION: state.FILE_EXTENSION, SINGLE_COMMENT: state.SINGLE_COMMENT, MULTI_START_COMMENT: state.MULTI_LINE_COMMENT_START, MULTI_END_COMMENT: state.MULTI_LINE_COMMENT_END, WEIGHT: state.WEIGHT 
        }]);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        const editFact = {
            addLine: state.ADD_NEW_LINE,
            delLine: state.DELETE_LINE,
            movLine: state.MOVE_LINE,
            syntax: state.SYNTAX,
            spaceChange: state.SPACING
        }

        const fileFact = {
            JAVA: state.JAVA,
            JS: state.JS,
            TS: state.TS,
            PY: state.PY,
            HTML: state.HTML,
            CSS: state.CSS,
            XML: state.XML,
            CPP: state.CPP,
            C: state.C,
            [(state.FILE_EXTENSION).replace(".","")] : state.WEIGHT
        }

        const singleComments = {
            endType: '',
            startType: state.SINGLE_COMMENT
        }

        const multiComments = {
            endType: state.MULTI_END_COMMENT,
            startType: state.MULTI_START_COMMENT
        }

        const commentTypes = {
            [(state.FILE_EXTENSION).replace(".","")] : [singleComments, multiComments]
        }

        saveConfig(commentTypes, editFact, username, startDate, endDate, fileFact, state.CONFIGURATION_NAME);
        if (toggleModal) {
            toggleModal();
        }
    };
    
    return (
        <>
            <Container fluid className="ConfigContainer">
                <Row className="justify-content-md-center" xs={12}>
                    <Form onSubmit={handleSubmit}>
                        <Table hover className="ConfigTable">
                            <thead>
                                <th className='ConfigTitle'>
                                    {ConfigLabels.CONFIGURATION_NAME}
                                </th>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        <Form.Control 
                                            placeholder="Enter Configuration Name" 
                                            defaultValue={state.CONFIGURATION_NAME}
                                            name = "CONFIGURATION_NAME"
                                            onChange = {handleInputChange}
                                            required
                                        />
                                    </td>
                                </tr>
                            </tbody>
                        </Table>
                        <Table hover className="ConfigTable">
                            <thead>
                                <tr>
                                    <th className='ConfigTitle'>
                                        {ConfigLabels.DATE_TIME}
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        <FormattedDateTimePicker 
                                            startName={ConfigLabels.START_DATE} 
                                            endName={ConfigLabels.END_DATE} 
                                            setStartDate={setStartDate} 
                                            setEndDate={setEndDate}
                                        />
                                    </td>
                                </tr>
                            </tbody>
                        </Table>
                        <Table hover className="ConfigTable">
                            <thead>
                                <tr>
                                    <th colSpan='5' className='ConfigTitle'>
                                        {ConfigLabels.CODE_SCORE}
                                    </th>
                                </tr>
                            </thead>
                            <thead>
                                <tr>
                                    <th>{ConfigLabels.ADD_NEW_LINE}</th>
                                    <th>{ConfigLabels.DELETE_LINE}</th>
                                    <th>{ConfigLabels.MOVE_LINE}</th>
                                    <th>{ConfigLabels.SPACING}</th>
                                    <th>{ConfigLabels.SYNTAX}</th>
                                </tr>
                            </thead>
                            <tbody>
                                <ScoreWeightConfig state={state} handleInputChange={handleInputChange} />
                            </tbody>
                        </Table>

                        <Table hover className="ConfigTable">
                            <thead>
                                <tr>
                                    <th colSpan='9' className='ConfigTitle'> 
                                        {ConfigLabels.DEFAULT_FILE}
                                    </th>
                                </tr>
                            </thead>
                            <thead>
                                <th>{ConfigLabels.JAVA}</th>
                                <th>{ConfigLabels.JS}</th>
                                <th>{ConfigLabels.TS}</th>
                                <th>{ConfigLabels.PY}</th>
                                <th>{ConfigLabels.HTML}</th>
                                <th>{ConfigLabels.CSS}</th>
                                <th>{ConfigLabels.XML}</th>
                                <th>{ConfigLabels.CPP}</th>
                                <th>{ConfigLabels.C}</th>
                            </thead>
                            <tbody>
                                <DefaultFileTypeConfig state={state} handleInputChange={handleInputChange} />
                            </tbody>

                        </Table>
                        <Table hover className="ConfigTable">
                            <thead>
                                <tr>
                                    <th colSpan='6' className='ConfigTitle'> 
                                        {ConfigLabels.NEW_FILE}
                                    </th>
                                </tr>
                            </thead>
                            <thead>
                                <tr>
                                    <th>{ConfigLabels.FILE_EXTENSION}</th>
                                    <th>{ConfigLabels.SINGLE_LINE_COMMENT}</th>
                                    <th>{ConfigLabels.MULTI_LINE_COMMENT_START}</th>
                                    <th>{ConfigLabels.MULTI_LINE_COMMENT_END}</th>
                                    <th>{ConfigLabels.WEIGHT}</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                {!inputList?.length ? (
                                    <td colSpan={6} ></td>
                                )
                                :
                                inputList.map((inputList) => (
                                    <NewFileTypeCofig state={state} handleInputChange={handleInputChange} key={inputList?.FILE_EXTENSION} inputList={inputList} handleAddClick={handleAddClick}/>
                                ))}
                            </tbody>
                        </Table>
                        <Col md="auto">
                            <Button variant="success" type="submit" size='md'>
                                Submit
                            </Button>
                        </Col>
                    </Form>
                </Row>
            </Container>
        </>
    )
}

export default ConfigForm

