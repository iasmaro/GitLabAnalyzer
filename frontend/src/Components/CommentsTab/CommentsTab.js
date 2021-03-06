import React from 'react';

import IssueCommentsList from 'Components/CommentsList/IssueCommentsList';
import MergeRequestCommentsList from 'Components/CommentsList/MergeRequestCommentsList';

import './CommentsTab.css'

const CommentsTab = (props) => {

    const { issueComments, mergeRequestComments } = props || {};

    return (
        <>
            <div className="comments-left">
                <MergeRequestCommentsList mergeRequestComments={mergeRequestComments}/>
            </div>
            <div className="comments-right">
                <IssueCommentsList issueComments={issueComments} />
            </div>
        </>
    );
}

export default CommentsTab;