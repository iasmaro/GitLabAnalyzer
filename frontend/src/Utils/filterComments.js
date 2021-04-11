const filterComments = ((comment, isOwn) => {
    if (isOwn === 'All') {
        return comment
    } 
    else if (isOwn === 'Own' && comment?.onOwnRequestOrIssue) {
        return comment
    }
    else if (isOwn === 'Other' && !comment?.onOwnRequestOrIssue) {
        return comment
    }
});

export default filterComments;