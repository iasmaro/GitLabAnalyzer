import React from 'react';

const Commit = (props) => {
    const { commit } = props || {};
    return (
        <tr>
            <td>{commit?.commitId}</td>
            <td>{commit?.commitDate}</td>
            <td>{commit?.commitAuthor}</td>
        </tr>
    );
};

export default Commit