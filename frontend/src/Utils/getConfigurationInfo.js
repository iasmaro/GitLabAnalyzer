import { config } from 'Constants/constants';

const getConfigurationInfo = async (username, configName) => {
    const response = await fetch(`${config.CONFIGURATION_FILE_INFO_URL + configName}?userId=${username}`);
    const data = await response.json();
    if (response.ok) {
        return data;
    }
    return null
}

export default getConfigurationInfo;