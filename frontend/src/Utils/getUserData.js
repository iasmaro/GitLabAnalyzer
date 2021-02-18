import { parseString, processors } from 'react-native-xml2js';
import Cookies from 'universal-cookie';

import { config } from 'Constants/constants';

const getUserData = async (ticket, dispatch) => {
    const cookies = new Cookies();
    const response = await fetch(`${config.SFU_AUTHENTICATION_URL}&ticket=${ticket}`);
    const data = await response.text();
    if (data && data.length) {
        cookies.set('user', data, { path: '/' });
        dispatch(data);
    }   
}

export default getUserData;