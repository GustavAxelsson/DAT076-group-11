import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Product} from "../pages/products/products.component";
import {environment} from "../../environments/environment";
import {Category} from "../models/category";

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

  public fetchProducts(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(environment.apiUrl + this.apiUrl + 'list-all-products');
  }

  public addProduct(product: Product): Observable<Object>{
    return this.httpClient.post(environment.apiUrl + this.apiUrl + 'add-product', product, this.httpOptions);
  }

  public fetchCategories():Observable<Category[]> {
    return this.httpClient.get<Category[]>(environment.apiUrl + '/category/' + 'list-all-categories');
  }
}
//        this.name = name;
//         this.url = url;
//         this.price = price;
//         this.description = description;
