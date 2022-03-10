import dayjs from 'dayjs';
import { SureDurum } from 'app/shared/model/enumerations/sure-durum.model';

export interface IProtokolTemel {
  id?: number;
  sureDurum?: SureDurum;
  sureBitisTarihi?: string | null;
  protokolNu?: string;
  olurYaziNu?: string;
  protokolImzaTarihi?: string;
  protokolAciklamasi?: string | null;
}

export const defaultValue: Readonly<IProtokolTemel> = {};
