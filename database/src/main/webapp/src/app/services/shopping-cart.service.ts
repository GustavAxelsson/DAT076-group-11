import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from '../models/product';
import { map } from 'rxjs/operators';
import { Category } from '../models/category';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ShoppingCartService {
  private _itemsInShoppingCart$: BehaviorSubject<
    Product[]
  > = new BehaviorSubject<Product[]>([]);

  get itemsInShoppingCart$(): Observable<Product[]> {
    return this._itemsInShoppingCart$.asObservable();
  }

  get numberOfItemsInShoppingCart(): Observable<number> {
    return this._itemsInShoppingCart$
      .asObservable()
      .pipe(map((list) => list.length));
  }

  private apiUrl: string = '/products/';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(private httpClient: HttpClient) {}

  public addProductToShoppingCart(product: Product): void {
    if (product == undefined) {
      return;
    }
    const previousProducts = this._itemsInShoppingCart$.getValue();
    if (previousProducts.length > 0) {
      const previousProduct = previousProducts.find((p) => p.id === product.id);
      if (
        previousProduct !== undefined &&
        previousProduct.amount !== undefined
      ) {
        previousProduct.amount = previousProduct.amount + 1;
        this._itemsInShoppingCart$.next(previousProducts);
        return;
      } else {
        product.amount = 1;
        this._itemsInShoppingCart$.next(previousProducts.concat(product));
        return;
      }
    } else {
      product.amount = 1;
      this._itemsInShoppingCart$.next(previousProducts.concat(product));
    }
  }

  // placeOrder(): Observable<boolean> {
  //
  // }
}
