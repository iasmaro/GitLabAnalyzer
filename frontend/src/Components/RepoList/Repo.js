import React from 'react';

const Repo = (props) => {
    const { repo } = props || {};
    return (
        <tr>
            <td>{repo?.projectName}</td>
            <td>{repo?.updatedAt}</td>
            <td><button>Analyze</button></td>
        </tr>
    );
};

export default Repo