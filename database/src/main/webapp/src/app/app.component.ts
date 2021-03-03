import { Component } from '@angular/core';
import { ShoppingCartService } from './services/shopping-cart.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'frontend';
  itemsInShoppingCart$: Observable<number> = this.shoppingCartService
    .itemsInShoppingCart$;
  constructor(private shoppingCartService: ShoppingCartService) {}
}
