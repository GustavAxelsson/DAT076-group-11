import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpEvent,
  HttpEventType,
  HttpHeaders,
  HttpParams,
  HttpResponse,
} from '@angular/common/http';
import { forkJoin, Observable, of } from 'rxjs';
import { environment } from '../../environments/environment';
import { Product } from '../models/product';
import { Category } from '../models/category';
import { DomSanitizer } from '@angular/platform-browser';
import { map, switchMap, take } from 'rxjs/operators';
import { AuthServiceService } from './auth-service.service';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private serviceUrl: string = environment.baseUrl + '/products/';

  apiToken: String = '';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(
    private httpClient: HttpClient,
    private sanitizer: DomSanitizer,
    private authService: AuthServiceService
  ) {
    authService.authToken$.subscribe((res) => {
      this.apiToken = res;
    });
  }

  public getAllProducts$(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(
      this.serviceUrl + 'list-all-products',
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Authorization: 'Bearer ' + this.apiToken,
        }),
      }
    );
  }

  public getProductWithImage(
    product: Product
  ): Observable<Product | undefined> {
    return this.downloadImage(product.productImage?.id!).pipe(
      map((response: Blob | undefined) => {
        if (response === undefined) {
          return undefined;
        }
        let objectURL = URL.createObjectURL(response);
        product.url = this.sanitizer.bypassSecurityTrustUrl(objectURL);
        return product;
      })
    );
  }

  public getAllProductsWithImage(): Observable<Product[]> {
    return this.getAllProducts$().pipe(
      switchMap((products) => {
        const observables: Array<Observable<Product | undefined>> = [];
        products.forEach((product) => {
          observables.push(this.getProductWithImage(product).pipe(take(1)));
        });
        return forkJoin(...observables);
      }),
      map((batch) => [].concat(...batch))
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
    console.log(img);
    return this.httpClient.post<any>(this.serviceUrl + 'upload-image', img, {
      reportProgress: true,
      observe: 'events',
    });
  }

  public downloadImage(id: number): Observable<Blob | undefined> {
    if (id === undefined) {
      return of(undefined);
    }
    return this.httpClient.get<Blob>(this.serviceUrl + 'download-image', {
      responseType: 'blob' as 'json',
      params: new HttpParams().set('id', id.toString()),
    });
  }

  public addNewCategory(name: string) {
    return this.httpClient.post(
      environment.baseUrl + '/category/' + 'add-category',
      name,
      this.httpOptions
    );
  }
}
