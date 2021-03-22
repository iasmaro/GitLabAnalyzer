import React from 'react';

import { utcToLocal } from 'Components/Utils/formatDates';

import './MergeRequestList.css';

const MergeRequest = (props) => {
    const { mergerequest, handleClick } = props || {};
    return (
        <tr className="merge-request" onClick={() => {handleClick(mergerequest?.commitDTOList, mergerequest?.mergeRequestDiffs)}} >
            <td>{mergerequest?.mergeId === -1 ? "DUMMY" : utcToLocal(mergerequest?.mergedDate)}</td>
            <td>{mergerequest?.mergeRequestTitle}</td>
            <td>{mergerequest?.mrscore}</td>
            <td>{mergerequest?.sumOfCommitScore}</td>
            <td>{mergerequest?.commitDTOList?.length}</td>
            <td className='lines-added'>+{mergerequest?.linesAdded}</td>
            <td className='lines-removed'>-{mergerequest?.linesRemoved}</td>
        </tr>
    );
};

export default MergeRequest