import { config } from 'Constants/constants';

const mapAliasToMember = async (mapping) => {
    const URL = `${config.PROJECT_ALIAS_API_URL}`;
    fetch(URL, { 
        method: "POST",
        headers: { "Content-type": "application/json"},
        body: JSON.stringify(mapping)
    });
}

export default mapAliasToMember;