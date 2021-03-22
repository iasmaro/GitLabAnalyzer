import React from 'react';
import Button from 'react-bootstrap/Button';


const Config = (props) => {
    const { config, handleClick, handleDelete } = props || {};
    
    return (
        <tr>
            <td onClick={() => {handleClick && handleClick(config)}}>{config}</td>
            <td><Button variant="danger" className="delete-config-button" onClick={() => handleDelete && handleDelete(config)}>Delete</Button></td>
        </tr>
    );
}

export default Config;