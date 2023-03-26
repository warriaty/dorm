import React from 'react';
import { Redirect, Route } from 'react-router-dom';
import { OmitNative, RouteProps } from 'react-router';
import { Path } from 'history';

interface GuardedRouteProps {
    restricted: boolean;
    component: React.FC<any>;
    componentProps?: object;
}

type TheRouteProps = RouteProps<Path> & OmitNative<{}, keyof RouteProps>;
const GuardedRoute: React.FC<GuardedRouteProps & TheRouteProps> = ({
                                                                       restricted,
                                                                       component: Component,
                                                                       componentProps,
                                                                       ...rest
                                                                   }) => {
    return (
        <Route
            {...rest}
            render={(renderRouteProps) =>
                restricted ? (
                    <Redirect to="/"/>
                ) : (
                    <Component {...renderRouteProps} {...componentProps} />
                )
            }
        />
    );
};

export default GuardedRoute;