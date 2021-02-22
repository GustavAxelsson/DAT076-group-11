import { Component, OnInit } from '@angular/core';
import {ShoppingCartService} from '../../services/shopping-cart.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  public products: Product[] = [];

  constructor(private shoppingCartService: ShoppingCartService) {}

  ngOnInit(): void {
    this.shoppingCartService.fetchProducts().subscribe(products => {
      if (products !== undefined && products.length > 0) {
        this.products = products
      }
    });
  }

  public addToCart(): void {
    this.shoppingCartService.addToShoppingCart();
  }
}

export interface Product {
  name: string;
  url: string;
  price: number;
  description: string;
}
