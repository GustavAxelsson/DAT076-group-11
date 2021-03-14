import { Category } from './category';
import { ProductImage } from './product-image';
import { SafeUrl } from '@angular/platform-browser';
export interface Product {
  name: string;
  url?: SafeUrl;
  price: number;
  description: string;
  id?: string;
  category?: Category;
  amount?: number;
  productImage?: ProductImage;
}
