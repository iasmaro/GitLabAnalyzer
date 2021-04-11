import React, { useState } from 'react';
import { Table, Button } from 'react-bootstrap';

import { message } from 'Constants/constants';
import { useSortableDataObject, getClassNamesFor } from 'Utils/sortTables';

import Comment from 'Components/CommentsList/Comment';
import filterComments from 'Utils/filterComments';
import './IssueCommentsList.css';

const IssueCommentsList = (props) => {
    const { issueComments } = props || {};
    const { items, requestSortObject, sortConfig  } = useSortableDataObject(issueComments);
    const [isOwn, setIsOwn] = useState('All');

    issueComments?.forEach(element => {
        element.wordCount = element.commentDescription.match(/(\w+)/g).length;
    });
    
    const handleClick = () => {
        if (isOwn === 'All') {
            setIsOwn('Own');
        }
        else if (isOwn === 'Own') {
            setIsOwn('Other');
        }
        else if (isOwn === 'Other') {
            setIsOwn('All');
        }
    }

    return (
        <Table className='issue-comments-table' borderless hover variant='light'>
            <thead>
                <tr className='issue-table-headers'>
                    <th colSpan='3'>Issue Comments</th>
                    <th colSpan='2'>Total Comments: {issueComments?.length || 0}</th>
                </tr>
            </thead>
            <thead>
                <tr className='issue-row-headers'>
                <th><Button variant='secondary' onClick={handleClick}>{isOwn}</Button></th>
                    <th className={getClassNamesFor(sortConfig, 'creationDate')} onClick={() => requestSortObject('creationDate')}>Created At</th>
                    <th className={getClassNamesFor(sortConfig, 'title')} onClick={() => requestSortObject('title')}>Title</th>
                    <th className={getClassNamesFor(sortConfig, 'commentDescription')} onClick={() => requestSortObject('commentDescription')}>Comment</th>
                    <th className={getClassNamesFor(sortConfig, 'wordCount')} onClick={() => requestSortObject('wordCount')}>Word Count</th>
                </tr>
            </thead>
            <tbody>
                {!issueComments?.length ? (
                    <td colSpan='8' >{message.NO_COMMENTS}</td>
                )
                :
                items.filter((comment) => filterComments(comment, isOwn)).map((comment) => (
                    <Comment {...comment} isMR={false}/>
                ))}
            </tbody>
        </Table>
    )
}


export default IssueCommentsList;
