import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from '../../models/product';
import { map } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';
import { environment } from '../../../environments/environment';
import { ProductOrder } from '../../models/ProductOrder';

@Injectable({
  providedIn: 'root',
})
export class ShoppingCartService {
  private storedItems: BehaviorSubject<Product[]> = new BehaviorSubject<
    Product[]
  >([]);
  private COOKIE_STORED_CART_ITEMS = 'webshop-access-stored-cart-items';
  private serviceUrl: string = environment.baseUrl + '/order/';
  private header: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });

  constructor(
    private httpClient: HttpClient,
    private cookieService: CookieService
  ) {
    const itemsJson = this.cookieService.get(this.COOKIE_STORED_CART_ITEMS);
    if (itemsJson) {
      const items: Product[] = JSON.parse(itemsJson);
      this.storedItems.next(items);
    }
  }

  get sumOfAllProducts(): Observable<number> {
    return this.storedItems.asObservable().pipe(
      map((products) => {
        let totalPrice = 0;
        if (products && products.length > 0) {
          products.forEach((product) => {
            totalPrice = totalPrice + product.price;
          });
        }
        return totalPrice;
      })
    );
  }

  get numberOfItemsInShoppingCart(): Observable<number> {
    return this.storedItems.asObservable().pipe(
      map((products) => {
        if (!products) {
          return 0;
        }
        return products.length;
      })
    );
  }

  getStoredItems$(): Observable<Product[]> {
    return this.storedItems.asObservable();
  }

  public addProductToShoppingCart(product: Product): void {
    if (product === undefined) {
      return;
    }
    if (
      this.storedItems.value.length > 0 &&
      this.storedItems.value.findIndex((item) => item.id === product.id) >= 0
    ) {
      return;
    }
    this.storedItems.value.push(product);
    this.storedItems.next(this.storedItems.value);
    this.storeItemsToCookie();
  }

  removeProductFromCart(product: Product): void {
    if (product === undefined) {
      return;
    }

    const productList: Product[] = this.storedItems.value;
    const index = productList.findIndex((item) => item.id === product.id);
    if (index >= 0) {
      productList.splice(index, 1);
    }
    this.storedItems.next(this.storedItems.value);
    this.storeItemsToCookie();
  }

  storeItemsToCookie(): void {
    const json = JSON.stringify(this.storedItems.value);
    this.cookieService.delete(this.COOKIE_STORED_CART_ITEMS);
    this.cookieService.set(this.COOKIE_STORED_CART_ITEMS, json);
  }

  purchase(): Observable<void> {
    const products = this.storedItems.value;
    if (!products) {
      return throwError('No products found');
    }
    const url = this.serviceUrl + 'purchase';
    return this.httpClient
      .post<void>(url, products, {
        headers: this.header,
      })
      .pipe(
        map(() => {
          this.storedItems.next([]);
          this.cookieService.delete(this.COOKIE_STORED_CART_ITEMS);
        })
      );
  }

  getMyOrders(): Observable<ProductOrder[]> {
    const url = this.serviceUrl + 'my-orders';
    return this.httpClient.get<ProductOrder[]>(url, {
      headers: this.header,
    });
  }
}
