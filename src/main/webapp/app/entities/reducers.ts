import producto from 'app/entities/producto/producto.reducer';
import factura from 'app/entities/factura/factura.reducer';
import cliente from 'app/entities/cliente/cliente.reducer';
import locate from 'app/entities/locate/locate.reducer';
import tipoDni from 'app/entities/tipo-dni/tipo-dni.reducer';
import prodFact from 'app/entities/prod-fact/prod-fact.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  producto,
  factura,
  cliente,
  locate,
  tipoDni,
  prodFact,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
