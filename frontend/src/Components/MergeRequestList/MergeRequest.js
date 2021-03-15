import React from 'react';

import { utcToLocal } from 'Components/Utils/formatDates';

const MergeRequest = (props) => {
    const { mergerequest, handleClick } = props || {};
    console.log(mergerequest);
    return (
        <tr className="merge-request" onClick={() => {handleClick(mergerequest?.commitDTOList, mergerequest?.mergeRequestDiffs)}} >
            <td>{utcToLocal(mergerequest?.mergedDate)}</td>
            <td>{mergerequest?.mergeRequestTitle}</td>
            <td>{mergerequest?.mrscore}</td>
            <td>{mergerequest?.sumOfCommitScore}</td>
            <td>{mergerequest?.commitDTOList?.length}</td>
            <td>{mergerequest?.linesAdded}</td>
            <td>{mergerequest?.linesRemoved}</td>
        </tr>
    );
};

export default MergeRequest