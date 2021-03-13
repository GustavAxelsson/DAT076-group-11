import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import {Sale} from "../../models/sale";
import {Product} from "../../models/product";
import {ProductService} from "../../services/product.service";
import {ShoppingCartService} from "../../services/shopping-cart.service";
import {Router} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {of} from "rxjs";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {

  public sales: Sale[] = [];
  public saleProducts: Product[] = [];
  currentSale: Sale | undefined = undefined;

  constructor(
    private productService: ProductService,
  ) {}

  ngOnInit(): void {
    this.productService.getCurrentSale().pipe(
      switchMap(currentSale => {
        if(currentSale !== undefined && currentSale.id !== undefined) {
          this.currentSale = currentSale;
          return this.productService.getAllProductsForSale(currentSale.id);
        }
        return of(undefined);
      })
    ).subscribe((saleProducts) => {
      if(saleProducts !== undefined) {
        this.saleProducts = saleProducts;
      }
      });
  }
}
