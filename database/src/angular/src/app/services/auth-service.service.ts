import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthServiceService {
  private authToken: BehaviorSubject<string> = new BehaviorSubject<string>('');
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  authToken$: Observable<string> = this.authToken.asObservable();
  private apiUrl = '/auth/';

  constructor(private httpClient: HttpClient) {}

  public login(username: string, password: string): void {
    this.httpClient
      .get<string>(environment.baseUrl + this.apiUrl + 'login', {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        params: new HttpParams()
          .set('username', username)
          .set('password', password),
      })
      .subscribe(
        (res) => {
          if (res != null) {
            this.authToken.next(res);
          }
        },
        (error) => {
          console.log('Error login');
        }
      );
  }
}
