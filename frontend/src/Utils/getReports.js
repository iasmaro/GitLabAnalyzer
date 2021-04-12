import { config } from 'Constants/constants';

const getReports = async (username) => {
    const response = await fetch(`${config.PAST_REPORTS_API_URL}/userReports?userId=${username}`);
    const data = await response.json();
    if (response.ok) {
        return data;
    }
    return null
}

export default getReports;