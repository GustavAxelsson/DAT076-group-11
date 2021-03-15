import { Component, Inject, OnInit } from '@angular/core';
import { AuthServiceService, AuthUser } from '../../services/auth-service/auth-service.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  usernameLogin: string = '';
  passwordLogin: string = '';

  constructor(
    private authService: AuthServiceService,
    private router: Router,
    private _snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<LoginComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {data: undefined}
  ) {}

  ngOnInit(): void {}

  public login() {
    this.authService.login(this.usernameLogin, this.passwordLogin).subscribe(
      (authUser: AuthUser | undefined) => {
        if (authUser === undefined) {
          return;
        }
        if (authUser.userType === 'ADMIN') {
          this.router.navigate(['admin']);
        }
        if (authUser.userType === 'USER') {
          this.router.navigate(['user', authUser.userId]);
        }
        this.dialogRef.close();
        return;
      },
      (error) => {
        this._snackBar.open('Login failed', 'Close', {
          duration: 2000,
        });
      }
    );
  }
}
