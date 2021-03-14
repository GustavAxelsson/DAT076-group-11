import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from '../../models/product';
import { map } from 'rxjs/operators';
import { Category } from '../../models/category';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ShoppingCartService {
  private storedItems: BehaviorSubject<Map<Product, number>> = new BehaviorSubject(new Map<Product, number>())

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

  constructor(private httpClient: HttpClient) {}

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
  }
}
