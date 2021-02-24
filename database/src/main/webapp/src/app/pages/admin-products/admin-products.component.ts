import { Component, OnInit } from '@angular/core';
import {ShoppingCartService} from "../../services/shopping-cart.service";
import {FormControl, FormGroup} from "@angular/forms";
import {Product} from "../products/products.component";

@Component({
  selector: 'app-admin-products',
  templateUrl: './admin-products.component.html',
  styleUrls: ['./admin-products.component.css']
})
export class AdminProductsComponent implements OnInit {

  formGroup = new FormGroup({
    name: new FormControl(''),
    price: new FormControl(''),
    url: new FormControl(''),
    description: new FormControl(''),
  });
  constructor(private shoppingCartService: ShoppingCartService) { }

  ngOnInit(): void {
  }

  onSubmit() {
    const p: Product = {
      name: this.formGroup.get('name')?.value,
      price: this.formGroup.get('price')?.value,
      url: this.formGroup.get('url')?.value,
      description: this.formGroup.get('description')?.value,
    };

    this.shoppingCartService.addProduct(this.formGroup.get('name')?.value).subscribe(
      res => console.log('res', res),
      error => console.warn('error', error)
    );
  }

}
