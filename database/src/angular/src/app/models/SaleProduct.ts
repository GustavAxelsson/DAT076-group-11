import { Sale } from './sale';
import { Product } from './product';

export interface SaleProduct {
  sale: Sale;
  product: Product;
}
