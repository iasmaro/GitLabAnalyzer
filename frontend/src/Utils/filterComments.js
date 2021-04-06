const filterComments = ((comment, isOwn) => {
    if (isOwn === 'All') {
        return comment
    } 
    else if (isOwn === 'Is Own' && comment?.onOwnRequestOrIssue) {
        return comment
    }
    else if (isOwn === 'Is Other' && !comment?.onOwnRequestOrIssue) {
        return comment
    }
});

export default filterComments;