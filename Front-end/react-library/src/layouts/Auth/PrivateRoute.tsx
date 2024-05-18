import React from 'react';
import { Route, Redirect, RouteProps } from 'react-router-dom';
import { isAuthenticated } from '../Utils/auth';

interface PrivateRouteProps extends RouteProps {
  component: React.ComponentType<any>;
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({ component: Component, ...rest }) => (
  <Route
    {...rest}
    render={props =>
      isAuthenticated() ? (
        <Component {...props} />
      ) : (
        <Redirect to='/login' />
      )
    }
  />
);

export default PrivateRoute;
