import { IServisTemel } from 'app/shared/model/servis-temel.model';

export interface IMetodTemel {
  id?: number;
  metodAdi?: string;
  metodNu?: number;
  metodAciklamasi?: string;
  servis?: IServisTemel | null;
}

export const defaultValue: Readonly<IMetodTemel> = {};
