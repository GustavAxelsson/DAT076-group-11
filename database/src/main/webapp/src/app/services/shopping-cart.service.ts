import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from '../models/product';
import { map } from 'rxjs/operators';

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
    const currentProducts = previousProducts.concat(product);
    this._itemsInShoppingCart$.next(currentProducts);
  }
}
