import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpEvent,
  HttpHeaders,
  HttpParams,
} from '@angular/common/http';
import { forkJoin, Observable, of } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Product } from '../../models/product';
import { Category } from '../../models/category';
import { DomSanitizer } from '@angular/platform-browser';
import { filter, map, switchMap, take } from 'rxjs/operators';
import { AuthServiceService } from '../auth-service/auth-service.service';
import { Sale } from '../../models/sale';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private serviceUrl: string = environment.baseUrl + '/products/';

  header: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  httpOptions = {
    headers: this.header,
  };

  constructor(
    private httpClient: HttpClient,
    private sanitizer: DomSanitizer,
    private authService: AuthServiceService
  ) {
    authService.authToken$.subscribe((token) => {
      if (token !== undefined) {
        this.header = new HttpHeaders({
          'Content-Type': 'application/json',
          Authorization: 'Bearer ' + token,
        });
      }
    });
  }

  public getAllProducts$(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(
      this.serviceUrl + 'list-all-products',
      this.httpOptions
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
        const objectURL = URL.createObjectURL(response);
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
      map((batch) => [].concat(...batch)),
      map(products => products.filter(product => product !== undefined))
    );
  }


  public getAllProductsForCategory$(name: string): Observable<Product[]> {
    const queryParams: HttpParams = new HttpParams().set('name', name);
    const url = this.serviceUrl + 'list-category-products';
    return this.httpClient.get<Product[]>(url, {
      headers: this.header,
      params: queryParams,
    });
  }

  public getProductById(id: number): Observable<Product | undefined> {
    const url = this.serviceUrl + 'product-id';
    const queryParams: HttpParams = new HttpParams().set('id', id.toString());
    return this.httpClient.get<Product>(url, {
      headers: this.header,
      params: queryParams,
    });
  }

  public addProduct(product: Product): Observable<void> {
    const url = this.serviceUrl + 'add-product';
    return this.httpClient.post<void>(url, product, this.httpOptions);
  }

  public fetchCategories$(): Observable<Category[]> {
    const url = environment.baseUrl + '/category/' + 'list-all-categories';
    return this.httpClient.get<Category[]>(url, this.httpOptions);
  }

  public uploadProductImage(img: FormData): Observable<HttpEvent<any>> {
    const url = this.serviceUrl + 'upload-image';
    return this.httpClient.post<any>(url, img, {
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

  public addNewCategory(name: string): Observable<void> {
    const url = environment.baseUrl + '/category/' + 'add-category';
    return this.httpClient.post<void>(url, name, this.httpOptions);
  }


  public addSale(sale: Sale): Observable<void> {
    return this.httpClient.post<void>(
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
