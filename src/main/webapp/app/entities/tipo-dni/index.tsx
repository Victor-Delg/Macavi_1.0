import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoDni from './tipo-dni';
import TipoDniDetail from './tipo-dni-detail';
import TipoDniUpdate from './tipo-dni-update';
import TipoDniDeleteDialog from './tipo-dni-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TipoDniUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TipoDniUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TipoDniDetail} />
      <ErrorBoundaryRoute path={match.url} component={TipoDni} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TipoDniDeleteDialog} />
  </>
);

export default Routes;
