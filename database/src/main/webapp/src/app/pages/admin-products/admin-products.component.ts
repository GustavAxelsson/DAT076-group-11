import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {ProductService} from "../../services/product.service";
import {Product} from "../../models/product";

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
  constructor(private productService: ProductService) { }

  ngOnInit(): void {
  }

  onSubmit() {
    const p: Product = {
      name: this.formGroup.get('name')?.value,
      price: this.formGroup.get('price')?.value,
      url: this.formGroup.get('url')?.value,
      description: this.formGroup.get('description')?.value,
    };

    this.productService.addProduct(this.formGroup.get('name')?.value).subscribe(
      res => console.log('res', res),
      error => console.warn('error', error)
    );
  }

}
