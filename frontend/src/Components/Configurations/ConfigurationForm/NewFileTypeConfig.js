import React from 'react';
import { Form, Button} from 'react-bootstrap';

import {ConfigLabels} from 'Constants/constants';

// import DeleteNewFileConfig from './DeleteNewFileConfig'

import './ConfigForm.css';

const NewFileTypeCofig = (props) => {
    const { handleInputChange, handleDeleteClick, index, inputList } = props || {};

    const state = inputList[index] || {};
    console.log(inputList)

    return (
         <tr className="merge-request" >
            <td>
                <Form.Control 
                    placeholder={ConfigLabels.NEW_FILE_EXTENSION} 
                    name = "FILE_EXTENSION"
                    value = {inputList.FILE_EXTENSION}
                    onChange = {(e) => handleInputChange(e, index)}
                />
            </td>
            <td>
                <Form.Control 
                    placeholder={ConfigLabels.SINGLE_COMMENT}
                    name = "SINGLE_COMMENT"
                    value = {inputList.SINGLE_COMMENT}
                    onChange = {(e) => handleInputChange(e, index)} 
                />
            </td>
            <td>
                <Form.Control 
                    placeholder={ConfigLabels.MULTI_START_COMMENT} 
                    name = "MULTI_START_COMMENT"
                    value = {inputList.MULTI_START_COMMENT}
                    onChange = {(e) => handleInputChange(e, index)}
                />
            </td>
            <td>
                <Form.Control 
                    placeholder={ConfigLabels.MULTI_END_COMMENT} 
                    name = "MULTI_END_COMMENT"
                    value = {inputList.MULTI_END_COMMENT}
                    onChange = {(e) => handleInputChange(e, index)}
                />
            </td>
            <td>
                <Form.Control 
                    placeholder={ConfigLabels.FILE_WEIGHT} 
                    name = "WEIGHT"
                    value = {inputList.FILE_WEIGHT}
                    onChange = {(e) => handleInputChange(e, index)}
                />
            </td>
            <td>
                <Button variant="danger" size='md' block onClick={() => handleDeleteClick(index)}>-</Button>
            </td>
        </tr>
    );
};

export default NewFileTypeCofig