import React from 'react';
import { OverlayTrigger, Tooltip } from 'react-bootstrap';
import { utcToLocal } from 'Components/Utils/formatDates';

import './Comment.css';

const Comment = (props) => {
    const { creationDate, onOwnRequestOrIssue, title, url, commentDescription, isMR } = props || {};
    return (
        <tr className='comment-row'>
            {isMR ? 
                <td className='is-own'>{onOwnRequestOrIssue ? ("Own MR") : ("Other's MR")}</td> : 
                <td className='is-own'>{onOwnRequestOrIssue ? ("Own Issue") : ("Other's Issue")}</td>
            }
            <td className='creation-date'>{utcToLocal(creationDate)}</td>
            <td className='title'>
                <a href={url} target='_blank' rel='noreferrer'>{title}</a>
            </td>
            <td className='comment-description'>
                {commentDescription.length < 100 ? commentDescription : 
                    <OverlayTrigger
                        placement="top"
                        overlay={
                            <Tooltip className='tooltip'>
                                {commentDescription}
                            </Tooltip>
                        }
                    >
                        <p>{commentDescription.slice(0, 100) + '...'}</p>
                    </OverlayTrigger>
                }
            </td>
            <td className='word-count'>{commentDescription.match(/(\w+)/g).length}</td>
        </tr>
    );
};

export default Comment;
