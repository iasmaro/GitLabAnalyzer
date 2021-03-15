import { config, SCHEME } from 'Constants/constants';

const updateUser = async (username, token, gitlabServer, activeConfiguration = "") => {
    const URL = `${config.USERS_API_URL}`;
    if (gitlabServer.substring(0,7) !== SCHEME.HTTP && gitlabServer.substring(0,8) !== SCHEME.HTTPS) {
        gitlabServer = SCHEME.HTTP + gitlabServer;
    }
    fetch(URL, { 
        method: "PUT",
        headers: { "Content-type": "application/json"},
        body: JSON.stringify({
            gitlabServer: gitlabServer,
            personalAccessToken: token,
            userId: username,
            activeConfig: activeConfiguration
        })
    });
}

export default updateUser;