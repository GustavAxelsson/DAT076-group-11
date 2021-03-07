import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Product } from '../models/product';
import { Category } from '../models/category';

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

  public getProductById(id: number): Observable<Product | undefined> {
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

  public uploadProductImage(img: FormData): Observable<any> {
    return this.httpClient.post<any>(this.serviceUrl + 'upload-image', img, {
      reportProgress: true,
      observe: 'events',
    });
  }

  public downloadImage(id: number): Observable<HttpEvent<Blob>>{

    return this.httpClient.get<Blob>(this.serviceUrl + 'download-image', {
        observe: 'events',
        responseType: 'blob' as 'json',
        params: new HttpParams().set('id', id.toString())
       },
    );
  }
}