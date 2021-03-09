import React from 'react';

const Commit = (props) => {
    const { commit, handleClick } = props || {};
    return (
        <tr onClick={() => handleClick(commit?.diffs)} className="commit">
            <td>{commit?.commitId}</td>
            <td>{commit?.commitDate}</td>
            <td>{commit?.commitAuthor}</td>
        </tr>
    );
};

export default Commit