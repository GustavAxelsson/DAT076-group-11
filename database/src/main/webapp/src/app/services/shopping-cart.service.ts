import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {
  private _itemsInShoppingCart$: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  public itemsInShoppingCart$: Observable<number> = this._itemsInShoppingCart$.asObservable();

  private apiUrl:string = '/products/';

   httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  }

  constructor(private httpClient: HttpClient) {
  }
  public addToShoppingCart(): void {
    this._itemsInShoppingCart$.next(this._itemsInShoppingCart$.getValue() + 1);
  }


}
