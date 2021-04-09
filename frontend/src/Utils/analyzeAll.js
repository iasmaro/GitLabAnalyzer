import { config } from 'Constants/constants';

const analyzeAll = async (username, projectId) => {
    const response = await fetch(`${config.ANALYSIS_API_URL}?projectId=${projectId}&userId=${username}`);
    const data = await response.json();
    if (response.ok) {
        return data;
    }
    return null
}

export default analyzeAll;