import { DataSource } from '@angular/cdk/collections';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Observable } from 'rxjs';
import { ShoppingCartService } from '../../services/shopping-service/shopping-cart.service';
import { Product } from '../../models/product';

export class ShoppingCartTableDataSource extends DataSource<Product> {
  data: Product[] = [];
  paginator: MatPaginator | undefined;
  sort: MatSort | undefined;

  constructor(private shoppingCartService: ShoppingCartService) {
    super();
  }

  connect(): Observable<Product[]> {
    return this.shoppingCartService.getStoredItems$();
  }

  disconnect() {}
}
