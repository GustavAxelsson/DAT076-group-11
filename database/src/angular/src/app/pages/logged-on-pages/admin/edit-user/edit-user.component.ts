import { Component, OnInit } from '@angular/core';
import { ExternalUser } from '../../../../models/external-user';
import { UserService } from '../../../../services/user-service/user-service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css'],
})
export class EditUserComponent implements OnInit {
  users: ExternalUser[] = [];

  constructor(private userService: UserService) {}

  selectedUsername = '';
  selectedRole = '';

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe((users) => {
      if (users && users.length > 0) {
        this.users = users;
      }
    });
  }

  userChanged(user: ExternalUser, role: string) {
    user.role = role;
  }

  updateUser(user: ExternalUser) {
    if (user && user.username && user.role) {
      this.userService.updateRoleOnUser(user.username, user.role).subscribe(
        () => {},
        () => console.log('Error')
      );
    }
  }
}
