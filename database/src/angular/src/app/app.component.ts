import { Component } from '@angular/core';
import { ShoppingCartService } from './services/shopping-service/shopping-cart.service';
import { Observable } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { LoginComponent } from './pages/login/login.component';
import { AuthServiceService, AuthUser } from './services/auth-service/auth-service.service';
import { RegisterComponent } from './pages/register/register.component';
import { Router } from '@angular/router';

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
    private authService: AuthServiceService) {

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

  logout(): void {
    this.authService.logout();
    this.router.navigate(['home']);
  }
}
