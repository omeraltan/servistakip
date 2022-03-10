import { IKurumTemel } from 'app/shared/model/kurum-temel.model';
import { Unvani } from 'app/shared/model/enumerations/unvani.model';

export interface IIrtibatTemel {
  id?: number;
  adi?: string;
  soyadi?: string;
  telefonu?: string;
  tcnu?: number;
  eposta?: string;
  unvani?: Unvani;
  kurum?: IKurumTemel | null;
}

export const defaultValue: Readonly<IIrtibatTemel> = {};
