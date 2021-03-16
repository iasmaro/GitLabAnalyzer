import React, { useState } from 'react';
import { Form, Col, Row, Container, Button, Table } from 'react-bootstrap';

import {ConfigLabels, initialConfigState} from 'Constants/constants'
import FormattedDateTimePicker from "Components/DateTimePicker/FormattedDateTimePicker";
import saveConfig from "Utils/saveConfig";

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
            startType:  state.SINGLE_COMMENT
        }

        const multiComments = {
            endType: state.MULTI_END_COMMENT,
            startType:  state.MULTI_START_COMMENT
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
                                <tr>
                                    <td>
                                        <Form.Control 
                                            placeholder={ConfigLabels.SCORE_WEIGHT}  
                                            defaultValue={state.ADD_NEW_LINE}
                                            name = "ADD_NEW_LINE"
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                    <td>
                                        <Form.Control 
                                            placeholder={ConfigLabels.SCORE_WEIGHT}  
                                            defaultValue={state.DELETE_LINE}
                                            name = "DELETE_LINE"
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                    <td>
                                        <Form.Control 
                                            placeholder={ConfigLabels.SCORE_WEIGHT} 
                                            defaultValue={state.MOVE_LINE}
                                            name = "MOVE_LINE"
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                    <td>
                                        <Form.Control 
                                            placeholder={ConfigLabels.SCORE_WEIGHT}
                                            defaultValue={state.SPACING}
                                            name = "SPACING"
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                    <td>
                                        <Form.Control 
                                            placeholder={ConfigLabels.SCORE_WEIGHT} 
                                            defaultValue={state.SYNTAX}
                                            name = "SYNTAX"
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                </tr>
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
                                <tr>
                                    <td>
                                        <Form.Control 
                                            placeholder={ConfigLabels.FILE_WEIGHT} 
                                            defaultValue={state.JAVA}
                                            name = "JAVA"
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                    <td>
                                        <Form.Control 
                                            placeholder={ConfigLabels.FILE_WEIGHT} 
                                            defaultValue={state.JS}
                                            name = "JS"
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                    <td>
                                        <Form.Control 
                                            placeholder={ConfigLabels.FILE_WEIGHT} 
                                            defaultValue={state.TS}
                                            name = "TS"
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                    <td>
                                        <Form.Control 
                                            placeholder={ConfigLabels.FILE_WEIGHT} 
                                            defaultValue={state.PY}
                                            name = "PY"
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                    <td>
                                        <Form.Control 
                                            placeholder={ConfigLabels.FILE_WEIGHT} 
                                            defaultValue={state.HTML}
                                            name = "HTML"
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                    <td>
                                        <Form.Control 
                                            placeholder={ConfigLabels.FILE_WEIGHT} 
                                            defaultValue={state.CSS}
                                            name = "CSS"
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                    <td>
                                    <   Form.Control 
                                            placeholder={ConfigLabels.FILE_WEIGHT} 
                                            defaultValue={state.XML}
                                            name = "XML"
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                    <td>
                                        <Form.Control
                                            placeholder={ConfigLabels.FILE_WEIGHT} 
                                            defaultValue={state.CPP}
                                            name = "CPP"
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                    <td>
                                        <Form.Control 
                                            placeholder={ConfigLabels.FILE_WEIGHT} 
                                            defaultValue={state.C}
                                            name = "C"
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                </tr>
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
                                <tr>
                                    <td>
                                        <Form.Control 
                                            placeholder={ConfigLabels.NEW_FILE_EXTENSION} 
                                            name = "FILE_EXTENSION"
                                            defaultValue = {state.FILE_EXTENSION}
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                    <td>
                                        <Form.Control 
                                            placeholder={ConfigLabels.SINGLE_COMMENT}
                                            name = "SINGLE_COMMENT"
                                            onChange = {handleInputChange} 
                                        />
                                    </td>
                                    <td>
                                        <Form.Control 
                                            placeholder={ConfigLabels.MULTI_START_COMMENT} 
                                            name = "MULTI_START_COMMENT"
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                    <td>
                                        <Form.Control 
                                            placeholder={ConfigLabels.MULTI_END_COMMENT} 
                                            name = "MULTI_END_COMMENT"
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                    <td>
                                        <Form.Control 
                                            placeholder={ConfigLabels.FILE_WEIGHT} 
                                            name = "WEIGHT"
                                            onChange = {handleInputChange}
                                        />
                                    </td>
                                    <td>
                                        {!inputList?.length ? (
                                            <td colSpan={6} ></td>
                                        )
                                        :
                                        inputList.map((inputList) => (
                                            <Button type="submit" size='md' block key={inputList?.FILE_EXTENSION} inputList={inputList} onClick={handleAddClick}>+</Button>
                                        ))}
                                    </td>
                                </tr>
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

