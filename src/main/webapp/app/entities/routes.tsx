import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Producto from './producto';
import Factura from './factura';
import Cliente from './cliente';
import Locate from './locate';
import TipoDni from './tipo-dni';
import ProdFact from './prod-fact';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}producto`} component={Producto} />
        <ErrorBoundaryRoute path={`${match.url}factura`} component={Factura} />
        <ErrorBoundaryRoute path={`${match.url}cliente`} component={Cliente} />
        <ErrorBoundaryRoute path={`${match.url}locate`} component={Locate} />
        <ErrorBoundaryRoute path={`${match.url}tipo-dni`} component={TipoDni} />
        <ErrorBoundaryRoute path={`${match.url}prod-fact`} component={ProdFact} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
