import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from '../../../../services/shopping-service/shopping-cart.service';
import { ProductOrder } from '../../../../models/ProductOrder';
import { take } from 'rxjs/operators';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  orderList:ProductOrder[] = [];

  constructor(private shoppingCartService: ShoppingCartService) { }

  ngOnInit(): void {
    this.shoppingCartService.getMyOrders().pipe(take(1)).subscribe(
      res => {
        if (res && res.length > 0) {
          this.orderList = res;
        }
      },
      error => console.warn(error)
    )
  }

}
