import { IServisTemel } from 'app/shared/model/servis-temel.model';

export interface IKlasorTemel {
  id?: number;
  odasi?: string;
  dolabi?: string;
  klasoru?: string;
  fihristSirasi?: string;
  servis?: IServisTemel | null;
}

export const defaultValue: Readonly<IKlasorTemel> = {};
