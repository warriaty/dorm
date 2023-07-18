import { Token } from './Token';

const tokenTimestamp = (expiresIn: number) => new Date().getTime() + expiresIn;

describe('Token model', () => {
    it('Should create token object', () => {
        //given
        const tokenValue = 'test';
        const expirationTimestamp = tokenTimestamp(1000);

        //when
        const result = new Token(tokenValue, expirationTimestamp);

        //then
        expect(result.value).toEqual(tokenValue);
        expect(result.expirationTimestamp).toEqual(expirationTimestamp);
    });

    it('Should test if token is FeToken', () => {
        //given
        const unknown = { value: 'test', expirationTimestamp: tokenTimestamp(1000) };


        //when
        const result = Token.isFeToken(unknown);

        //then
        expect(result).toEqual(true);
    });

    it('Should test if token is not FeToken', () => {
        //given
        const unknown = { value: 123, expirationTimestamp: "test" };


        //when
        const result = Token.isFeToken(unknown);

        //then
        expect(result).toEqual(false);
    });

    it('Should test if expiresIn acknowledges time change', async () => {
        //given
        const expirationTimestamp = tokenTimestamp(1000);

        //when
        const result = new Token('test', expirationTimestamp);

        //then
        await new Promise<void>((resolve) => {
            setTimeout(() => {
                expect(result.expiresIn()).toBeLessThan(1000);
                expect(result.expiresIn()).toBeGreaterThan(0);
                resolve();
            }, 10)
        })
    });
});