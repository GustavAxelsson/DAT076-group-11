import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { ShoppingCartTableDataSource } from './shopping-cart-table-datasource';
import { Product } from '../../models/product';
import { ShoppingCartService } from '../../services/shopping-service/shopping-cart.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-shopping-cart-table',
  templateUrl: './shopping-cart-table.component.html',
  styleUrls: ['./shopping-cart-table.component.css'],
})
export class ShoppingCartTableComponent implements AfterViewInit, OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;
  @ViewChild(MatSort) sort: MatSort | undefined;
  @ViewChild(MatTable) table: MatTable<Product> | undefined;
  dataSource: ShoppingCartTableDataSource | undefined;
  totalSum = this.shoppingCartService.sumOfAllProducts;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['name', 'price', 'remove'];

  constructor(
    private shoppingCartService: ShoppingCartService,
    private _snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit() {
    this.dataSource = new ShoppingCartTableDataSource(this.shoppingCartService);
  }

  removeProduct(product: Product) {
    this.shoppingCartService.removeProductFromCart(product);
  }

  addProduct(product: Product) {
    this.shoppingCartService.addProductToShoppingCart(product);
  }

  purchase() {
    this.shoppingCartService.purchase().subscribe(() => {
      this._snackBar.open('Thank you for your purchase', undefined, {
        duration: 2000,
      });
      this.router.navigate(['home']);
    });
  }

  ngAfterViewInit() {
    this.dataSource!.sort = this.sort;
    this.dataSource!.paginator = this.paginator;
    this.table!.dataSource = this.dataSource
      ? this.dataSource
      : new ShoppingCartTableDataSource(this.shoppingCartService);
  }
}
