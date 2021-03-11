import { Component, OnInit } from '@angular/core';
import { AuthServiceService } from '../../services/auth-service.service';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  usernameRegister: string = '';
  passwordRegister: string = '';
  usernameLogin: string = '';
  passwordLogin: string = '';

  constructor(
    private authService: AuthServiceService,
    private productService: ProductService
  ) {}

  ngOnInit(): void {}

  public login() {
    this.authService.login(this.usernameLogin, this.passwordLogin);
  }

  public register() {
    this.authService.register(this.usernameRegister, this.passwordRegister);
  }

  public fetchAllProducts() {
    this.productService.getAllProducts$().subscribe(
      (res) => console.log('Successful', res),
      (error) => console.warn('Failed', error)
    );
  }
}
