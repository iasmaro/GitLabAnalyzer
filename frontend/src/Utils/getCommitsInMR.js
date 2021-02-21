import { config } from 'Constants/constants';

const getCommitsInMR = async (username, projectId, mergeRequestId) => {
    const response = await fetch(`${config.MR_COMMITS_API_URL}${mergeRequestId}?userId=${username}&projectId=${projectId}`);
    const data = await response.json();
    if (response.ok) {
        return data;
    }
    return null
}

export default getCommitsInMR;