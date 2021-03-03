import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Category } from '../models/category';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private serviceUrl: string = environment.baseUrl + '/products/';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(private httpClient: HttpClient) {}

  public getAllProducts$(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(
      this.serviceUrl + 'list-all-products',
      this.httpOptions
    );
  }

  public getAllProductsForCategory$(name: string): Observable<Product[]> {
    return this.httpClient.get<Product[]>(
      this.serviceUrl + 'list-category-products',
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        params: new HttpParams().set('name', name),
      }
    );
  }

  public getProductById(id: number): Observable<Product> {
    return this.httpClient.get<Product>(this.serviceUrl + 'product-id', {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      params: new HttpParams().set('id', id.toString()),
    });
  }

  public addProduct(name: string): Observable<Object> {
    return this.httpClient.post(
      this.serviceUrl + 'add-product',
      name,
      this.httpOptions
    );
  }
}
