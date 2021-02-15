import React, { useState, useContext } from 'react';
import Cookies from 'universal-cookie';

const UserStateContext = React.createContext();
const UserDispatchContext = React.createContext();

const UserProvider = (props) => {
    const cookies = new Cookies();
    const { children } = props;
    const [user, dispatch] = useState(cookies.get('user'));

    const setUser = (value) => {
        dispatch(value);
    }

    return (
        <UserStateContext.Provider value={user}>
            <UserDispatchContext.Provider value={setUser}>
                {children}
            </UserDispatchContext.Provider>
        </UserStateContext.Provider>
    )
}

const useUserState = () => {
    const context = useContext(UserStateContext);
    return context;
}

const useUserDispatch = () => {
    const dispatch = useContext(UserDispatchContext);
    return dispatch;
}

export { UserProvider, useUserState, useUserDispatch };