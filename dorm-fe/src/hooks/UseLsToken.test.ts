import { act, renderHook } from '@testing-library/react';
import { LsTokenService, useLsToken } from './UseLsToken';
import { Token } from '../util/Token';
import { LOCAL_STORAGE_TOKEN_KEY } from '../util/Consts';


const tokenTimestamp = (expiresIn: number) => new Date().getTime() + expiresIn;

describe('UseLsToken custom hook', function () {

    beforeAll(() => {
        jest.useFakeTimers();
    });

    afterAll(() => {
        jest.useRealTimers();
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
        act(() => hook.result.current.saveToken(new Token("test", tokenTimestamp(1000))))

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

