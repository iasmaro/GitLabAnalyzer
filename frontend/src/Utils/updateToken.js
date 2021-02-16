import { config } from 'Constants/constants';

const updateToken = async (username, token) => {
    const URL = `${config.USERS_API_URL}`;
    fetch(URL, { 
        method: "PUT",
        headers: { "Content-type": "application/json"},
        body: JSON.stringify({
            personalAccessToken: token,
            userId: username
        })
    });
}

export default updateToken;