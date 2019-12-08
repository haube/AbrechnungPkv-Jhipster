import { ITermin } from '@/shared/model/termin.model';
import { IRechnung } from '@/shared/model/rechnung.model';

export interface IArzt {
  id?: number;
  name?: string;
  strasse?: string;
  hausnummer?: string;
  plz?: string;
  ort?: string;
  telefon?: string;
  telefon2?: string;
  fax?: string;
  email?: string;
  web?: string;
  termines?: ITermin[];
  rechnungens?: IRechnung[];
}

export class Arzt implements IArzt {
  constructor(
    public id?: number,
    public name?: string,
    public strasse?: string,
    public hausnummer?: string,
    public plz?: string,
    public ort?: string,
    public telefon?: string,
    public telefon2?: string,
    public fax?: string,
    public email?: string,
    public web?: string,
    public termines?: ITermin[],
    public rechnungens?: IRechnung[]
  ) {}
}
