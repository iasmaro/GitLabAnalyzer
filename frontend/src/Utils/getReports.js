import { config } from 'Constants/constants';

const getReports = async (username) => {
    const response = await fetch(`${config.REPOS_API_URL}?userId=${username}`);
    const data = await response.json();
    if (response.ok) {
        return data;
    }
    return null
}

export default getReports;