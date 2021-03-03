import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  }

  private apiUrl = '/auth/'

  constructor(private httpClient: HttpClient) {
  }

  public getRoles(): Observable<string> {
    return this.httpClient.get<string>(environment.apiUrl + this.apiUrl + 'get-roles');
  }
}
