import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductsComponent } from './pages/products/products.component';
import { HomeComponent } from './pages/home/home.component';
import { AboutComponent } from './pages/about/about.component';
import { ShoppingCartComponent } from './pages/shopping-cart/shopping-cart.component';
import { AdminProductsComponent } from './pages/logged-on-pages/admin/admin-products/admin-products.component';
import { ProductComponent } from './pages/products/id/product/product.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { UserHomeComponent } from './pages/logged-on-pages/user/user-home/user-home.component';
import { AdminHomeComponent } from './pages/logged-on-pages/admin/admin-home/admin-home.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: 'products',
    component: ProductsComponent,
  },
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'about',
    component: AboutComponent,
  },
  {
    path: 'shopping-cart',
    component: ShoppingCartComponent,
  },
  {
    path: 'product/:id',
    component: ProductComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: 'user/:id',
    component: UserHomeComponent,
    loadChildren: () =>
      import('./pages/logged-on-pages/user/user.module').then(
        (mod) => mod.UserModule
      ),
  },
  {
    path: 'admin',
    loadChildren: () =>
      import('./pages/logged-on-pages/admin/admin.module').then(
        (mod) => mod.AdminModule
      ),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
})
export class AppRoutingModule {}
