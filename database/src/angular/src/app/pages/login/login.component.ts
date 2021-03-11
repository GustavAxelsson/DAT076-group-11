import { Component, OnInit } from '@angular/core';
import { AuthServiceService } from '../../services/auth-service.service';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  username: string = '';
  password: string = '';

  constructor(
    private authService: AuthServiceService,
    private productService: ProductService
  ) {}

  ngOnInit(): void {}

  public login() {
    this.authService.login(this.username, this.password);
  }

  public register() {
    this.authService.register(this.username, this.password);
  }

  public fetchAllProducts() {
    this.productService.getAllProducts$().subscribe(
      (res) => console.log('Successful'),
      (error) => console.warn('Failed', error)
    );
  }
}
