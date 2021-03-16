import { Component } from '@angular/core';
import { ShoppingCartService } from './services/shopping-service/shopping-cart.service';
import { Observable } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { Router } from '@angular/router';
import { take } from 'rxjs/operators';
import { AuthUser } from './models/auth-user';
import { AuthService } from './services/auth-service/auth-service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'frontend';
  itemsInShoppingCart$: Observable<number> = this.shoppingCartService
    .numberOfItemsInShoppingCart;

  authUser: AuthUser | undefined;
  authUser$: Observable<AuthUser | undefined> = this.authService.authUser$

  constructor(
    private shoppingCartService: ShoppingCartService,
    private dialog: MatDialog,
    private router: Router,
    private authService: AuthService) {

    this.authUser$.subscribe(
      res => this.authUser = res
    );
  }

  openDialog(action: string): void {
    if (action === 'login') {
      const dialogRef = this.dialog.open(LoginComponent, {
        width: '250px',
      });
    } else if (action === 'register') {
      const dialogRef = this.dialog.open(RegisterComponent, {
        width: '250px',
      });
    }
  }

  navigateToUserMyPages() {
    this.authService.authUser$.pipe(take(1)).subscribe(
      (authUser: AuthUser | undefined) => {
        if (authUser === undefined) {
          return;
        }
        if (authUser.userType === 'ADMIN') {
          this.router.navigate(['admin']);
        }
        if (authUser.userType === 'USER') {
          this.router.navigate(['user', authUser.userId]);
        }
        return;
      },
      error => {
        console.warn('')
      });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['home']);
  }
}
