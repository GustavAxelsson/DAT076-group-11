import { Component, OnInit } from '@angular/core';
import { AuthServiceService } from '../../services/auth-service/auth-service.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  usernameRegister: string = '';
  passwordRegister: string = '';

  constructor(private authService: AuthServiceService) {}

  ngOnInit(): void {}

  public register() {
    this.authService.register(this.usernameRegister, this.passwordRegister);
  }
}
