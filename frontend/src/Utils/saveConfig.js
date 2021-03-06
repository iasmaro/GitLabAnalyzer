import { config } from 'Constants/constants';

const saveConfig = async (languages, ConfigFileWeights, username, langaugeWeights, configName) => {
    const URL = `${config.SAVE_CONFIG_URL}?userId=${username}`;

    const response = await fetch(URL, { 
        method: "POST",
        headers: { "Content-type": "application/json"},
        body: JSON.stringify({
            commentTypes: languages,
            editFactor: ConfigFileWeights,
            userId: username,
            fileFactor: langaugeWeights,
            fileName: configName,
            ignoreFileExtension: [
                'txt',
                'md',
            ],
            targetBranch: 'master'
        })
    });
    return response.ok;
}

export default saveConfig;