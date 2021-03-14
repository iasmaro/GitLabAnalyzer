import { config } from 'Constants/constants';

const updateUser = async (username, token, gitlabServer) => {
    const URL = `${config.USERS_API_URL}`;
    if (gitlabServer.substring(0,7) !== 'http://' && gitlabServer.substring(0,8) !== 'https://') {
        gitlabServer = 'http://' + gitlabServer;
    }
    fetch(URL, { 
        method: "PUT",
        headers: { "Content-type": "application/json"},
        body: JSON.stringify({
            gitlabServer: gitlabServer,
            personalAccessToken: token,
            userId: username
        })
    });
}

export default updateUser;