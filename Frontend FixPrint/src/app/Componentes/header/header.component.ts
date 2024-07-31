import { Component, OnInit } from '@angular/core';
import { UserService } from '../../Services/user.service';
import { AuthService } from 'src/app/Services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  userRole: string | null = '';
  isLoggedIn: boolean = false;

  constructor(private userService: UserService, private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.isLoggedIn.subscribe(loggedIn => {
      this.isLoggedIn = loggedIn;
    });
    this.authService.currentUserRole.subscribe(role => {
      this.userRole = role;
    });
  }

  logout(): void {
    this.authService.logout(); // Llamar al método logout del servicio de autenticación
    this.userRole = null;
  }

  

}
