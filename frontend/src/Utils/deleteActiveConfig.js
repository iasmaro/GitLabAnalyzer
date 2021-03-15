import { config } from 'Constants/constants';

const deleteActiveConfig = async (username) => {
    const response = await fetch(`${config.USERS_API_URL}/activeConfig?userId=${username}`, {method: 'DELETE'});
    const data = await response.text();
    if (response.ok) {
        return data;
    }
    return null;
}

export default deleteActiveConfig;