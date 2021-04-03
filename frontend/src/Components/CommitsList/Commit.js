import React from 'react';
import { Tooltip, OverlayTrigger } from 'react-bootstrap';

import { utcToLocal } from 'Components/Utils/formatDates';

import './CommitsList.css';

const Commit = (props) => {
    const { commit, handleClick, selected, index } = props || {};
    const commitRowClass = selected ? 'commit-selected' : 'commit';
    return (
        <tr className={commitRowClass} onClick={() => handleClick(commit?.commitDiffs, index)} >
            <td>{utcToLocal(commit?.commitDate)}</td>
            <td>
                <OverlayTrigger
                    placement='top'
                    overlay={
                        <Tooltip className='tooltip'>
                        {commit?.commitLink}
                        </Tooltip>
                    }
                >
                    <a href={commit?.commitLink} target='_blank' rel='noreferrer'>{commit?.commitMessage}</a>
                </OverlayTrigger>
            </td>
            <td>{commit?.commitScore}</td>
            <td>{commit?.commitAuthor}</td>
            <td className='lines-added'>+{commit?.linesAdded}</td>
            <td className='lines-removed'>-{commit?.linesRemoved}</td>
        </tr>
    );
};

export default Commit
