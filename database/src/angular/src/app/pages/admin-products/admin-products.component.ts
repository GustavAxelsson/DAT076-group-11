import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Category } from '../../models/category';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { HttpErrorResponse, HttpEventType } from '@angular/common/http';
import { catchError, map, take } from 'rxjs/operators';
import { combineLatest, of } from 'rxjs';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { MatSnackBar } from '@angular/material/snack-bar';

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
  imageUrl: SafeUrl | undefined;
  products: Product[] | undefined;
  newCategory: string | undefined;
  selectedProduct: Product | undefined;

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
  constructor(
    private productService: ProductService,
    private sanitizer: DomSanitizer,
    private _snackBar: MatSnackBar
  ) {}

  public categories: Category[] = [];

  ngOnInit(): void {
    this.refreshStreams();
  }

  public resetForms() {
    this.name?.patchValue('');
    this.price?.patchValue('');
    this.category?.patchValue('');
    this.description?.patchValue('');
  }

  public resetCategory() {
    this.newCategory = undefined;
  }

  public resetImage() {
    this.selectedProduct = undefined;
    this.imageUrl = undefined;
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

  public refreshStreams(): void {
    combineLatest([
      this.productService.fetchCategories(),
      this.productService.getAllProducts$(),
    ])
      .pipe(take(1))
      .subscribe(([categories, products]) => {
        this.products =
          products !== undefined && products.length > 0 ? products : [];
        this.categories =
          categories !== undefined && categories.length > 0 ? categories : [];
      });
  }

  public uploadImage() {
    const img = new FormData();
    const productId: string = this.selectedProduct?.id!;
    img.append('file', this.selectedFile!, productId);
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
          this.openSnackBar('Failed to add new category', 'close');
          return of('Upload failed');
        })
      )
      .subscribe((event: any) => {
        if (typeof event === 'object') {
          this.openSnackBar('Image successfully uploaded', 'close');
          this.resetImage();
        }
      });
  }

  addCategory() {
    if (this.newCategory === undefined) {
      return;
    }
    this.productService
      .addNewCategory(this.newCategory)
      .pipe(take(1))
      .subscribe(
        () => {
          this.refreshStreams();
          this.openSnackBar('New category successfully added', 'close');
          this.resetCategory();
        },
        (error) => this.openSnackBar('Failed to add new category', 'close')
      );
  }

  onFileSelected() {
    if (this.fileInput?.nativeElement && this.fileInput.nativeElement.files) {
      this.selectedFile = this.fileInput.nativeElement.files[0];
      let objectURL = URL.createObjectURL(this.selectedFile);
      this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    }
  }

  onSubmit() {
    const p: Product = {
      name: this.formGroup.get('name')?.value,
      price: this.formGroup.get('price')?.value,
      description: this.formGroup.get('description')?.value,
      category: this.category?.value,
    };

    this.productService.addProduct(p).subscribe(
      () => {
        this.refreshStreams();
        this.openSnackBar('Product successfully added', 'close');
        this.resetForms();
      },
      (error) => {
        this.openSnackBar('Failed to upload product', 'close');
      }
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
