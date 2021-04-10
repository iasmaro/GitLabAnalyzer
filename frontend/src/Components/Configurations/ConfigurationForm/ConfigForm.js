import React, { useState } from 'react';
import { Form, Col, Row, Container, Button, Table } from 'react-bootstrap';

import { ConfigLabels } from 'Constants/constants';
import saveConfig from "Utils/saveConfig";
import editConfig from "Utils/editConfig";

import setInitialState from './utils/setInitialState';
import setInitialFileInputList from './utils/setInitialFileInputList';
import NewFileTypeCofig from './NewFileTypeConfig';
import DefaultFileTypeConfig from './DefaultFileTypeConfig';
import ScoreWeightConfig from './ScoreWeightConfig';

import './ConfigForm.css';

const ConfigForm = (props) => {
    const { username, toggleModal, setMessage, configInfo } = props || {};

    const [state, setstate] = useState(setInitialState(configInfo));
    const [inputList, setInputList] = useState(setInitialFileInputList(configInfo));
    
    const displayAlert = (successful) => {
        let snackbar = document.getElementById("config-snackbar");
        if (successful) {
            if (configInfo) {
                setMessage("Successfully modified configuration");
            } else {
                setMessage("Successfully created configuration");
            }
            snackbar.style = 'background-color:green';
        }
        else {
            if (configInfo) {
                setMessage("There was an error modifying the configuration");
            } else {
                setMessage("There was an error creating the configuration");
            } 
            snackbar.style = 'background-color:red';
        }
        snackbar.className = "show";
        setTimeout(function () { snackbar.className = snackbar.className.replace("show", ""); }, 3000);
        setTimeout(function () { setMessage(""); }, 3000);
    }

    
    const handleInputChange = event => {
        const { name, value } = event.target
        setstate({
            ...state,
            [name]: value,
        })
        
    };
    
    const handleChange = (event, index) => {
        const { name, value } = event.target;
        const updatedInputList = inputList.slice();
        updatedInputList[index][name] = value;
        setInputList(updatedInputList);
    };

    const handleAddClick = () => {
        setInputList([...inputList, { 
            FILE_EXTENSION: state.FILE_EXTENSION, SINGLE_COMMENT: state.SINGLE_COMMENT, MULTI_START_COMMENT: state.MULTI_LINE_COMMENT_START, MULTI_END_COMMENT: state.MULTI_LINE_COMMENT_END, WEIGHT: state.WEIGHT 
        }]);
    };

    const handleDeleteClick = (index) => {
        let updatedInputList = inputList.slice();
        updatedInputList.splice(index, 1);
        setInputList(updatedInputList);
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        const editFactor = {
            addLine: state.ADD_NEW_LINE,
            delLine: state.DELETE_LINE,
            movLine: state.MOVE_LINE,
            syntax: state.SYNTAX,
        }

        const fileFactor = {
            JAVA: state.JAVA,
            JS: state.JS,
            TS: state.TS,
            PY: state.PY,
            HTML: state.HTML,
            CSS: state.CSS,
            XML: state.XML,
            CPP: state.CPP,
            C: state.C,
        }

        const commentTypes = {};

        for (let input of inputList) {
            const extension = ((input.FILE_EXTENSION).replace(".","")).toUpperCase();
            if (extension?.length) {
                fileFactor[extension] = input.WEIGHT;
                const singleComments = {
                    endType: '',
                    startType: input.SINGLE_COMMENT
                }
                const multiComments = {
                    endType: input.MULTI_END_COMMENT,
                    startType: input.MULTI_START_COMMENT
                }
                commentTypes[extension] = [singleComments, multiComments];
            }
        }

        if (configInfo) {
            editConfig(commentTypes, editFactor, username, fileFactor, state.CONFIGURATION_NAME).then(response => {
                if (toggleModal) {
                    toggleModal();
                }
                displayAlert(response);
            });
        } else {
            saveConfig(commentTypes, editFactor, username, fileFactor, state.CONFIGURATION_NAME).then(response => {
                if (toggleModal) {
                    toggleModal();
                }
                displayAlert(response);
            });
        }
    };

    return (
        <>
            <Container fluid className="ConfigContainer">
                <Row className="justify-content-md-center" xs={12}>
                    <Form onSubmit={handleSubmit}>
                        <Table hover className="ConfigTable">
                            <thead>
                                <tr>
                                    <th className='ConfigTitle'>
                                        {ConfigLabels.CONFIGURATION_NAME}
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        <Form.Control 
                                            placeholder="Enter Configuration Name" 
                                            defaultValue={state.CONFIGURATION_NAME}
                                            name = "CONFIGURATION_NAME"
                                            onChange = {handleInputChange}
                                            readOnly = {!!configInfo}
                                            required
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
                                <tr>
                                    <th>{ConfigLabels.JAVA}</th>
                                    <th>{ConfigLabels.JS}</th>
                                    <th>{ConfigLabels.TS}</th>
                                    <th>{ConfigLabels.PY}</th>
                                    <th>{ConfigLabels.HTML}</th>
                                    <th>{ConfigLabels.CSS}</th>
                                    <th>{ConfigLabels.XML}</th>
                                    <th>{ConfigLabels.CPP}</th>
                                    <th>{ConfigLabels.C}</th>
                                </tr>
                            </thead>
                            <tbody>
                                <DefaultFileTypeConfig state={state} handleInputChange={handleInputChange} />
                            </tbody>

                        </Table>
                        <Table hover className="ConfigTable">
                            <thead>
                                <tr>
                                    <th colSpan='7' className='ConfigTitle'> 
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
                                    <th><Button size='md' block onClick={handleAddClick}>+</Button></th>
                                </tr>
                            </thead>
                            <tbody>
                                {!inputList?.length ? (
                                    <td colSpan={7} ></td>
                                )
                                :
                                inputList.map((inputList, index) => (
                                    <NewFileTypeCofig
                                        handleInputChange={handleChange}
                                        key={index}
                                        inputList={inputList}
                                        index={index}
                                        handleDeleteClick={handleDeleteClick} />
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
