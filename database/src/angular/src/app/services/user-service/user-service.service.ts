import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';
import { AuthServiceService } from '../auth-service/auth-service.service';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserServiceService {
  private serviceUrl: string = environment.baseUrl + '/user/';

  header: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  httpOptions = {
    headers: this.header,
  };

  constructor(
    private httpClient: HttpClient,
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

  public getAllUsers(): Observable<ExternalUser[]> {
    const url = this.serviceUrl + 'get-all-users';
    return this.httpClient.get<ExternalUser[]>(url, this.httpOptions);
  }

  public updateRoleOnUser(username: string, role: string): Observable<void> {
    const url = this.serviceUrl + 'update-user-role';
    const queryParams: HttpParams = new HttpParams()
      .set('username', username)
      .set('role', role);
    return this.httpClient.post<void>(url, null, {
      headers: this.header,
      params: queryParams,
    });
  }
}

export interface ExternalUser {
  username: string | undefined;
  role: string | undefined;
}