import { config } from 'Constants/constants';

const getMembersAndAliasesFromDatabase = async (username, projectId) => {
    const response = await fetch(`${config.PROJECT_MAPPING_API_URL}?projectId=${projectId}&userId=${username}`);
    const data = await response.json();
    if (response.ok) {
        return data;
    }
    return null;
}

export default getMembersAndAliasesFromDatabase;