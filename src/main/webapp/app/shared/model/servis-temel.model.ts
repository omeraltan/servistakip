import { IProtokolTemel } from 'app/shared/model/protokol-temel.model';
import { IKurumTemel } from 'app/shared/model/kurum-temel.model';
import { ServisSekli } from 'app/shared/model/enumerations/servis-sekli.model';
import { ServisTipi } from 'app/shared/model/enumerations/servis-tipi.model';
import { BaglantiSekli } from 'app/shared/model/enumerations/baglanti-sekli.model';
import { ServisDurum } from 'app/shared/model/enumerations/servis-durum.model';

export interface IServisTemel {
  id?: number;
  servisAdi?: string;
  servisUrl?: string;
  servisVeriTip?: string;
  aciklamasi?: string;
  dosyasiContentType?: string | null;
  dosyasi?: string | null;
  resmiContentType?: string | null;
  resmi?: string | null;
  servisSekli?: ServisSekli;
  servisTipi?: ServisTipi;
  baglantiSekli?: BaglantiSekli;
  servisDurum?: ServisDurum | null;
  anlikSorgu?: string | null;
  yiginVeri?: string | null;
  gds?: string | null;
  protokol?: IProtokolTemel | null;
  bakanlik?: IKurumTemel | null;
}

export const defaultValue: Readonly<IServisTemel> = {};
