import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { map } from 'rxjs/operators';
import { JsonObject } from '@angular/compiler-cli/ngcc/src/packages/entry_point';
import { AuthUser, UserType } from '../../models/auth-user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authUser: BehaviorSubject<AuthUser | undefined> = new BehaviorSubject<
    AuthUser | undefined
  >(undefined);

  private ACCESS_TOKEN = 'webshop-access-token';
  private apiUrl = '/auth/';

  get authUser$(): Observable<AuthUser | undefined> {
    return this.authUser.asObservable();
  }


  constructor(private httpClient: HttpClient) {
    const token = localStorage.getItem(this.ACCESS_TOKEN);
    if (token) {
      this.decodeToken(token);
    }
  }

  public register(username: string, password: string): Observable<boolean> {
    const payload = new HttpParams()
      .set('username', username)
      .set('password', password);

    const url = environment.baseUrl + this.apiUrl + 'register';
    return this.httpClient
      .post(url, payload, {
        observe: 'response',
        responseType: 'text',
      })
      .pipe(
        map((response) => {
          if (response.body) {
            this.decodeToken(response.body);
            return true;
          }
          return undefined;
        }),
        map((response) => response !== undefined)
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
        observe: 'response',
        responseType: 'text',
      })
      .pipe(
        map((response) => {
          if (response.body) {
            return this.decodeToken(response.body);
          }
          return undefined;
        })
      );
  }

  public logout() {
    localStorage.removeItem(this.ACCESS_TOKEN);
    this.authUser.next(undefined);
  }

  decodeToken(token: string): AuthUser | undefined {
    localStorage.setItem(this.ACCESS_TOKEN, token);
    const tmp = JSON.parse(atob(token.split('.')[1]));
    const role: UserType = tmp['groups'][0].toUpperCase();
    const username = tmp['sub'];
    const userId = tmp['userId'].toString();
    const expiration = tmp['exp'];

    if (new Date(expiration * 1000) < new Date()) {
      localStorage.removeItem(this.ACCESS_TOKEN);
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

