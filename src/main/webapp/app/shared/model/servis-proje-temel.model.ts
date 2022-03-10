import { IServisTemel } from 'app/shared/model/servis-temel.model';
import { IProjeTemel } from 'app/shared/model/proje-temel.model';

export interface IServisProjeTemel {
  id?: number;
  kullanildigiYer?: string;
  servis?: IServisTemel | null;
  proje?: IProjeTemel | null;
}

export const defaultValue: Readonly<IServisProjeTemel> = {};
