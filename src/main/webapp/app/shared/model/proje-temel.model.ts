export interface IProjeTemel {
  id?: number;
  projedi?: string;
  projeKodu?: string | null;
  aciklama?: string;
}

export const defaultValue: Readonly<IProjeTemel> = {};
