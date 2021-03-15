import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Category } from '../../../../models/category';
import { ProductService } from '../../../../services/product-service/product.service';
import { Product } from '../../../../models/product';
import { HttpErrorResponse, HttpEventType } from '@angular/common/http';
import { catchError, map, take } from 'rxjs/operators';
import { combineLatest, of } from 'rxjs';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthServiceService } from '../../../../services/auth-service/auth-service.service';
import { Sale } from '../../../../models/sale';
import { SaleProduct } from '../../../../models/SaleProduct';


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
  public saleProducts: Product[] = [];
  public sales: Sale[] = [];

  formGroup = new FormGroup({
    name: new FormControl('', [
      Validators.required,
      Validators.minLength(4)
    ]),
    price: new FormControl('', [
      Validators.required,
      Validators.min(1),
      Validators.max(100000),
    ]),
    url: new FormControl(''),
    description: new FormControl('', [Validators.required]),
    category: new FormControl('', [Validators.required]),
  });


  formGroupSale = new FormGroup({
    name: new FormControl('', [
      Validators.required,
      Validators.minLength(4)
    ]),
    percentage: new FormControl('', [
      Validators.required,
      Validators.min(0.1),
      Validators.max(0.9)
    ]),
  })

  formGroupAddProductSale = new FormGroup({
    sale: new FormControl('',[Validators.required]),
    product: new FormControl('', [Validators.required]),
  })

  formGroupSetCurrentSale = new FormGroup({
    currentSale: new FormControl('', [Validators.required]),
  })
  constructor(
    private productService: ProductService,
    private sanitizer: DomSanitizer,
    private snackBar: MatSnackBar,
    private authService: AuthServiceService
  ) {}

  public categories: Category[] = [];

  ngOnInit(): void {
    this.refreshStreams();
  }

  public resetForms(): void {
    this.name?.patchValue('');
    this.price?.patchValue('');
    this.category?.patchValue('');
    this.description?.patchValue('');
  }

  public resetCategory(): void {
    this.newCategory = undefined;
  }

  public resetImage(): void {
    this.selectedProduct = undefined;
    this.imageUrl = undefined;
  }

  openSnackBar(message: string, action: string): void {
    this.snackBar.open(message, action, {
      duration: 2000,
    });
  }

  public refreshStreams(): void {
    combineLatest([
      this.productService.fetchCategories$(),
      this.productService.getAllProducts$(),
      this.productService.fetchSales()
    ])
      .pipe(take(1))
      .subscribe(([categories, products, sales]) => {
        this.products =
          products !== undefined && products.length > 0 ? products : [];
        this.categories =
          categories !== undefined && categories.length > 0 ? categories : [];

        if (sales !== undefined && sales.length > 0) {
          this.sales = sales;
        }
      });
  }

  public uploadImage(): void {
    const img = new FormData();
    // tslint:disable-next-line:no-non-null-assertion
    const productId: string = this.selectedProduct?.id!;
    // tslint:disable-next-line:no-non-null-assertion
    img.append('file', this.selectedFile!, productId);
    this.productService
      .uploadProductImage(img)
      .pipe(
        map((event) => {
          switch (event.type) {
            case HttpEventType.UploadProgress:
              this.uploadInProgress = true;
              this.uploadProgress = Math.round(
                (event.loaded * 100) / event.total!
              );
              break;
            case HttpEventType.Response:
              return event;
          }
          return event;
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

  addCategory(): void {
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

  onFileSelected(): void {
    if (this.fileInput?.nativeElement && this.fileInput.nativeElement.files) {
      this.selectedFile = this.fileInput.nativeElement.files[0];
      const objectURL = URL.createObjectURL(this.selectedFile);
      this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    }
  }

  onSubmit(): void {
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

  get name(): AbstractControl | null {
    return this.formGroup.get('name');
  }
  get price(): AbstractControl | null {
    return this.formGroup.get('price');
  }
  get description(): AbstractControl | null {
    return this.formGroup.get('description');
  }
  get category(): AbstractControl | null {
    return this.formGroup.get('category');
  }
  get saleName(): AbstractControl | null {
    return this.formGroupSale.get('name');
  }
  get percentage(): AbstractControl | null {
    return this.formGroupSale.get('percentage');
  }
}
