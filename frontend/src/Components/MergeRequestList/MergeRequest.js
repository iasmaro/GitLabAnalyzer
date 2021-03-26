import React from 'react';
import { Tooltip, OverlayTrigger } from 'react-bootstrap';

import { utcToLocal } from 'Components/Utils/formatDates';

import './MergeRequestList.css';

const MergeRequest = (props) => {
    const { mergerequest, handleClick } = props || {};
    return (
        <tr className='merge-request' onClick={() => {handleClick(mergerequest?.commitDTOList, mergerequest?.mergeRequestDiffs)}} >
            <td>{mergerequest?.mergeId === -1 ? 'DUMMY' : utcToLocal(mergerequest?.mergedDate)}</td>
            <td>{mergerequest?.mergeId === -1 ? mergerequest?.mergeRequestTitle :
                <OverlayTrigger
                    placement='top'
                    overlay={
                        <Tooltip className='tooltip'>
                        {mergerequest?.mergeRequestLink}
                        </Tooltip>
                    }
                >
                    <a href={mergerequest?.mergeRequestLink} target='_blank' rel='noreferrer'>{mergerequest?.mergeRequestTitle}</a>
                </OverlayTrigger>}
            </td>
            <td>{mergerequest?.mrscore}</td>
            <td>{mergerequest?.sumOfCommitScore}</td>
            <td>{mergerequest?.commitDTOList?.length}</td>
            <td className='lines-added'>+{mergerequest?.linesAdded}</td>
            <td className='lines-removed'>-{mergerequest?.linesRemoved}</td>
        </tr>
    );
};

export default MergeRequest