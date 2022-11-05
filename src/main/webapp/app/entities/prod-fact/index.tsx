import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProdFact from './prod-fact';
import ProdFactDetail from './prod-fact-detail';
import ProdFactUpdate from './prod-fact-update';
import ProdFactDeleteDialog from './prod-fact-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProdFactUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProdFactUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProdFactDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProdFact} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProdFactDeleteDialog} />
  </>
);

export default Routes;
