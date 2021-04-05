import { config } from 'Constants/constants';

const getCommentsForIssues = async (username, memberId, projectId) => {
    const response = await fetch(`${config.ISSUE_COMMENTS_URL}${memberId}?userId=${username}&projectId=${projectId}`);
    const data = await response.json();
    if (response.ok) {
        return data;
    }
    return null
}

export default getCommentsForIssues;