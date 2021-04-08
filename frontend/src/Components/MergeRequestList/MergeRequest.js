import React from 'react';
import { Tooltip, OverlayTrigger, Badge } from 'react-bootstrap';

import { utcToLocal } from 'Components/Utils/formatDates';

import './MergeRequestList.css';

const MergeRequest = (props) => {
    const { mergerequest, handleClick, selected, index } = props || {};

    if (!mergerequest) {
        return null;
    }

    const mergeRequestRowClass = selected ? 'merge-request-selected' : 'merge-request';
    const dummyTooltip = <Tooltip>
        Dummy refers to all the commits that were made directly to the master. These commits have no associated merge requests, hence MR score is not applicable.
        </Tooltip>;
    const mergeRequestTitleDisplay = mergerequest.mergeRequestTitle?.length > 40 ? mergerequest.mergeRequestTitle.slice(0, 40) + '...' : mergerequest.mergeRequestTitle.slice(0, 40);

    return (
        <tr className={mergeRequestRowClass} onClick={() => {handleClick(mergerequest.commitDTOList, mergerequest.mergeRequestDiffs, index, mergerequest.mergeRequestTitle)}} >
            <td>
                {mergerequest.mergeId !== -1 ? utcToLocal(mergerequest.mergedDate) : 
                    <OverlayTrigger 
                        placement='bottom' 
                        overlay={dummyTooltip}>
                            <h8 className='DummyBadge'>
                                <Badge variant="light" className='DummyBadge'>
                                    DUMMY
                                </Badge>
                            </h8>
                    </OverlayTrigger>
                }
            </td>
            <td>
                {mergerequest.mergeId === -1 ? mergerequest.mergeRequestTitle :
                    <OverlayTrigger
                        placement='top'
                        overlay={
                            <Tooltip className='tooltip'>
                                {mergerequest.mergeRequestLink}
                            </Tooltip>
                        }
                    >
                        <a href={mergerequest.mergeRequestLink} target='_blank' rel='noreferrer'>{mergeRequestTitleDisplay}</a>
                    </OverlayTrigger>}
            </td>
            <td>{mergerequest.mergeId === -1 ? 'N/A' : mergerequest.mrscore}</td>
            <td>{mergerequest.sumOfCommitScore}</td>
            <td>{mergerequest.commitDTOList.length}</td>
            <td className='lines-added'>{mergerequest.mergeId === -1 ? 'N/A' : ['+', mergerequest.linesAdded].join('')}</td>
            <td className='lines-removed'>{mergerequest.mergeId === -1 ? 'N/A' : ['-', mergerequest.linesRemoved].join('')}</td>
        </tr>
    );
};

export default MergeRequest