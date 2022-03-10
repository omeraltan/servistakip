import dayjs from 'dayjs';
import { IServisTemel } from 'app/shared/model/servis-temel.model';
import { IPersonel } from 'app/shared/model/personel.model';

export interface ISorunTemel {
  id?: number;
  sorunAciklamasi?: string;
  sorunTarihi?: string;
  cozumAciklamasi?: string | null;
  cozumTarihi?: string | null;
  servis?: IServisTemel | null;
  bulan?: IPersonel | null;
  cozen?: IPersonel | null;
}

export const defaultValue: Readonly<ISorunTemel> = {};
