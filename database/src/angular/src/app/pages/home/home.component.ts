import { Component, OnInit } from '@angular/core';
import { Sale } from '../../models/sale';
import { Product } from '../../models/product';
import { switchMap } from 'rxjs/operators';
import { of } from 'rxjs';
import { ProductService } from '../../services/product-service/product.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  public sales: Sale[] = [];
  public saleProducts: Product[] = [];
  currentSale: Sale | undefined = undefined;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.productService
      .getCurrentSale()
      .pipe(
        switchMap((currentSale) => {
          if (
            currentSale !== undefined &&
            currentSale.id !== undefined &&
            currentSale.id !== 0
          ) {
            this.currentSale = currentSale;
            return this.productService.getAllProductsForSale(currentSale.id);
          }
          return of([]);
        }),
        switchMap((products) =>
          this.productService.getProductsWithImages(products)
        )
      )
      .subscribe((saleProducts) => {
        console.log(saleProducts);
        if (saleProducts !== undefined) {
          this.saleProducts = saleProducts;
        }
      });
  }
}
