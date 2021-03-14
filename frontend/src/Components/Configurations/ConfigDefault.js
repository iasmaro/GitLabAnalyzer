import React from 'react';

const ConfigDefault = (props) => {
    const { defaultConfig, handleClick } = props || {}; 
    return (
        <tr onClick={() => {handleClick(defaultConfig)}}>
            <td>{defaultConfig.fileName}</td>
            
        </tr>
    );
}

export default ConfigDefault;