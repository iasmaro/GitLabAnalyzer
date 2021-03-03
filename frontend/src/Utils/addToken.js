import { config } from 'Constants/constants';

const addToken = async (username, token) => {
    const URL = `${config.USERS_API_URL}`;
    fetch(URL, { 
        method: "POST",
        headers: { "Content-type": "application/json"},
        body: JSON.stringify({
            personalAccessToken: token,
            userId: username
        })
    });
}

export default addToken;