import React from 'react';

import { utcToLocal } from 'Components/Utils/formatDates';


const Comment = (props) => {
    const { creationDate, onOwnRequestOrIssue, url, commentDescription } = props || {};
    return (
        <tr>
            <td>{onOwnRequestOrIssue ? ("Yes") : ("No")}</td>
            <td>{utcToLocal(creationDate)}</td>
            <td>
                <a href={url} target='_blank' rel='noreferrer'>View</a>
            </td>
            <td className='lines-added'>{commentDescription}</td>
            <td>{commentDescription.match(/(\w+)/g).length}</td>
        </tr>
    );
};

export default Comment;
