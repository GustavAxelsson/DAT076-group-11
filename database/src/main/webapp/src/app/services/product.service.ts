import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Product } from '../models/product';
import { Category } from '../models/category';
import { Sale } from '../models/sale';
import {SaleProduct} from "../models/SaleProduct";

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

  public addProduct(product: Product): Observable<Object> {
    return this.httpClient.post(
      this.serviceUrl + 'add-product',
      product,
      this.httpOptions
    );
  }

  public fetchCategories(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(
      environment.baseUrl + '/category/' + 'list-all-categories'
    );
  }

  public addSale(sale: Sale): Observable<Object> {
    return this.httpClient.post(
      environment.baseUrl + '/sale/' + 'add-sale',
      sale,
      this.httpOptions
    );
  }

  public fetchSales(): Observable<Sale[]> {
    return this.httpClient.get<Sale[]>(
      environment.baseUrl + '/sale/' + 'list-all-sales'
    );
  }

  public addProductToSale(productId: string, saleId: number): Observable<void> {
    return this.httpClient.post<void>(environment.baseUrl + '/sale/' + 'add-product-sale',
      {productId: productId, saleId: saleId})
  }

  public getAllProductsForSale(id: number): Observable<Product[]> {
    return this.httpClient.get<Product[]>(
      environment.baseUrl + '/sale/' + 'list-sale-products', {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        params: new HttpParams().set('id', id.toString())
      }
    );
  }

  public setCurrentSale(sale: Sale): Observable<Object> {
    return this.httpClient.post(
      environment.baseUrl + '/sale/' + 'set-current-sale',
      sale,
      this.httpOptions
    );
  }

  public getCurrentSale(): Observable<Sale> {
    return this.httpClient.get<Sale>(
      environment.baseUrl + '/sale/' + 'get-current-sale',
    );
  }
}
