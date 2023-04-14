import React from 'react';
import { RouteProps } from 'react-router';
import { Path } from 'history';
import { Redirect, Route } from 'react-router-dom';

interface AuthRouteProps {
    authorized: boolean;
    redirectUrl?: string;
    children: React.ReactNode;
}

type props = AuthRouteProps & RouteProps<Path>;

const AuthRoute: React.FC<props> = ({ authorized, redirectUrl = '/', children, ...rest }) => {
    if (authorized) {
        return (
            <Route {...rest}>
                {children}
            </Route>
        );
    }
    return (
        <Route {...rest}>
            <Redirect to={redirectUrl}/>
        </Route>
    );
};

export default AuthRoute;