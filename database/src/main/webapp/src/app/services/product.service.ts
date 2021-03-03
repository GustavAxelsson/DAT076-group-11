import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Product} from "../pages/products/products.component";
import {environment} from "../../environments/environment";
import {Category} from "../models/category";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private serviceUrl:string = environment.baseUrl + '/products/';

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  }

  constructor(private httpClient: HttpClient) {

  }

  public getAllProducts$(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(this.serviceUrl + 'list-all-products', this.httpOptions);
  }

  public getAllProductsForCategory$(category: Category): Observable<Product[]> {
    return this.httpClient.get<Product[]>(this.serviceUrl + 'list-all-products', this.httpOptions);
  }

  public getProductById(id: string): Observable<Product> {
    return this.httpClient.get<Product>(this.serviceUrl + 'list-all-products', this.httpOptions);
  }

  public addProduct(name: string): Observable<Object>{
    return this.httpClient.post(this.serviceUrl + 'add-product', name, this.httpOptions);
  }


}
