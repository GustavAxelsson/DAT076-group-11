import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { UserHomeComponent } from './user-home/user-home.component';
import { OrdersComponent } from './orders/orders.component';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { OrderItemsComponent } from './orders/order-items/order-items.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { TestComponent } from './orders/test/test.component';

const routes: Routes = [
  {
    path: '',
    component: UserHomeComponent,
    children: [
      { path: 'home',
        component: UserHomeComponent
      },
      {
        path: 'orders',
        component: OrdersComponent
      }
    ],
  },
];

@NgModule({
  declarations: [OrdersComponent, OrdersComponent, UserHomeComponent, OrderItemsComponent, TestComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    MatListModule,
    MatSidenavModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
  ],
})
export class UserModule {}
