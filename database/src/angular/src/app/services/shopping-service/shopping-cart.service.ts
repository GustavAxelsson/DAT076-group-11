import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from '../../models/product';
import { map } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';
import { AuthServiceService } from '../auth-service/auth-service.service';
import { environment } from '../../../environments/environment';
import { ProductOrder } from '../../models/ProductOrder';

@Injectable({
  providedIn: 'root',
})
export class ShoppingCartService {
  private storedItems: BehaviorSubject<Map<Product, number>> = new BehaviorSubject(new Map<Product, number>())

  private COOKIE_STORED_CART_ITEMS = 'webshop-access-stored-cart-items';

  private serviceUrl: string = environment.baseUrl + '/products/';



  header: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  httpOptions = {
    headers: this.header,
  };

  constructor(
    private httpClient: HttpClient,
    private cookieService: CookieService,
    private authService: AuthServiceService
    ) {
    const itemsJson = this.cookieService.get(this.COOKIE_STORED_CART_ITEMS);
    if (itemsJson) {
      const itemsMap: Map<Product, number> = JSON.parse(itemsJson, this.reviver)
      this.storedItems.next(itemsMap);
    }

    authService.authToken$.subscribe((token) => {
      console.log('got token from authService', token);
      if (token !== undefined) {
        this.header = new HttpHeaders({
          'Content-Type': 'application/json',
          Authorization: 'Bearer ' + token,
        });
      }
    });
  }

  get sumOfAllProducts(): Observable<number> {
    return this.storedItems
      .asObservable()
      .pipe(
        map((products) => {
          let totalPrice = 0;
          for (let [key, value] of products) {
            totalPrice = totalPrice +  (value * key.price);
          }
          return totalPrice;
        })
      );
  }


  get numberOfItemsInShoppingCart(): Observable<number> {
    return this.storedItems
      .asObservable()
      .pipe(
        map((products) => {
          let numberOfItems = 0;
          for(let value of Array.from( products.values()) ) {
            numberOfItems = numberOfItems + value;
          }
          return numberOfItems;
        })
      );
  }

  getStoredItems$(): Observable<Product[]> {
    return this.storedItems.asObservable().pipe(
      map(products => {
        const list: Product[] = [];
        for (let [key, value] of products) {
          const product = key;
          product.amount = value;
          list.push(product);
        }
        return list;
      })
    );
  }


  public addProductToShoppingCart(product: Product): void {
    if (product === undefined) {
      return;
    }
    const items = this.storedItems.value.get(product);
    if (items === undefined) {
      this.storedItems.value.set(product, 1);
    } else {
      this.storedItems.value.set(product, items + 1);
    }
    this.storedItems.next(this.storedItems.value);
    this.storeItemsToCookie();
  }

  removeProductFromCart(product: Product): void {
    if (product === undefined) {
      return;
    }
    const numberOfProducts = this.storedItems.value.get(product);
    if (numberOfProducts === undefined) {
      return;
    } else if (numberOfProducts === 0 || numberOfProducts === 1) {
      this.storedItems.value.delete(product);
    } else {
      this.storedItems.value.set(product, numberOfProducts - 1);
    }
    this.storedItems.next(this.storedItems.value);
    this.storeItemsToCookie();
  }

  storeItemsToCookie(): void {
    const json = JSON.stringify(this.storedItems.value, this.replacer);
    this.cookieService.delete(this.COOKIE_STORED_CART_ITEMS);
    this.cookieService.set(this.COOKIE_STORED_CART_ITEMS, json);
  }

  replacer(key: any, value: any) {
    if(value instanceof Map) {
      return {
        dataType: 'Map',
        value: Array.from(value.entries()),
      };
    } else {
      return value;
    }
  }

 reviver(key: any, value: any) {
    if(typeof value === 'object' && value !== null) {
      if (value.dataType === 'Map') {
        return new Map(value.value);
      }
    }
    return value;
  }

  purchase(): Observable<void> {

    const products = Array.from(this.storedItems.value.keys());

    console.log(products, this.httpOptions);
    const url = this.serviceUrl + 'purchase';

    return this.httpClient.post<void>(url, products, {
      headers: this.header
    });
  }

  getMyOrders(): Observable<ProductOrder[]> {
    const url = this.serviceUrl + 'my-orders';
    return this.httpClient.get<ProductOrder[]>(url, {
      headers: this.header
    });
  }

}
