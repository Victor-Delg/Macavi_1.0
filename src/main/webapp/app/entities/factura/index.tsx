import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Factura from './factura';
import FacturaDetail from './factura-detail';
import FacturaUpdate from './factura-update';
import FacturaDeleteDialog from './factura-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FacturaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FacturaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FacturaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Factura} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FacturaDeleteDialog} />
  </>
);

export default Routes;
