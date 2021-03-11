import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpEvent,
  HttpEventType,
  HttpHeaders,
  HttpParams,
} from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { tap } from 'rxjs/operators';
import { JsonObject } from '@angular/compiler-cli/ngcc/src/packages/entry_point';

@Injectable({
  providedIn: 'root',
})
export class AuthServiceService {
  private authToken: BehaviorSubject<string> = new BehaviorSubject<string>('');
  private authUser: BehaviorSubject<AuthUser | undefined> = new BehaviorSubject<
    AuthUser | undefined
  >(undefined);

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  authToken$: Observable<string> = this.authToken.asObservable();
  private apiUrl = '/auth/';

  constructor(private httpClient: HttpClient) {}

  public register(username: string, password: string): void {
    const payload = new HttpParams()
      .set('username', username)
      .set('password', password);

    const url = environment.baseUrl + this.apiUrl + 'register';
    this.httpClient
      .post(url, payload, {
        observe: 'events',
        responseType: 'text',
      })
      .subscribe(
        (res) => {
          console.log(res);
          if (res.type === HttpEventType.Response) {
            console.log(res.body);
            if (res.body != null) {
              this.authToken.next(res.body);
            }
          }
        },
        (error) => console.warn(error)
      );
  }

  public login(
    username: string,
    password: string
  ): Observable<HttpEvent<string>> {
    const payload = new HttpParams()
      .set('username', username)
      .set('password', password);

    const url = environment.baseUrl + this.apiUrl + 'login';

    return this.httpClient
      .post(url, payload, {
        observe: 'events',
        responseType: 'text',
      })
      .pipe(
        tap((response) => {
          if (response.type === HttpEventType.Response && response.body) {
            this.authToken.next(response.body);
            this.decodeToken(response.body);
          }
        })
      );
  }

  decodeToken(token: string) {
    const tmp = atob(token.split('.')[1]);
    const json: JsonObject = (tmp as unknown) as JsonObject;
    console.log(json);
  }
}
export type UserType = 'USER' | 'ADMIN' | undefined;
export interface AuthUser {
  userType: UserType;
  userId: string;
  username: string;
}

//{
// "sub":"g",
// "iss":"webshop-company",
// "groups":["user"],
// "exp":1615491182,
// "iat":1615490582,
// "jti":"42"}
