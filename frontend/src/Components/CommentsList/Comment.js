import React from 'react';

import { utcToLocal } from 'Components/Utils/formatDates';


const Comment = (props) => {
    const { creationDate, onOwnRequestOrIssue, url, commentDescription } = props || {};
    console.log(creationDate, onOwnRequestOrIssue, url, commentDescription);
    return (
        <tr>
            <td>{utcToLocal(creationDate)}</td>
            <td>{onOwnRequestOrIssue}</td>
            <td>
                <a href={url} target='_blank' rel='noreferrer'>View</a>
            </td>
            <td className='lines-added'>{commentDescription}</td>
        </tr>
    );
};

export default Comment;
