import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../../../services/auth-service/auth-service';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class UserGuard implements CanActivate {
  constructor(private authService: AuthService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> {
    return this.authService.authUser$.pipe(
      map((user) => {
        return (
          user !== undefined &&
          route.params.id &&
          route.params.id === user.userId
        );
      })
    );
  }
}
