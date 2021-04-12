import React from 'react';
import Button from 'react-bootstrap/Button';
import { AiOutlineDelete } from 'react-icons/ai';
import { FiEdit } from 'react-icons/fi';
import './Config.css';

const Config = (props) => {
    const { config, handleClick, handleDelete, handleEdit } = props || {};
    
    return (
        <tr>
            <td className='config-name' onClick={() => {handleClick && handleClick(config)}}>{config}</td>
            <td className="config-button"><Button className="edit-config-btn" variant="warning" onClick={() => handleEdit && handleEdit(config)}><FiEdit /></Button></td>
            <td className="config-button"><Button variant="danger" className="delete-config-button" onClick={() => handleDelete && handleDelete(config)}><AiOutlineDelete /></Button></td>
        </tr>
    );
}

export default Config;