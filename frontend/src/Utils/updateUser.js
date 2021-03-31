import { config } from 'Constants/constants';

const updateUser = async (username, token, gitlabServer, activeConfiguration = "", startDate, endDate) => {
    const URL = `${config.USERS_API_URL}`;

    const response = await fetch(URL, { 
        method: "PUT",
        headers: { "Content-type": "application/json"},
        body: JSON.stringify({
            gitlabServer: gitlabServer,
            personalAccessToken: token,
            userId: username,
            activeConfig: activeConfiguration,
            start: startDate,
            end: endDate,
        })
    });

    return response.ok;
}

export default updateUser;