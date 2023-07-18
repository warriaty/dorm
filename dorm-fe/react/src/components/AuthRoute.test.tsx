import React from 'react';
import { render, } from '@testing-library/react';
import { IonRouterOutlet } from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import { Route } from 'react-router';
import AuthRoute from './AuthRoute';

describe('AuthRoute component', () => {
    it('Should render children when authorized', async () => {
        //given
        const { queryByText } = render(
            <IonReactRouter>
                <IonRouterOutlet id="test">
                    <AuthRoute authorized={true} path="/">
                        <div>Login content</div>
                    </AuthRoute>
                </IonRouterOutlet>
            </IonReactRouter>
        );

        //when
        const loginContent = await queryByText('Login content');

        //then
        expect(loginContent).not.toBeNull();
    });

    it('Should not render children when unauthorized', async () => {
        //given
        const { queryByText } = render(
            <IonReactRouter>
                <IonRouterOutlet id="test">
                    <Route path="/test">
                        <div>Fallback route</div>
                    </Route>
                    <AuthRoute authorized={false} path="/" exact={true} redirectUrl="/test">
                        <div>Login content</div>
                    </AuthRoute>
                </IonRouterOutlet>
            </IonReactRouter>
        );

        //when
        const fallbackRoute = await queryByText('Login content');

        //then
        expect(fallbackRoute).toBeNull();
    });

    it('Should redirect when not authorized', async () => {
        //given
        const { queryByText } = render(
            <IonReactRouter>
                <IonRouterOutlet id="test">
                    <Route path="/test">
                        <div>Fallback route</div>
                    </Route>
                    <AuthRoute authorized={false} path="/" exact={true} redirectUrl="/test">
                        <div>Login content</div>
                    </AuthRoute>
                </IonRouterOutlet>
            </IonReactRouter>
        );

        //when
        const fallbackRoute = await queryByText('Fallback route');

        //then
        expect(fallbackRoute).not.toBeNull();
    });
});

