import React from 'react';
import { Tooltip, OverlayTrigger } from 'react-bootstrap';

import { utcToLocal } from 'Components/Utils/formatDates';

import './CommitsList.css';

const Commit = (props) => {
    const { commit, handleClick, selected, index, student, databaseMembersAndAliases } = props || {};
    const commitRowClass = selected ? 'commit-selected' : 'commit';
    const member = databaseMembersAndAliases?.find(mapping => mapping.memberId === student);
    const commitAuthorClass = member?.alias?.includes(commit?.commitAuthor) ? 'author-highlighted' : 'author';
    const commitMessageDisplay = commit?.commitMessage?.length > 40 ? commit?.commitMessage?.slice(0, 40) + '...' : commit?.commitMessage?.slice(0, 40);

    return (
        <tr className={commitRowClass} onClick={() => handleClick(commit?.commitDiffs, index, commit?.commitMessage)} >
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
                    <a href={commit?.commitLink} target='_blank' rel='noreferrer'>{commitMessageDisplay}</a>
                </OverlayTrigger>
            </td>
            <td>{commit?.commitScore}</td>
            <td className={commitAuthorClass}>{commit?.commitAuthor}</td>
            <td className='lines-added'>+{commit?.linesAdded}</td>
            <td className='lines-removed'>-{commit?.linesRemoved}</td>
        </tr>
    );
};

export default Commit
