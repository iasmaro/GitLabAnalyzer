import { config } from 'Constants/constants';

const saveUser = async (username, token, gitlabServer, activeConfiguration = "") => {
    const URL = `${config.USERS_API_URL}`;

    const response = await fetch(URL, { 
        method: "POST",
        headers: { "Content-type": "application/json"},
        body: JSON.stringify({
            gitlabServer: gitlabServer,
            personalAccessToken: token,
            userId: username,
            activeConfig: activeConfiguration
        })
    });
    
    return response.ok;
}

export default saveUser;