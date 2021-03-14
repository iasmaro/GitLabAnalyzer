import { config } from 'Constants/constants';

const getToken = async (username) => {
    const response = await fetch(`${config.USERS_TOKEN_API_URL}?userId=${username}`);
    const data = await response.text();
    if (response.ok) {
        return data;
    }
    return null
}

export default getToken;