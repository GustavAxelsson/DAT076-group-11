import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ProductsComponent} from "./pages/products/products.component";
import {HomeComponent} from "./pages/home/home.component";
import {AboutComponent} from "./pages/about/about.component";
import {ShoppingCartComponent} from "./pages/shopping-cart/shopping-cart.component";
import {AdminProductsComponent} from "./pages/admin-products/admin-products.component";
import {ProductComponent} from "./pages/products/id/product/product.component";

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'products',
    component: ProductsComponent
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'about',
    component: AboutComponent
  },
  {
    path: 'shopping-cart',
    component: ShoppingCartComponent
  },
  {
    path: 'admin-products',
    component: AdminProductsComponent
  },
  {
    path: 'product/:id',
    component: ProductComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes , { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
