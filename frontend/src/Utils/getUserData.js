import { parseString, processors } from 'react-native-xml2js';
import Cookies from 'universal-cookie';

const getUserData = async (ticket, dispatch) => {
    const cookies = new Cookies();
    const response = await fetch(`https://cas.sfu.ca/cas/serviceValidate?service=http://localhost:3000/&ticket=${ticket}`);
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