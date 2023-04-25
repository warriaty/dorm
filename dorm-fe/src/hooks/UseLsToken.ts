import { useState, useEffect } from 'react';
import { Token } from '../util/Token';
import { LOCAL_STORAGE_TOKEN_KEY } from '../util/Consts';
import { InternalAxiosRequestConfig } from 'axios';

export const useLsToken = () => {
    const [token, setToken] = useState<Token | null>(LsTokenService.fetchToken());

    useEffect(() => {
        if (token) {
            const tokenExpirationTimer = setTimeout(removeToken, token.expiresIn());
            return () => {
                clearTimeout(tokenExpirationTimer);
            };
        }
    });

    const expiresIn = (): number | null => {
        return token?.expiresIn() ?? null;
    };

    const saveToken = (token: Token) => {
        LsTokenService.saveToken(token);
        setToken(token);
    };

    const removeToken = () => {
        LsTokenService.removeToken();
        setToken(null);
    };

    return { isPresent: !!token, expiresIn: expiresIn(), saveToken, removeToken };
};


export class LsTokenService {
    static fetchToken() {
        const json = localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY);
        if (json) {
            const obj = JSON.parse(json) as unknown;
            if (Token.isFeToken(obj)) {
                return (new Token(obj.value, obj.expirationTimestamp));
            }
        }
        return null;
    };

    static saveToken(token: Token) {
        localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, JSON.stringify(token));
    };

    static removeToken() {
        return localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
    };

    static authInterceptor(config: InternalAxiosRequestConfig<any>) {
        const token = LsTokenService.fetchToken();
        if (token && !config.headers.Authorization) {
            config.headers.Authorization = 'Bearer ' + token.value;
        }
        return config;
    };
}