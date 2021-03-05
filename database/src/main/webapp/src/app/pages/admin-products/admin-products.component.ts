import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Category } from '../../models/category';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { HttpErrorResponse, HttpEventType } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-admin-products',
  templateUrl: './admin-products.component.html',
  styleUrls: ['./admin-products.component.css'],
})
export class AdminProductsComponent implements OnInit {
  @ViewChild('fileInput') fileInput: ElementRef | undefined;
  selectedFile: File | undefined;
  uploadInProgress = false;
  uploadProgress = 0;

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

  public uploadImage() {
    const img = new FormData();
    img.append('file', this.selectedFile!);

    this.productService
      .uploadProductImage(img)
      .pipe(
        map((event) => {
          switch (event.type) {
            case HttpEventType.UploadProgress:
              this.uploadInProgress = true;
              this.uploadProgress = Math.round(
                (event.loaded * 100) / event.total
              );
              break;
            case HttpEventType.Response:
              return event;
          }
        }),
        catchError((error: HttpErrorResponse) => {
          this.uploadInProgress = false;
          return of('Upload failed');
        })
      )
      .subscribe((event: any) => {
        if (typeof event === 'object') {
          console.log(event.body);
        }
      });
  }

  onFileSelected() {
    if (this.fileInput?.nativeElement && this.fileInput.nativeElement.files) {
      this.selectedFile = this.fileInput.nativeElement.files[0];
    }
  }

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
      category: this.category?.value,
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
