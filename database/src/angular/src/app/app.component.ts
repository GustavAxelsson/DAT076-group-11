import { Component } from '@angular/core';
import { ShoppingCartService } from './services/shopping-service/shopping-cart.service';
import { Observable } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { LoginComponent } from './pages/login/login.component';
import { AuthServiceService, AuthUser } from './services/auth-service/auth-service.service';

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
    private authService: AuthServiceService) {

    this.authUser$.subscribe(
      res => this.authUser = res
    );
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(LoginComponent, {
      width: '250px',
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      // this.animal = result;
    });
  }
}
