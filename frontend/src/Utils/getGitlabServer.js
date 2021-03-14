import { config } from 'Constants/constants';

const getGitlabServer = async (username) => {
    const response = await fetch(`${config.USERS_API_URL}/server?userId=${username}`);
    const data = await response.text();
    if (response.ok) {
        return data;
    }
    return null;
}

export default getGitlabServer;