import { config } from 'Constants/constants';

const updateAliasForMembers = async (mapping) => {
    const URL = `${config.PROJECT_ALIAS_API_URL}`;
    fetch(URL, { 
        method: "PUT",
        headers: { "Content-type": "application/json"},
        body: JSON.stringify(mapping)
    });
}

export default updateAliasForMembers;