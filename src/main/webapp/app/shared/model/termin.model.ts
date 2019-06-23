import { IArzt } from '@/shared/model/arzt.model';

export interface ITermin {
  id?: number;
  datum?: Date;
  notiz?: string;
  arzt?: IArzt;
}

export class Termin implements ITermin {
  constructor(public id?: number, public datum?: Date, public notiz?: string, public arzt?: IArzt) {}
}
