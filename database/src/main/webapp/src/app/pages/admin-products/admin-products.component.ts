import { Component, OnInit } from '@angular/core';
import {ShoppingCartService} from "../../services/shopping-cart.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Product} from "../products/products.component";

@Component({
  selector: 'app-admin-products',
  templateUrl: './admin-products.component.html',
  styleUrls: ['./admin-products.component.css']
})
export class AdminProductsComponent implements OnInit {

  formGroup = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.minLength(4)]),
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
      description: this.formGroup.get('description')?.value,
    };

    this.shoppingCartService.addProduct(p).subscribe(
      res => console.log('res', res),
      error => console.warn('error', error)
    );
  }

  get name() { return this.formGroup.get('name'); }

}
