import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Locate from './locate';
import LocateDetail from './locate-detail';
import LocateUpdate from './locate-update';
import LocateDeleteDialog from './locate-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LocateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LocateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LocateDetail} />
      <ErrorBoundaryRoute path={match.url} component={Locate} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LocateDeleteDialog} />
  </>
);

export default Routes;
