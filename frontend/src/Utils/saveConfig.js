import { config } from 'Constants/constants';

const saveConfig = async (languages, ConfigFileWeights, username, startDate, endDate, langaugeWeights, configName) => {
    const URL = `${config.SAVE_CONFIG_URL}?userId=${username}`;
    let temp = JSON.stringify({
        commentTypes: languages,
        editFactor: ConfigFileWeights,
        userId: username,
        start: startDate,
        end: endDate,
        fileFactor: langaugeWeights,
        fileName: configName,
        ignoreFileExtension: [
            'txt',
            'md',
        ],
        targetBranch: 'master'
    })
    console.log(temp)

    fetch(URL, { 
        method: "POST",
        headers: { "Content-type": "application/json"},
        body: JSON.stringify({
            commentTypes: languages,
            editFactor: ConfigFileWeights,
            userId: username,
            start: startDate,
            end: endDate,
            fileFactor: langaugeWeights,
            fileName: configName,
            ignoreFileExtension: [
                'txt',
                'md',
            ],
            targetBranch: 'master'
        })
    });
}

export default saveConfig;