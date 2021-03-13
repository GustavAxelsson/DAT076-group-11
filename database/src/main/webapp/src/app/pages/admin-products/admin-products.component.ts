import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Category } from '../../models/category';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import {Sale} from "../../models/sale";
import {SaleProduct} from "../../models/SaleProduct";

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

  formGroupSale = new FormGroup({
    name: new FormControl(''),
    percentage: new FormControl(''),
  })

  formGroupAddProductSale = new FormGroup({
    sale: new FormControl(''),
    product: new FormControl(''),
  })

  formGroupSetCurrentSale = new FormGroup({
    currentSale: new FormControl(''),
  })
  constructor(private productService: ProductService) {}

  public categories: Category[] = [];
  public saleProducts: Product[] = [];
  public sales: Sale[] = [];

  ngOnInit(): void {
    this.productService.fetchCategories().subscribe((categories) => {
      if (categories !== undefined && categories.length > 0) {
        this.categories = categories;
      }
    });
    this.productService.getAllProducts$().subscribe((saleProducts) => {
      if (saleProducts !== undefined && saleProducts.length > 0) {
        this.saleProducts = saleProducts;
      }
    });
    this.productService.fetchSales().subscribe((sales) => {
      if (sales !== undefined && sales.length > 0) {
        this.sales = sales;
      }
    });

  }

  onSubmitProduct() {
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

  onSubmitSale() {
    const s: Sale = {
      name: this.formGroupSale.get('name')?.value,
      percentage: this.formGroupSale.get('percentage')?.value,
    };

    this.productService.addSale(s).subscribe(
      (res) => console.log('res', res),
      (error) => console.warn('error', error)
    );
  }

  onSubmitProductToSale() {
    const sp: SaleProduct = {
      sale: this.formGroupAddProductSale.get('sale')?.value,
      product: this.formGroupAddProductSale.get('product')?.value,
    };

    if(sp && sp.product && sp.product.id && sp.sale && sp.sale.id) {
      this.productService.addProductToSale(sp.product.id, sp.sale.id).subscribe(
        (res) => console.log('res', res),
        (error) => console.warn('error', error)
      );
    }
  }

  onSubmitCurrentSale() {
    const cs: Sale = this.formGroupSetCurrentSale.get('currentSale')?.value
    this.productService.setCurrentSale(cs).subscribe(
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
