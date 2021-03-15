import { config } from 'Constants/constants';

const deleteConfig = async (username, fileName) => {
    const response = await fetch(`${config.CONFIGURATION_FILES_URL}?userId=${username}&fileName=${fileName}`, {method: 'DELETE'});
    const data = await response.text();
    if (response.ok) {
        return data;
    }
    return null;
}

export default deleteConfig;