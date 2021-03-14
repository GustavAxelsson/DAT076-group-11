import { Product } from './product';

export interface Sale {
  id?: number;
  name: string;
  percentage: number;
  productList?: Product[];
  currentSale?: boolean;
}
