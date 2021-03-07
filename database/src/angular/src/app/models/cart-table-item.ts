import { Product } from './product';

export class CartTableItem {
  product: Product | undefined;
  constructor() {
    this.product = undefined;
  }
}
