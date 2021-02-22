import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Product} from "../pages/products/products.component";

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {
  private _itemsInShoppingCart$: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  public itemsInShoppingCart$: Observable<number> = this._itemsInShoppingCart$.asObservable();

  constructor(private httpClient: HttpClient) {
  }
  public addToShoppingCart(): void {
    this._itemsInShoppingCart$.next(this._itemsInShoppingCart$.getValue() + 1);
  }

  public fetchProducts(): Observable<Product[]> {
    return this.httpClient.get<Product[]>('http://localhost:8080/database-2.0/api/products');
  }
}
