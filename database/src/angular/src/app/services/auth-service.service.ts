import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpEvent,
  HttpEventType,
  HttpHeaders,
  HttpParams,
  HttpResponse,
} from '@angular/common/http';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { environment } from '../../environments/environment';
import { catchError } from 'rxjs/operators';

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
    const payload = new HttpParams()
      .set('username', username)
      .set('password', password);

    const url = environment.baseUrl + this.apiUrl + 'login';
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
            // console.log(res.body);
            // this.authToken.next(res?.body);
          }
        },
        (error) => console.warn(error)
      );

    // this.httpClient
    //   .post<any>(environment.baseUrl + this.apiUrl + 'login', {
    //     headers: new HttpHeaders({
    //       'Content-Type': 'application/x-www-form-urlencoded',
    //     }),
    //     params: new HttpParams()
    //       .set('username', username)
    //       .set('password', password),
    //   })
    //   .subscribe(
    //     (res) => {
    //       console.log(res);
    //       if (res != null) {
    //         console.log(res);
    //         this.authToken.next(res);
    //       }
    //     },
    //     (error) => {
    //       console.log('Error login');
    //     }
    //   );
  }
}
