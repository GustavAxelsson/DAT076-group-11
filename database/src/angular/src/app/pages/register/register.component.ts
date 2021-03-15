import { Component, Inject, OnInit } from '@angular/core';
import { AuthServiceService } from '../../services/auth-service/auth-service.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Customer } from '../../models/customer';
import { UserServiceService } from '../../services/user-service/user-service.service';

@Component({
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  usernameRegister: string = '';
  passwordRegister: string = '';

  showRegister = true;

  formGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    pid: new FormControl('', [Validators.required]),
    address: new FormControl('', [Validators.required]),
  });

  constructor(
    private authService: AuthServiceService,
    private router: Router,
    private _snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<RegisterComponent>,
    private userService: UserServiceService,
    @Inject(MAT_DIALOG_DATA) public data: { data: undefined }
  ) {}

  ngOnInit(): void {}

  onSubmitCustomer() {
    const customer: Customer = {
      email: this.email?.value ? this.email.value : undefined,
      firstName: this.firstName?.value ? this.firstName.value : undefined,
      lastName: this.lastName?.value ? this.lastName.value : undefined,
      personalNumber: this.pid?.value ? this.pid.value : undefined,
      billingAddress: this.address?.value ? this.address.value : undefined,
    };
    this.userService.updateCustomerOnUser(customer).subscribe(
      () => {
        this.showSnackBar('Register successful');
        this.dialogRef.close();
      },
      () => {
        this.showSnackBar('Register failed');
      }
    );
  }

  get email(): AbstractControl | null {
    return this.formGroup.get('email');
  }
  get firstName(): AbstractControl | null {
    return this.formGroup.get('firstName');
  }
  get lastName(): AbstractControl | null {
    return this.formGroup.get('lastName');
  }
  get pid(): AbstractControl | null {
    return this.formGroup.get('pid');
  }
  get address(): AbstractControl | null {
    return this.formGroup.get('address');
  }

  public register() {
    this.authService
      .register(this.usernameRegister, this.passwordRegister)
      .subscribe(
        (response) => {
          if (response) {
            this.showSnackBar('Register successful');
            this.showRegister = false;
          } else {
            this.showSnackBar('Register failed');
          }
        },
        () => {
          this.showSnackBar('Register failed');
        }
      );
  }

  showSnackBar(text: string) {
    this._snackBar.open(text, undefined, { duration: 2000 });
  }
}
