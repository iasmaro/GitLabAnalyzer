import React from 'react';

const Commit = (props) => {
    const { commit, handleClick } = props || {};
    return (
        <tr onClick={() => handleClick(commit?.commitDiffs)} className="commit">
            <td>{commit?.commitDate}</td>
            <td>{commit?.commitMessage}</td>
            <td>{commit?.commitScore}</td>
            <td>{commit?.commitAuthor}</td>
            <td>{commit?.linesAdded}</td>
            <td>{commit?.linesRemoved}</td>
        </tr>
    );
};

export default Commit
