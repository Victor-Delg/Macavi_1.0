import dayjs from 'dayjs';
import { IProdFact } from 'app/shared/model/prod-fact.model';
import { ICliente } from 'app/shared/model/cliente.model';
import { Pago } from 'app/shared/model/enumerations/pago.model';

export interface IFactura {
  id?: number;
  descripcion?: string;
  fechaFactura?: string;
  fechaVencimiento?: string | null;
  tipoPago?: Pago;
  totalFactura?: number;
  prodFacts?: IProdFact[] | null;
  cliente?: ICliente;
}

export const defaultValue: Readonly<IFactura> = {};
