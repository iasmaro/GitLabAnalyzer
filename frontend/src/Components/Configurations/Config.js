import React from 'react';

const Config = (props) => {
    const { config, handleClick } = props || {};
    return (
        <tr onClick={() => {handleClick(config)}}>
            <td>{config?.configName}</td>
        </tr>
    );
}

export default Config;