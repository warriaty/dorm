import { act, renderHook } from '@testing-library/react';
import { LsTokenService, useLsToken } from './UseLsToken';
import { Token } from '../util/Token';
import { LOCAL_STORAGE_TOKEN_KEY } from '../util/Consts';
import { AxiosHeaders, InternalAxiosRequestConfig } from 'axios';


const tokenTimestamp = (expiresIn: number) => new Date().getTime() + expiresIn;


describe('UseLsToken custom hook', function () {

    beforeAll(() => {
        jest.useFakeTimers();
    });

    afterAll(() => {
        jest.useRealTimers();
    });

    afterEach(() => {
        localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
    });

    it('Should auto-load token', () => {
        //given
        LsTokenService.saveToken(new Token(LOCAL_STORAGE_TOKEN_KEY, tokenTimestamp(1)));

        //when
        const hook = renderHook(() => useLsToken());

        //then
        expect(hook.result.current).toHaveProperty('isPresent', true);
        expect(hook.result.current.expiresIn).toBeGreaterThan(0);
    });

    it('Should auto-remove token', () => {
        //given
        LsTokenService.saveToken(new Token(LOCAL_STORAGE_TOKEN_KEY, tokenTimestamp(1000)));

        //when
        const hook = renderHook(() => useLsToken());
        act(() => {
            jest.runAllTimers();
        });

        //then
        expect(hook.result.current).toHaveProperty('isPresent', false);
        expect(hook.result.current.expiresIn).toBeNull();
    });

    it('Should save and set token', () => {
        //given
        const hook = renderHook(() => useLsToken());

        //when
        act(() => hook.result.current.saveToken(new Token('test', tokenTimestamp(1000))));

        //then
        expect(hook.result.current).toHaveProperty('isPresent', true);
        expect(hook.result.current.expiresIn).toBeGreaterThan(0);
    });

    it('Should remove token', () => {
        //given
        const hook = renderHook(() => useLsToken());

        //when
        act(() => hook.result.current.removeToken());

        //then
        expect(hook.result.current).toHaveProperty('isPresent', false);
        expect(hook.result.current.expiresIn).toBeNull();
    });
});

describe('LsTokenService', function () {

    afterEach(() => {
        localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
    });

    it('Should fetch token', () => {
        //given
        localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY,
            JSON.stringify(new Token('test', tokenTimestamp(1000))));

        //when
        const token = LsTokenService.fetchToken();

        //then
        expect(token).toHaveProperty('value', 'test');
        expect(token?.expiresIn()).toBeGreaterThan(0);
    });

    it('Should return null if token is missing', () => {
        //given
        localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);

        //when
        const token = LsTokenService.fetchToken();

        //then
        expect(token).toBeNull();
    });

    it('Should save token ', () => {
        //given
        const token = new Token('test', tokenTimestamp(1000));

        //when
        LsTokenService.saveToken(token);

        //then
        const result = JSON.parse(localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY) ?? '');
        expect(result.value).toEqual('test');
        expect(result.expirationTimestamp).toBeGreaterThan(0);
    });

    it('Should replace old token', () => {
        //given
        localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY,
            JSON.stringify(new Token('test', tokenTimestamp(1000))));

        //when
        LsTokenService.saveToken(new Token('new token', tokenTimestamp(1000)));

        //then
        const result = JSON.parse(localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY) ?? '');
        expect(result.value).toEqual('new token');
    });

    it('Should remove token', () => {
        //given
        localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY,
            JSON.stringify(new Token('test', tokenTimestamp(1000))));

        //when
        LsTokenService.removeToken();

        //then
        const result = localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY);
        expect(result).toBeNull();
    });

    it('Should append (auth) header with token', () => {
        //given
        LsTokenService.saveToken(new Token('test', tokenTimestamp(1000)));
        const config: InternalAxiosRequestConfig = { headers: new AxiosHeaders() };

        //when
        LsTokenService.authInterceptor(config);

        //then
        expect(config.headers.Authorization).toEqual('Bearer test');
    });

    it('Should skip (auth) header when token is missing', () => {
        //given
        LsTokenService.removeToken();
        const config: InternalAxiosRequestConfig = { headers: new AxiosHeaders() };

        //when
        LsTokenService.authInterceptor(config);

        //then
        expect(config.headers.Authorization).toBeUndefined();
    });
});

