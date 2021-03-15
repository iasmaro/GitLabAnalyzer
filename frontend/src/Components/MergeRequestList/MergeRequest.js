import React from 'react';

const MergeRequest = (props) => {
    const { mergerequest, handleClick } = props || {};
    return (
        <tr className="merge-request" onClick={() => {handleClick(mergerequest?.commitDTOList, mergerequest?.mergeRequestDiffs)}} >
            <td>{mergerequest?.mergeId}</td>
            <td>{mergerequest?.createdDate}</td>
            <td>{mergerequest?.updatedDate}</td>
            <td>{mergerequest?.mergedDate}</td>
        </tr>
    );
};

export default MergeRequest