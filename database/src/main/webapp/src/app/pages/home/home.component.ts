import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import {Sale} from "../../models/sale";
import {Product} from "../../models/product";
import {ProductService} from "../../services/product.service";
import {ShoppingCartService} from "../../services/shopping-cart.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit, AfterViewInit {

  public sales: Sale[] = [];

  public saleProducts: Product[] = [];

  constructor(
    private productService: ProductService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.productService.fetchSales().subscribe((sales) => {
      if (sales !== undefined && sales.length > 0) {
        this.sales = sales;
      }
    });
  }

  ngAfterViewInit(): void {}

  navigateToProducts(): void {}

  navigateToProductDetail(): void {}
}
