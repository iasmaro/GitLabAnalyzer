import React from 'react';

const Config = (props) => {
    const { config, handleClick } = props || {};
    return (
        <tr onClick={() => {handleClick && handleClick(config)}}>
            <td>{config}</td>
        </tr>
    );
}

export default Config;