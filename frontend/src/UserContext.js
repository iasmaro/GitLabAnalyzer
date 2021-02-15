import React, { useState, useContext } from 'react';

const UserStateContext = React.createContext();
const UserDispatchContext = React.createContext();

const UserProvider = (props) => {
    const { children } = props;
    const [user, dispatch] = useState();

    return (
        <UserStateContext.Provider value={user}>
            <UserDispatchContext.Provider value={dispatch}>
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