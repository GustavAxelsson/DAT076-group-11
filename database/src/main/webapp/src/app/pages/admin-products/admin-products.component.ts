import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Category } from '../../models/category';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';

@Component({
  selector: 'app-admin-products',
  templateUrl: './admin-products.component.html',
  styleUrls: ['./admin-products.component.css'],
})
export class AdminProductsComponent implements OnInit {
  formGroup = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.minLength(4)]),
    price: new FormControl('', [
      Validators.required,
      Validators.min(1),
      Validators.max(100000),
    ]),
    url: new FormControl(''),
    description: new FormControl('', [Validators.required]),
    category: new FormControl(''),
  });
  constructor(private productService: ProductService) {}

  public categories: Category[] = [];

  ngOnInit(): void {
    this.productService.fetchCategories().subscribe((categories) => {
      if (categories !== undefined && categories.length > 0) {
        this.categories = categories;
      }
    });
  }

  onSubmit() {
    const p: Product = {
      name: this.formGroup.get('name')?.value,
      price: this.formGroup.get('price')?.value,
      description: this.formGroup.get('description')?.value,
      category: this.formGroup.get('category')?.value,
    };

    this.productService.addProduct(p).subscribe(
      (res) => console.log('res', res),
      (error) => console.warn('error', error)
    );
  }

  get name() {
    return this.formGroup.get('name');
  }
  get price() {
    return this.formGroup.get('price');
  }
  get description() {
    return this.formGroup.get('description');
  }
  get category() {
    return this.formGroup.get('category');
  }
}
