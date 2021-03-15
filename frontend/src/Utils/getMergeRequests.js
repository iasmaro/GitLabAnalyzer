import { config } from 'Constants/constants';

const getMergeRequests = async (username, memberId, projectId) => {
    const response = await fetch(`${config.MR_API_URL}${memberId}?userId=${username}&projectId=${projectId}`);
    const data = await response.json();
    if (response.ok) {
        return data;
    }
    return null
}

export default getMergeRequests;