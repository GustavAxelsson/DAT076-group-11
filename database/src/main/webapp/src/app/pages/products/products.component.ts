import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from '../../services/shopping-cart.service';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { Router } from '@angular/router';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css'],
})
export class ProductsComponent implements OnInit {
  public products: Product[] = [];

  constructor(
    private productService: ProductService,
    private shoppingCartService: ShoppingCartService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.productService.getAllProducts$().subscribe((products) => {
      if (products !== undefined && products.length > 0) {
        this.products = products;
      }
    });
  }

  public addToCart(): void {
    this.shoppingCartService.addToShoppingCart();
  }

  navigateToProduct(product: Product) {
    this.router.navigate(['/product', product.id]);
  }
}
