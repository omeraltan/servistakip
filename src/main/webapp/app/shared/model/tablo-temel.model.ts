import { IServisTemel } from 'app/shared/model/servis-temel.model';

export interface ITabloTemel {
  id?: number;
  gercekVeritabani?: string;
  gercekLogTablo?: string;
  gercekaciklamasi?: string | null;
  testVeritabani?: string;
  testLogTablo?: string;
  testaciklamasi?: string | null;
  servis?: IServisTemel | null;
}

export const defaultValue: Readonly<ITabloTemel> = {};
