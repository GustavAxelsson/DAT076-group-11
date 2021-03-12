import { DataSource } from '@angular/cdk/collections';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Observable } from 'rxjs';
import { ShoppingCartService } from '../../services/shopping-service/shopping-cart.service';
import { Product } from '../../models/product';

export class ShoppingCartTableDataSource extends DataSource<Product> {
  data: Product[] = [
    {
      name: 'Cool product',
      price: 100,
      description: 'description',
      category: {
        name: 'shoe',
      },
    },
  ];
  paginator: MatPaginator | undefined;
  sort: MatSort | undefined;

  constructor(private shoppingCartService: ShoppingCartService) {
    super();
  }

  connect(): Observable<Product[]> {
    // Combine everything that affects the rendered data into one update
    // stream for the data-table to consume.
    return this.shoppingCartService.itemsInShoppingCart$;
  }

  /**
   *  Called when the table is being destroyed. Use this function, to clean up
   * any open connections or free any held resources that were set up during connect.
   */
  disconnect() {}

  /**
   * Paginate the data (client-side). If you're using server-side pagination,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getPagedData(data: Product[]) {
    const startIndex = this.paginator!.pageIndex * this.paginator!.pageSize;
    return data.splice(startIndex, this.paginator?.pageSize);
  }

  /**
   * Sort the data (client-side). If you're using server-side sorting,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getSortedData(data: Product[]) {
    if (!this.sort?.active || this.sort.direction === '') {
      return data;
    }

    return data.sort((a, b) => {
      const isAsc = this?.sort?.direction === 'asc';
      switch (this?.sort?.active) {
        // case 'name': return compare(a.name, b.name, isAsc);
        // case 'id': return compare(+a.id, +b.id, isAsc);
        default:
          return 0;
      }
    });
  }
}

/** Simple sort comparator for example ID/Name columns (for client-side sorting). */
function compare(a: string | number, b: string | number, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
