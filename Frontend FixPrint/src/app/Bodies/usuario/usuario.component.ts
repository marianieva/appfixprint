import { Component, OnInit } from '@angular/core';
import { UserService } from '../../Services/user.service';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {
  
  userRole: string = '';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userRole = this.userService.getUserRole();
  }

}
