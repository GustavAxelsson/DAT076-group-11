import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import decode from 'jwt-decode';

@Injectable({
  providedIn: 'root',
})
export class AdminGuard implements CanActivate {
  private ACCESS_TOKEN = 'webshop-access-token';
  private ALLOWED_ROLE = ['admin'];

  constructor(public router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const token = localStorage.getItem(this.ACCESS_TOKEN);
    if (token) {
      const tokenInfo = decode(token);
      // @ts-ignore
      if (tokenInfo.groups && tokenInfo.groups[0].includes(this.ALLOWED_ROLE)) {
        return true;
      }
      this.router.navigate(['home']);
      return false;
    }
    this.router.navigate(['home']);
    return false;
  }
}
