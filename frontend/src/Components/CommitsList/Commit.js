import React from 'react';

import { utcToLocal } from 'Components/Utils/formatDates';

import './CommitsList.css';

const Commit = (props) => {
    const { commit, handleClick } = props || {};
    return (
        <tr onClick={() => handleClick(commit?.commitDiffs)} className='commit'>
            <td>{utcToLocal(commit?.commitDate)}</td>
            <td><a href={commit?.commitLink} target='_blank' rel='noreferrer'>Link</a></td>
            <td>{commit?.commitMessage}</td>
            <td>{commit?.commitScore}</td>
            <td>{commit?.commitAuthor}</td>
            <td className='lines-added'>+{commit?.linesAdded}</td>
            <td className='lines-removed'>-{commit?.linesRemoved}</td>
        </tr>
    );
};

export default Commit
