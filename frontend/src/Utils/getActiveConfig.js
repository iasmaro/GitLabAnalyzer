import { config } from 'Constants/constants';

const getActiveConfig = async (username) => {
    const response = await fetch(`${config.USERS_API_URL}/activeConfig?userId=${username}`);
    const data = await response.text();
    if (response.ok) {
        return data;
    }
    return null;
}

export default getActiveConfig;