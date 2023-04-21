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

jest.mock('../../util/HttpService', () => {
    return {
        post: () => new Promise(resolve =>
            resolve({
                    data: {
                        tokenValue: 'test',
                        expirationTimestamp: 'test'
                    }
                }
            ))
    };
});

describe('LoginPage component', () => {
    describe('Sign in scenarios', function () {
        it('Should navigate to dashboard on sign in', async () => {
            //given
            const { findByText } = render(<LoginPage login={true}/>);

            //when
            await findByText('OK').then(ionFireEvent.submit);

            //then
            expect(mockHistoryPush).toBeCalledWith('dashboard');
        });
    });
});

