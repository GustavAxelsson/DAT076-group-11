import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { ShoppingCartTableDataSource } from './shopping-cart-table-datasource';
import { Product } from '../../models/product';
import { ShoppingCartService } from '../../services/shopping-service/shopping-cart.service';

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

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['name', 'amount', 'price'];

  constructor(private shoppingCartService: ShoppingCartService) {}

  ngOnInit() {
    this.dataSource = new ShoppingCartTableDataSource(this.shoppingCartService);
  }

  ngAfterViewInit() {
    this.dataSource!.sort = this.sort;
    this.dataSource!.paginator = this.paginator;
    this.table!.dataSource = this.dataSource
      ? this.dataSource
      : new ShoppingCartTableDataSource(this.shoppingCartService);
  }
}
