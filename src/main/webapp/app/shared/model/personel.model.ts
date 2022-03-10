import { Unvani } from 'app/shared/model/enumerations/unvani.model';

export interface IPersonel {
  id?: number;
  adi?: string;
  soyadi?: string;
  telefonu?: string;
  eposta?: string | null;
  unvani?: Unvani;
}

export const defaultValue: Readonly<IPersonel> = {};
