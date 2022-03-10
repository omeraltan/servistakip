import dayjs from 'dayjs';
import { IPersonel } from 'app/shared/model/personel.model';
import { IServisTemel } from 'app/shared/model/servis-temel.model';

export interface IServisIs {
  id?: number;
  baslamaTarihi?: string;
  tamamlamaTarihi?: string;
  sorumlu?: IPersonel | null;
  yapan?: IPersonel | null;
  servis?: IServisTemel | null;
}

export const defaultValue: Readonly<IServisIs> = {};
