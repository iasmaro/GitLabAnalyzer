import { config } from 'Constants/constants';

const getAllCommits = async (username, memberId, start, end, projectId) => {
    const response = await fetch(`${config.COMMITS_API_URL}${memberId}?userId=${username}&projectId=${projectId}&start=${start}&end=${end}`);
    const data = await response.json();
    if (response.ok) {
        return data;
    }
    return null
}

export default getAllCommits;