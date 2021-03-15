import React from 'react';

const ConfigDefault = (props) => {
    const { defaultConfig, handleClick } = props || {}; 
    return (
        <tr onClick={() => {handleClick && handleClick(defaultConfig)}}>
            <td colSpan='2'>{defaultConfig.fileName}</td>
        </tr>
    );
}

export default ConfigDefault;