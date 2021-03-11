import { Component, OnInit } from '@angular/core';
import { AuthServiceService } from '../../services/auth-service.service';
import { ProductService } from '../../services/product.service';
import { HttpEventType } from '@angular/common/http';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  usernameLogin: string = '';
  passwordLogin: string = '';

  constructor(
    private authService: AuthServiceService,
    private router: Router,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {}

  public login() {
    this.authService.login(this.usernameLogin, this.passwordLogin).subscribe(
      (response) => {
        if (response.type === HttpEventType.Response) {
          // this.router.navigate(['home']);
        }
      },
      (error) => {
        this._snackBar.open('Login failed', 'Close', {
          duration: 2000,
        });
      }
    );
  }
}
