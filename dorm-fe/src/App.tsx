import { IonApp, IonRouterOutlet, setupIonicReact } from '@ionic/react';

/* Core CSS required for Ionic components to work properly */
import '@ionic/react/css/core.css';

/* Basic CSS for apps built with Ionic */
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';

/* Optional CSS utils that can be commented out */
import '@ionic/react/css/padding.css';
import '@ionic/react/css/float-elements.css';
import '@ionic/react/css/text-alignment.css';
import '@ionic/react/css/text-transformation.css';
import '@ionic/react/css/flex-utils.css';
import '@ionic/react/css/display.css';

/* Theme variables */
import './theme/variables.css';
import { IonReactRouter } from '@ionic/react-router';
import React, { useContext } from 'react';
import { Route } from 'react-router';
import WelcomePage from './pages/welcome/WelcomePage';
import LoginPage from './pages/login/LoginPage';
import AuthRoute from './components/AuthRoute';
import AuthContext from './store/auth-context';

setupIonicReact();

const App: React.FC = () => {
    const {isLoggedIn, autoLogoutIn} = useContext(AuthContext);

    return (
        <IonApp>
            <IonReactRouter>
                <IonRouterOutlet id="main">
                    <Route path="/" exact={true}>
                        <WelcomePage/>
                    </Route>
                    <AuthRoute authorized={!isLoggedIn} redirectUrl={'/dashboard'} path="/login">
                        <LoginPage login={true}/>
                    </AuthRoute>
                    <AuthRoute authorized={!isLoggedIn} redirectUrl={'/dashboard'} path="/register">
                        <LoginPage login={false}/>
                    </AuthRoute>
                    <AuthRoute authorized={isLoggedIn} path={"/dashboard"}>
                        <div>Witaj zalogowany użtkowniku</div>
                        <div>Zostało milisecund do autologotu: {autoLogoutIn}</div>
                    </AuthRoute>
                </IonRouterOutlet>
            </IonReactRouter>
        </IonApp>
    );
};

export default App;
