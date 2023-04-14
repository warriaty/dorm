import React from 'react';
import { Token } from '../util/Token';
import { useLsToken } from '../hooks/UseLsToken';

interface Auth {
    isLoggedIn: boolean;
    login: (tokenString: string, expirationTimestamp: number) => void;
    logout: () => void;
    autoLogoutIn: number | null;
}

const AuthContext = React.createContext<Auth>({
    isLoggedIn: false,
    login: (tokenValue: string, expirationTimestamp: number) => {
    },
    logout: () => {
    },
    autoLogoutIn: null
});

export const AuthContextProvider: React.FC<{ children: React.ReactNode }> = (props) => {

    const {isPresent, expiresIn, saveToken, removeToken} = useLsToken();

    return <AuthContext.Provider value={
        {
            isLoggedIn: isPresent,
            login: (tokenValue, expirationTimestamp) => saveToken(new Token(tokenValue, expirationTimestamp)),
            logout: removeToken,
            autoLogoutIn: expiresIn
        }
    }>
        {props.children}
    </AuthContext.Provider>;
};

export default AuthContext;

