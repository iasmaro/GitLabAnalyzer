import React, { useState } from 'react';
import { Table } from 'react-bootstrap';

import { message } from 'Constants/constants';
import { useSortableDataObject, getClassNamesFor } from 'Utils/sortTables';

import Comment from 'Components/CommentsList/Comment';

const IssueCommentsList = (props) => {
    const { issueComments } = props || {};
    const { items, requestSortObject, sortConfig  } = useSortableDataObject(issueComments);
    return (
        <div className='merge-request-list-container'>
            <Table bordered hover variant='light'>
                <thead>
                    <tr>
                        <th colSpan='3' className='mrTitle'>Issue Comments</th>
                        <th colSpan='1' className='mrTitle'>Total: {issueComments?.length || 0}</th>
                    </tr>
                </thead>
                <thead>
                    <tr className='mr-headers'>
                        <th className={getClassNamesFor(sortConfig, 'creationDate')} onClick={() => requestSortObject('creationDate')}>Created At</th>
                        <th className={getClassNamesFor(sortConfig, 'onOwnRequestOrIssue')} onClick={() => requestSortObject('onOwnRequestOrIssue')}>Is Own</th>
                        <th className={getClassNamesFor(sortConfig, 'url')} onClick={() => requestSortObject('url')}>Link</th>
                        <th className={getClassNamesFor(sortConfig, 'commentDescription')} onClick={() => requestSortObject('commentDescription')}>Comment</th>
                    </tr>
                </thead>
                <tbody>
                    {!issueComments?.length ? (
                        <td colSpan='8' >{message.NO_COMMENTS}</td>
                    )
                    :
                    items.map((comment) => (
                        <Comment creationDate={comment?.creationDate} onOwnRequestOrIssue={comment?.onOwnRequestOrIssue} url={comment?.url} commentDescription={comment?.commentDescription}/>
                    ))}
                </tbody>
            </Table>
        </div>
    )
}


export default IssueCommentsList;
