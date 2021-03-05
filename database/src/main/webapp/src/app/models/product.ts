import { Category } from './category';
export interface Product {
  name: string;
  url?: string;
  price: number;
  description: string;
  id?: string;
  category?: Category;
  amount?: number;
}
