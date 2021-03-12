import { Component, OnInit } from '@angular/core';
import {
  ExternalUser,
  UserServiceService,
} from '../../../../services/user-service/user-service.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css'],
})
export class EditUserComponent implements OnInit {
  users: ExternalUser[] = [];

  constructor(private userService: UserServiceService) {}

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe((users) => {
      if (users && users.length > 0) {
        this.users = users;
      }
    });
  }
}
