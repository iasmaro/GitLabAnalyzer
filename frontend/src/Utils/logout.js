import Cookies from 'universal-cookie';

const logout = (dispatch) => {
    const cookies = new Cookies();
    cookies.remove('user', { path: '/' });
    dispatch(null);
}

export default logout;