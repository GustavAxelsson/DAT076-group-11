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
    this.products = [
      {
        name: 'Jeans',
        price: 50,
        url: 'assets/pants1.png',
        description: 'Cool pants for all occasions'
      },
      {
        name: 'Black shirt',
        price: 12,
        url: 'assets/shirt1.png',
        description: 'Plain shirt that always works'
      },
      {
        name: 'Nike shoe',
        price: 100,
        url: 'assets/shoe1.png',
        description: 'Sporty and nice shoes'
      },
      {
        name: 'Beige sweater',
        price: 80,
        url: 'assets/sweater1.png',
        description: 'Comfy knitted sweater'
      },
    ];
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
