import React from 'react';

import IssueCommentsList from 'Components/CommentsList/IssueCommentsList';
import MergeRequestCommentsList from 'Components/CommentsList/MergeRequestCommentsList';

import './CommentsTab.css'

const CommentsTab = (props) => {

    const { issueComments, mergeRequestComments } = props || {};

    return (
        <div className="comments-tab">
            <div className="comments-top">
                <MergeRequestCommentsList mergeRequestComments={mergeRequestComments}/>
            </div>
            <div className="comments-bottom">
                <IssueCommentsList issueComments={issueComments} />
            </div>
        </div>
    );
}

export default CommentsTab;