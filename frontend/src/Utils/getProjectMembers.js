import { config } from 'Constants/constants';

const getProjectMembers = async (username, projectId) => {
    const response = await fetch(`${config.PROJECT_MEMBERS_API_URL}?projectId=${projectId}&userId=${username}`);
    const data = await response.json();
    if (response.ok) {
        return data;
    }
    return null
}

export default getProjectMembers;