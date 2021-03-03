import { Product } from './product';

export interface Order {
  items: Product;
  sum: number;
}
