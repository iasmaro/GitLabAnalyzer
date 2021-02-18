import React from 'react';

const Commit = (props) => {
    const { commit } = props || {};
    return (
        <tr>
            <td>{commit?.id}</td>
            <td>{commit?.date}</td>
            <td>{commit?.author}</td>
            <td>{commit?.message}</td>
        </tr>
    );
};

export default Commit