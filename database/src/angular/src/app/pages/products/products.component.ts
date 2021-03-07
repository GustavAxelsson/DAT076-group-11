import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from '../../services/shopping-cart.service';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { Router } from '@angular/router';
import { map, switchMap, take } from 'rxjs/operators';
import { DomSanitizer } from '@angular/platform-browser';
import { HttpEvent, HttpEventType } from '@angular/common/http';

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
    private router: Router,

  ) {}

  ngOnInit(): void {
    this.productService.getAllProductsWithImage().subscribe(
      res => {
        console.log(res);
        this.products = res;
      }
    );
    // let prod: Product[] = [];
    // this.productService.getAllProducts$().pipe(
    //   switchMap((products: Product[]) => {
    //     prod = products;
    //     // @ts-ignore
    //     const productID= products[0].productImage.id!
    //     return this.productService.downloadImage(productID);
    //   })
    // ).subscribe((response: HttpEvent<Blob>) => {
    //
    //     if (response.type ===HttpEventType.Response) {
    //       console.log(response.body);
    //       let objectURL = URL.createObjectURL(response.body);
    //       prod[0].url = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    //       this.products = prod;
    //     }
    //   },
    //     error => console.log(error)
    //   );
  }

  public addToCart(product: Product, event: Event): void {
    event.stopPropagation();
    this.shoppingCartService.addProductToShoppingCart(product);
  }

  navigateToProduct(product: Product) {
    this.router.navigate(['/product', product.id]);
  }
}
