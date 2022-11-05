import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/producto">
        <Translate contentKey="global.menu.entities.producto" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/factura">
        <Translate contentKey="global.menu.entities.factura" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cliente">
        <Translate contentKey="global.menu.entities.cliente" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/locate">
        <Translate contentKey="global.menu.entities.locate" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tipo-dni">
        <Translate contentKey="global.menu.entities.tipoDni" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/prod-fact">
        <Translate contentKey="global.menu.entities.prodFact" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
