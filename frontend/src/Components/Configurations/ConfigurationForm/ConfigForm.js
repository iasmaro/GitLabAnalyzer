import React, {useState, useEffect} from 'react'
import FormattedDateTimePicker from "Components/DateTimePicker/FormattedDateTimePicker";
import {Form, Col, Row, Container, Button} from 'react-bootstrap'
import {ConfigLabels, initialConfigState} from 'Constants/constants'
import './ConfigForm.css'

const ConfigForm = () => {

    const [state, setstate] = useState([]);
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());
    const [inputList, setInputList] = useState([{ FILE_EXTENSION: "", SINGLE_COMMENT: "", MULTI_START_COMMENT: "", MULTI_END_COMMENT: "", WEIGHT: "" }]);

    const handleInputChange = event =>{
        const {name, value} = event.target.value
        setstate({
            ...state,
            [name]: value
        })

    }
    const handleAddClick = (event) => {
        event.preventDefault();
        setInputList([...inputList, { FILE_EXTENSION: event.target.value, SINGLE_COMMENT: "", MULTI_START_COMMENT: "", MULTI_END_COMMENT: "", WEIGHT: "" }]);
        console.log(inputList)

    };





    return (
        <>
            <Container fluid className="ConfigContainer">
                <Row className="justify-content-md-center" xs={12}>
                    <h2>{ConfigLabels.CONFIGURATION_FORM}</h2>
                </Row>
                <Row className="justify-content-md-center">
                    <Form>
                        <Form.Group as={Row} controlId="configName">
                            <Form.Label comlumn md={2}>
                                {ConfigLabels.CONFIGURATION_NAME}
                            </Form.Label>
                            <Col md={10}>
                                <Form.Control 
                                    placeholder="Enter Configuration Name" 
                                    defaultValue={initialConfigState.CONFIGURATION_NAME}
                                    name = "ConfigName"
                                    onChange = {handleInputChange}
                                />
                            </Col>
                        </Form.Group>
                        <Form.Row className="justify-content-md-center">
                            <h4>{ConfigLabels.DATE_TIME}</h4>
                        </Form.Row>
                        <Form.Row className="justify-content-md-center">
                            <FormattedDateTimePicker 
                                startName={ConfigLabels.START_DATE} 
                                endName={ConfigLabels.END_DATE} 
                                setStartDate={setStartDate} 
                                setEndDate={setEndDate}
                            />
                        </Form.Row>
                        <Form.Row className="justify-content-md-center">
                            <h4>{ConfigLabels.CODE_SCORE}</h4>
                        </Form.Row>
                        <Form.Row>
                            <Form.Group as={Col} controlId="CodeScoreValues">
                                <Form.Label>{ConfigLabels.ADD_NEW_LINE}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.SCORE_WEIGHT}  
                                    defaultValue={initialConfigState.ADD_NEW_LINE}
                                    name = "addline"
                                    onChange = {handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="CodeScoreValues">
                                <Form.Label>{ConfigLabels.DELETE_LINE}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.SCORE_WEIGHT}  
                                    defaultValue={state.DELETE_LINE}
                                    name = "deleteline"
                                    onChange = {handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="CodeScoreValues">
                                <Form.Label>{ConfigLabels.MOVE_LINE}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.SCORE_WEIGHT} 
                                    defaultValue={state.MOVE_LINE}
                                    name = "moveline"
                                    onChange = {handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="CodeScoreValues">
                                <Form.Label>{ConfigLabels.SPACING}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.SCORE_WEIGHT}
                                    defaultValue={state.SPACING}
                                    name = "spacing"
                                    onChange = {handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="CodeScoreValues">
                                <Form.Label>{ConfigLabels.SYNTAX}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.SCORE_WEIGHT} 
                                    defaultValue={state.SYNTAX}
                                    name = "syntax"
                                    onChange = {handleInputChange}
                                />
                            </Form.Group>
                        </Form.Row>
                        <Form.Row className="justify-content-md-center">
                            <h4>{ConfigLabels.DEFAULT_FILE}</h4>
                        </Form.Row>
                        <Form.Row>
                            <Form.Group as={Col} controlId="configFileScore">
                                <Form.Label>{ConfigLabels.JAVA}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.FILE_WEIGHT} 
                                    defaultValue={state.JAVA}
                                    name = "java"
                                    onChange = {handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="configFileScore">
                                <Form.Label>{ConfigLabels.JS}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.FILE_WEIGHT} 
                                    defaultValue={state.JS}
                                    name = "js"
                                    onChange = {handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="configFileScore">
                                <Form.Label>{ConfigLabels.TS}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.FILE_WEIGHT} 
                                    defaultValue={state.TS}
                                    name = "ts"
                                    onChange = {handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="configFileScore">
                                <Form.Label>{ConfigLabels.PY}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.FILE_WEIGHT} 
                                    defaultValue={state.PY}
                                    name = "py"
                                    onChange = {handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="configFileScore">
                                <Form.Label>{ConfigLabels.HTML}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.FILE_WEIGHT} 
                                    defaultValue={state.HTML}
                                    name = "html"
                                    onChange = {handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="configFileScore">
                                <Form.Label>{ConfigLabels.CSS}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.FILE_WEIGHT} 
                                    defaultValue={state.CSS}
                                    name = "css"
                                    onChange = {handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="configFileScore">
                                <Form.Label>{ConfigLabels.XML}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.FILE_WEIGHT} 
                                    defaultValue={state.XML}
                                    name = "xml"
                                    onChange = {handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="configFileScore">
                                <Form.Label>{ConfigLabels.CPP}</Form.Label>
                                <Form.Control
                                    placeholder={ConfigLabels.FILE_WEIGHT} 
                                    defaultValue={state.CPP}
                                    name = "cpp"
                                    onChange = {handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="configFileScore">
                                <Form.Label>{ConfigLabels.C}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.FILE_WEIGHT} 
                                    defaultValue={state.C}
                                    name = "c"
                                    onChange = {handleInputChange}
                                />
                            </Form.Group>
                        </Form.Row>
                        <Form.Row className="justify-content-md-center">
                            <h4>{ConfigLabels.NEW_FILE}</h4>
                        </Form.Row>
                        <Form.Row className="justify-content-md-center">
                            <Form.Group as={Col} controlId="newFileScore">
                                <Form.Label>{ConfigLabels.FILE_EXTENSION}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.NEW_FILE_EXTENSION} 
                                    name = "fileExtension"
                                    onChange = {handleInputChange}
                                    value = {state.FILE_EXTENSION}
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="newFileScore">
                                <Form.Label>{ConfigLabels.SINGLE_LINE_COMMENT}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.SINGLE_COMMENT}
                                    name = "singleComment"
                                    onChange = {handleInputChange} 
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="newFileScore">
                                <Form.Label>{ConfigLabels.MULTI_LINE_COMMENT_START}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.MULTI_START_COMMENT} 
                                    name = "multiStartComment"
                                    onChange = {handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="newFileScore">
                                <Form.Label>{ConfigLabels.MULTI_LINE_COMMENT_END}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.MULTI_END_COMMENT} 
                                    name = "multiEndComment"
                                    onChange = {handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group as={Col} controlId="newFileScore">
                                <Form.Label>{ConfigLabels.WEIGHT}</Form.Label>
                                <Form.Control 
                                    placeholder={ConfigLabels.FILE_WEIGHT} 
                                    name = "weight"
                                    onChange = {handleInputChange}
                                />
                            </Form.Group>
                    
                            <Form.Group as={Col} controlId="newFileScore">
                                <Form.Label ></Form.Label>
                                <Button type="submit" size='md'block onClick={handleAddClick}>+</Button>
                            </Form.Group>

                            {/* <Form.Group as={Col} controlId="newFileScore">
                                <Form.Label></Form.Label>
                                <Button variant="danger" type="submit" size='md'block>-</Button>
                            </Form.Group> */}
                        </Form.Row>
                    </Form>
                </Row>
            </Container>
        </>
    )
}

export default ConfigForm

