import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes } from '@angular/router';
import { AdminProductsComponent } from '../admin/admin-products/admin-products.component';
import { AdminHomeComponent } from '../admin/admin-home/admin-home.component';
import { UserHomeComponent } from './user-home/user-home.component';

const routes: Routes = [
  {
    path: '',
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'home' },
      { path: 'home', component: UserHomeComponent },
    ],
  },
];

@NgModule({
  declarations: [],
  imports: [CommonModule],
})
export class UserModule {}
