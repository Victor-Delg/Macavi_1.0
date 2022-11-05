import { IFactura } from 'app/shared/model/factura.model';
import { IProducto } from 'app/shared/model/producto.model';

export interface IProdFact {
  id?: number;
  factura?: IFactura;
  producto?: IProducto;
}

export const defaultValue: Readonly<IProdFact> = {};
