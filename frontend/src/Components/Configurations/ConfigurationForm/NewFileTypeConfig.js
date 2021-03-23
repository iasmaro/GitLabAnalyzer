import React from 'react';
import { Form, Button} from 'react-bootstrap';

import {ConfigLabels} from 'Constants/constants';

import './ConfigForm.css';

const NewFileTypeCofig = (props) => {
    const { handleInputChange, state, handleAddClick} = props || {};

    return (
         <tr className="merge-request" >
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
                <Button type="submit" size='md' block onClick={handleAddClick}>+</Button>
            </td>
           
        </tr>
    );
};

export default NewFileTypeCofig