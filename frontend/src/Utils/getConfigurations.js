import { config } from 'Constants/constants';

const getConfigurations = async (username) => {
    const response = await fetch(`${config.CONFIGURATION_FILES_URL}?userId=${username}`);
    const data = await response.json();
    if (response.ok) {
        return data;
    }
    return null
}

export default getConfigurations;