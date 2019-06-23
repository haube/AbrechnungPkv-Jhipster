import { IArzt } from '@/shared/model/arzt.model';

export interface IRechnung {
  id?: number;
  betrag?: number;
  datumRechnung?: Date;
  datumZahlung?: Date;
  arzt?: IArzt;
}

export class Rechnung implements IRechnung {
  constructor(public id?: number, public betrag?: number, public datumRechnung?: Date, public datumZahlung?: Date, public arzt?: IArzt) {}
}
