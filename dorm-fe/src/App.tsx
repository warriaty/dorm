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
import WelcomePage from './pages/welcome/WelcomePage';
import { IonReactRouter } from '@ionic/react-router';
import { Route } from 'react-router';
import LoginPage from './pages/login/LoginPage';
import GuardedRoute from './components/guarded-route/GuardedRoute';

setupIonicReact();

const App: React.FC = () => {
    return (
        <IonApp>
            <IonReactRouter>
                <IonRouterOutlet id="main">
                    <GuardedRoute path="/siema"
                                  component={WelcomePage}
                                  componentProps={{t: 'test'}}
                                  restricted={true}/>
                    <Route path="/" exact={true}>
                        <WelcomePage t={"siema"}/>
                    </Route>
                    <Route path="/login">
                        <LoginPage registered={true}/>
                    </Route>
                    <Route path="/register">
                        <LoginPage registered={false}/>
                    </Route>
                </IonRouterOutlet>
            </IonReactRouter>
        </IonApp>
    );
};

export default App;
