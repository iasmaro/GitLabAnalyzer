import React from 'react';
import { Form } from 'react-bootstrap';

import { ConfigLabels } from 'Constants/constants';

import './ConfigForm.css';


const ScoreWeightConfig = (props) => {
    const { handleInputChange, state } = props || {};

    return (
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
                    defaultValue={state.SYNTAX}
                    name = "SYNTAX"
                    onChange = {handleInputChange}
                />
            </td>
        </tr>
    );
};

export default ScoreWeightConfig