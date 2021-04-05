import React from 'react';


import './CommentsTab.css'

const CommentsTab = (props) => {

    const { issueComments, mergeRequestComments } = props || {};

    return (
        <div className="comments-tab">
            {console.log(issueComments)}
            {console.log(mergeRequestComments)}
        </div>
    );
}

export default CommentsTab;