import { config } from 'Constants/constants';

const getEnd = async (username) => {
    const response = await fetch(`${config.END_URL}?userId=${username}`);
    const data = await response.json();
    if (response.ok) {
        return data;
    }
    return null;
}

export default getEnd;