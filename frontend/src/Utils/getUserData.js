import { parseString, processors } from 'react-native-xml2js';
import Cookies from 'universal-cookie';

import { config } from 'Constants/constants';

const getUserData = async (ticket, dispatch) => {
    const cookies = new Cookies();
    const response = await fetch(`${config.SFU_AUTHENTICATION_URL}&ticket=${ticket}`);
    const data = await response.text();
    let authenticationSuccess;
    parseString(data, { tagNameProcessors: [processors.stripPrefix] }, (err, result) => {
        authenticationSuccess = result.serviceResponse?.authenticationSuccess;
    });
    if (authenticationSuccess && authenticationSuccess.length && authenticationSuccess[0].user && authenticationSuccess[0].user.length) {
        cookies.set('user', authenticationSuccess[0].user[0], { path: '/' });
        dispatch(authenticationSuccess[0].user[0]);
    }   
}

export default getUserData;