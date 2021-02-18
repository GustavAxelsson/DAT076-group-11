import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {
  private _itemsInShoppingCart$: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  public itemsInShoppingCart$: Observable<number> = this._itemsInShoppingCart$.asObservable();

  constructor() {
  }
  public addToShoppingCart(): void {
    this._itemsInShoppingCart$.next(this._itemsInShoppingCart$.getValue() + 1);
  }
}
