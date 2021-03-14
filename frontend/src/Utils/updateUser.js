import { config } from 'Constants/constants';

const updateUser = async (username, token, gitlabServer) => {
    const URL = `${config.USERS_API_URL}`;
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