import { config } from 'Constants/constants';

const getStart = async (username) => {
    const response = await fetch(`${config.START_URL}?userId=${username}`);
    const data = await response.json();
    if (response.ok) {
        return data;
    }
    return null;
}

export default getStart;