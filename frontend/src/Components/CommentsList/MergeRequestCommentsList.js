import React, { useState } from 'react';
import { Table } from 'react-bootstrap';

import { message } from 'Constants/constants';
import { useSortableDataObject, getClassNamesFor } from 'Utils/sortTables';

import Comment from 'Components/CommentsList/Comment';
import filterComments from 'Utils/filterComments';
import './MergeRequestComments.css'

const MergeRequestCommentsList = (props) => {
    const { mergeRequestComments } = props || {};
    const { items, requestSortObject, sortConfig  } = useSortableDataObject(mergeRequestComments);
    const [isOwn, setIsOwn] = useState('All')

    mergeRequestComments.forEach(element => {
        element.wordCount = element.commentDescription.match(/(\w+)/g).length;
    });

    const handleClick = () => {
        if (isOwn === 'All') {
            setIsOwn('Is Own');
        }
        else if (isOwn === 'Is Own') {
            setIsOwn('Is Other');
        }
        else if (isOwn === 'Is Other') {
            setIsOwn('All');
        }
    }

    return (
        <div className='merge-request-comments-container'>
            <Table bordered hover variant='light'>
                {console.log(mergeRequestComments)}
                <thead>
                    <tr>
                        <th colSpan='4' className='mrTitle'>Merge Request Comments</th>
                        <th colSpan='1' className='mrTitle'>Total Comments: {mergeRequestComments?.length || 0}</th>
                    </tr>
                </thead>
                <thead>
                    <tr className='mr-headers'>
                        <th><button onClick={handleClick}>{isOwn}</button></th>
                        <th className={getClassNamesFor(sortConfig, 'creationDate')} onClick={() => requestSortObject('creationDate')}>Created At</th>
                        <th className={getClassNamesFor(sortConfig, 'url')} onClick={() => requestSortObject('url')}>Title</th>
                        <th className={getClassNamesFor(sortConfig, 'commentDescription')} onClick={() => requestSortObject('commentDescription')}>Comment</th>
                        <th className={getClassNamesFor(sortConfig, 'wordCount')} onClick={() => requestSortObject('wordCount')}>Word Count</th>
                    </tr>
                </thead>
                <tbody>
                    {!mergeRequestComments?.length ? (
                        <td colSpan='8' >{message.NO_COMMENTS}</td>
                    )
                    :
                    items.filter((comment) => filterComments(comment, isOwn)).map((comment) => (
                        <Comment creationDate={comment?.creationDate} onOwnRequestOrIssue={comment?.onOwnRequestOrIssue} url={comment?.url} commentDescription={comment?.commentDescription}/>
                    ))}
                </tbody>
            </Table>
        </div>
    )
}


export default MergeRequestCommentsList;
