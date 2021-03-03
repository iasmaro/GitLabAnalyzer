import React from 'react';

const Config = (props) => {
    const { config, handleClick } = props || {};
    return (
        <tr onClick={() => {handleClick(config?.configName)}}>
            <td>{config?.configName}</td>
        </tr>
    );
}

export default Config;