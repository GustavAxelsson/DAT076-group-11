import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from '../../services/shopping-service/shopping-cart.service';
import { ProductService } from '../../services/product-service/product.service';
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
    this.productService.getAllProductsWithImage().subscribe((res) => {
      this.products = res;
    });
  }

  public addToCart(product: Product, event: Event): void {
    event.stopPropagation();
    this.shoppingCartService.addProductToShoppingCart(product);
  }

  navigateToProduct(product: Product) {
    this.router.navigate(['/product', product.id]);
  }
}
