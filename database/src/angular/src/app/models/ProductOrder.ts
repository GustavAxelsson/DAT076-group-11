import { Customer } from './customer';
import { Product } from './product';

export interface ProductOrder {
  id: string;
  customer: Customer;
  productList: Product[];
}
