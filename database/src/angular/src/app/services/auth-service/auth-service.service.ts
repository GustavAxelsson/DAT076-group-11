import { Injectable } from '@angular/core';
import { HttpClient, HttpEventType, HttpHeaders, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { map } from 'rxjs/operators';
import { JsonObject } from '@angular/compiler-cli/ngcc/src/packages/entry_point';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class AuthServiceService {
  private COOKIE_TOKEN_ID = 'webshop-access-token';
  private authToken: BehaviorSubject<string> = new BehaviorSubject<string>('');
  private authUser: BehaviorSubject<AuthUser | undefined> = new BehaviorSubject<
    AuthUser | undefined
  >(undefined);

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  get authUser$(): Observable<AuthUser | undefined> {
    return this.authUser.asObservable();
  }
  authToken$: Observable<string> = this.authToken.asObservable();
  private apiUrl = '/auth/';

  constructor(private httpClient: HttpClient, private cookieService: CookieService) {
    const token = this.cookieService.get(this.COOKIE_TOKEN_ID);
    if (token) {
      this.decodeToken(token);
    }
  }

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
          if (res.type === HttpEventType.Response) {
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
  ): Observable<AuthUser | undefined> {
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
        map((response) => {
          if (response.type === HttpEventType.Response && response.body) {
            this.authToken.next(response.body);
            return this.decodeToken(response.body);
          }
          return undefined;
        })
      );
  }

  decodeToken(token: string): AuthUser | undefined {
    this.cookieService.set(this.COOKIE_TOKEN_ID, token);
    const tmp = JSON.parse(atob(token.split('.')[1]));
    const role: UserType = tmp['groups'][0].toUpperCase();
    const username = tmp['sub'];
    const userId = tmp['userId'];
    const expiration = tmp['exp'];

    if((new Date(expiration * 1000)) < new Date()) {
      this.cookieService.delete(this.COOKIE_TOKEN_ID);
      this.authUser.next(undefined);
      return undefined;
    }

    const authUser: AuthUser = {
      username: username,
      userId: userId,
      userType: role,
    };
    this.authUser.next(authUser);
    return authUser;
  }

  parseUserType(jsonObject: JsonObject): UserType {
    if (jsonObject !== undefined && jsonObject['groups'] !== undefined) {
      const tmp: string[] = (jsonObject['groups'] as unknown) as string[];
      if (tmp !== undefined && tmp.length > 0) {
        switch (tmp[0]) {
          case 'user':
            return 'USER';
          case 'admin':
            return 'ADMIN';
          default:
            return undefined;
        }
      }
    }
    return undefined;
  }
}

export type UserType = 'USER' | 'ADMIN' | undefined;
export interface AuthUser {
  userType: UserType | undefined;
  userId: string | undefined;
  username: string | undefined;
}
