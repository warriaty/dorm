import {
    IonButton,
    IonCard,
    IonCardContent,
    IonCardHeader,
    IonCardTitle,
    IonContent,
    IonInput,
    IonItem,
    IonLabel
} from '@ionic/react';

import './LoginPage.scss';
import React, { FormEvent, useContext, useRef } from 'react';
import AuthContext from '../../store/auth-context';
import { useHistory } from 'react-router';
import httpService from '../../util/HttpService';
import { BeToken } from '../../util/Token';

interface LoginPageProps {
    login: boolean;
}

const LoginPage: React.FC<LoginPageProps> = ({ login }) => {

    const { login: loginHandler } = useContext(AuthContext);
    const { push } = useHistory();
    const emailRef = useRef<HTMLIonInputElement>(null);
    const psswdRef = useRef<HTMLIonInputElement>(null);

    const onSubmitHandler = (event: FormEvent) => {
        event.preventDefault();
        const url = login ? '/login' : 'user/register';
        httpService.post<BeToken>(url, {
            email: emailRef.current?.value,
            password: psswdRef.current?.value
        }).then((response) => {
            loginHandler(response.data.tokenValue, response.data.expirationTimestamp);
            push('dashboard');
        });
    };

    return (
        <IonContent className="login-content">

            <div className="h-100 d-flex flex-column justify-content-center align-items-center">
                <form onSubmit={(event) => onSubmitHandler(event)}>
                    <IonCard className="login-card ion-padding">

                        <IonCardHeader className="ion-padding">
                            <IonCardTitle>
                                <h3>{login ? 'Zaloguj' : 'Zarejstruj'}</h3>
                            </IonCardTitle>
                        </IonCardHeader>

                        <IonCardContent>
                            <IonItem className="ion-padding">
                                <IonLabel>Email:</IonLabel>
                                <IonInput autocomplete="off"
                                          className="ion-padding-horizontal"
                                          type="email"
                                          size={40}
                                          required
                                          ref={emailRef}
                                />
                            </IonItem>
                            <IonItem className="ion-padding">
                                <IonLabel>Hasło:</IonLabel>
                                <IonInput autocomplete="off"
                                          className="ion-padding-horizontal"
                                          type="password"
                                          size={40}
                                          required
                                          ref={psswdRef}/>

                            </IonItem>

                            <div className="ion-padding-horizontal d-flex justify-content-between">
                                <IonButton className="ion-margin-vertical"
                                           routerLink={login ? '/register' : '/login'}
                                           size="large">
                                    {login ? 'Zarejstruj' : 'Zaloguj'}
                                </IonButton>
                                <IonButton className="ion-margin-vertical" type="submit" size="large">
                                    OK
                                </IonButton>
                            </div>

                        </IonCardContent>

                    </IonCard>
                </form>
            </div>
        </IonContent>
    );
};

export default LoginPage;