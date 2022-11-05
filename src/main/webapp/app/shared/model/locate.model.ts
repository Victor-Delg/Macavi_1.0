import { ICliente } from 'app/shared/model/cliente.model';

export interface ILocate {
  id?: number;
  ciudad?: string;
  departamento?: string;
  pais?: string;
  clientes?: ICliente[] | null;
}

export const defaultValue: Readonly<ILocate> = {};
