import { ICliente } from 'app/shared/model/cliente.model';

export interface ITipoDni {
  id?: number;
  siglasDni?: string;
  nombreDni?: string;
  clientes?: ICliente[] | null;
}

export const defaultValue: Readonly<ITipoDni> = {};
