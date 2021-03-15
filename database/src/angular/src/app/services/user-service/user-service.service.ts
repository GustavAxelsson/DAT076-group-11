import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { Customer } from '../../models/customer';

@Injectable({
  providedIn: 'root',
})
export class UserServiceService {
  private serviceUrl: string = environment.baseUrl + '/user/';

  header: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  httpOptions = {
    headers: this.header,
  };

  constructor(private httpClient: HttpClient) {}

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

  public updateCustomerOnUser(customer: Customer): Observable<void> {
    const url = this.serviceUrl + 'set-customer';
    return this.httpClient.post<void>(url, customer, {
      headers: this.header,
    });
  }
}

export interface ExternalUser {
  username: string | undefined;
  role: string | undefined;
}
