import React from 'react';
import { Form } from 'react-bootstrap';

import {ConfigLabels} from 'Constants/constants';

import './ConfigForm.css';

const DefaultFileTypeConfig = (props) => {
    const { handleInputChange, state } = props || {};

    return (
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
    );
};

export default DefaultFileTypeConfig