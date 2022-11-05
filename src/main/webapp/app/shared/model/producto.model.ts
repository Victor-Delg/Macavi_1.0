import { IProdFact } from 'app/shared/model/prod-fact.model';

export interface IProducto {
  id?: number;
  talla?: number;
  porcentajeIva?: number | null;
  marca?: string;
  genero?: string;
  estilo?: string;
  descripcionProducto?: string;
  cantidadProducto?: number;
  prodFacts?: IProdFact[] | null;
}

export const defaultValue: Readonly<IProducto> = {};
