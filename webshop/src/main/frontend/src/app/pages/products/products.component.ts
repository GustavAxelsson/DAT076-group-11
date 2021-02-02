import { Component, OnInit } from '@angular/core';
import {ShoppingCartService} from "../../services/shopping-cart.service";

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
        name: 'Yummy cat food',
        price: 4.20,
        url: 'assets/happycat.jpg'
      },
      {
        name: 'Yummy cat food',
        price: 4.20,
        url: 'assets/happycat.jpg'
      },
      {
        name: 'Yummy cat food',
        price: 4.20,
        url: 'assets/happycat.jpg'
      },
      {
        name: 'Yummy cat food',
        price: 4.20,
        url: 'assets/happycat.jpg'
      },
    ];
  }

  public addToCart() {
    this.shoppingCartService.addToShoppingCart();
  }
}

export interface Product {
  name: string;
  url: string;
  price: number;
}
