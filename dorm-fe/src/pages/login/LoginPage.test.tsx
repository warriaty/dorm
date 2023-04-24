import React from 'react';
import { render, } from '@testing-library/react';
import LoginPage from './LoginPage';
import { ionFireEvent } from '@ionic/react-test-utils';

const mockHistoryPush = jest.fn();

jest.mock('react-router', () => ({
    useHistory: () => ({
        push: mockHistoryPush,
    })
}));

jest.mock('../../util/HttpService', () =>
    ({
        post: Function
    })
);

const mockLoginRequest = () => jest.spyOn(require('../../util/HttpService'), 'post')
    .mockReturnValue(new Promise(resolve =>
        resolve({
                data: {
                    tokenValue: 'test',
                    expirationTimestamp: 'test'
                }
            }
        )));

describe('LoginPage component', () => {
    describe('Sign in scenarios', function () {

        it('Should send post to login url on submit', async () => {
            //given
            jest.useFakeTimers();
            const httpPost = mockLoginRequest();
            const { findByText, findByTitle } = render(<LoginPage login={true}/>);

            //when
            await findByTitle('email').then(input => ionFireEvent.ionInput(input, 'test'));
            await findByTitle('password').then(input => ionFireEvent.ionInput(input, 'test'));
            setTimeout(() => findByText('OK').then(ionFireEvent.submit));


            //then
            setTimeout(() => expect(httpPost).toBeCalledWith('/login', { 'email': 'test', 'password': 'test' }));
        });

        it('Should navigate to dashboard on sign in', async () => {
            //given
            mockLoginRequest();
            const { findByText } = render(<LoginPage login={true}/>);

            //when
            await findByText('OK').then(ionFireEvent.submit);

            //then
            expect(mockHistoryPush).toBeCalledWith('dashboard');
        });
    });

    describe('Sign up scenarios', function () {

        it('Should send post to register url on submit', async () => {
            //given
            jest.useFakeTimers();
            const httpPost = mockLoginRequest();
            const { findByText, findByTitle } = render(<LoginPage login={false}/>);

            //when
            await findByTitle('email').then(input => ionFireEvent.ionInput(input, 'test'));
            await findByTitle('password').then(input => ionFireEvent.ionInput(input, 'test'));
            setTimeout(() => findByText('OK').then(ionFireEvent.submit));

            //then
            setTimeout(() => expect(httpPost).toBeCalledWith('user/register', { 'email': 'test', 'password': 'test' }));
        });

        it('Should navigate to dashboard on sign up', async () => {
            //given
            mockLoginRequest();
            const { findByText } = render(<LoginPage login={false}/>);

            //when
            await findByText('OK').then(ionFireEvent.submit);

            //then
            expect(mockHistoryPush).toBeCalledWith('dashboard');
        });
    });
});

