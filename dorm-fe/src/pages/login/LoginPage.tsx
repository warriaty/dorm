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
import React from 'react';

interface LoginPageProps {
    registered: boolean;
}

const LoginPage: React.FC<LoginPageProps> = ({ registered }) => {
    return (
        <IonContent className="login-content">
            <div className="h-100 d-flex flex-column justify-content-center align-items-center">
                <IonCard className="login-card ion-padding">

                    <IonCardHeader className="ion-padding">
                        <IonCardTitle>
                            <h3>{registered ? 'Zaloguj' : 'Zarejstruj'}</h3>
                        </IonCardTitle>
                    </IonCardHeader>

                    <IonCardContent>
                        <IonItem className="ion-padding">
                            <IonLabel>Email:</IonLabel>
                            <IonInput autocomplete="off" className="ion-padding-horizontal" type="email" size={40}/>
                        </IonItem>

                        <IonItem className="ion-padding">
                            <IonLabel>Hasło:</IonLabel>
                            <IonInput autocomplete="off" className="ion-padding-horizontal" type="password" size={40}/>
                        </IonItem>

                        <div className="ion-padding-horizontal d-flex justify-content-between">
                            <IonButton className="ion-margin-vertical"
                                       routerLink={registered ? '/register' : '/login'}
                                       size="large">
                                {registered ? 'Zarejstruj' : 'Zaloguj'}
                            </IonButton>
                            <IonButton className="ion-margin-vertical" routerLink="/login" size="large">
                                OK
                            </IonButton>
                        </div>
                    </IonCardContent>

                </IonCard>
            </div>
        </IonContent>
    );
};

export default LoginPage;