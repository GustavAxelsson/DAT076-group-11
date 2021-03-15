import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import decode from 'jwt-decode';

//https://medium.com/@ryanchenkie_40935/angular-authentication-using-route-guards-bf7a4ca13ae3
//https://medium.com/angular-shots/shot-3-how-to-add-http-headers-to-every-request-in-angular-fab3d10edc26

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  private ACCESS_TOKEN = 'webshop-access-token';
  private ALLOWED_ROLE = 'user'

  constructor(public router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    const token = localStorage.getItem(this.ACCESS_TOKEN);
    if (token) {
      const tokenInfo = decode(token);
      // @ts-ignore
      if (tokenInfo.groups && tokenInfo.groups.includes(this.ALLOWED_ROLE)) {
        return true;
      }
      this.router.navigate(['home'])
      return false;
    }
    this.router.navigate(['home'])
    return false;
  }

}
