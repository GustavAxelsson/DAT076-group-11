import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { UserHomeComponent } from './user-home/user-home.component';
import { OrdersComponent } from './orders/orders.component';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';

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
  declarations: [OrdersComponent, OrdersComponent, UserHomeComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    MatListModule,
    MatSidenavModule,
  ],
})
export class UserModule {}
