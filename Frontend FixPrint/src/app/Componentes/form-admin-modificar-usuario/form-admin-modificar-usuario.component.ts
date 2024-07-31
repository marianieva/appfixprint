import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/Services/auth.service';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-form-admin-modificar-usuario',
  templateUrl: './form-admin-modificar-usuario.component.html',
  styleUrls: ['./form-admin-modificar-usuario.component.css']
})
export class FormAdminModificarUsuarioComponent implements OnInit {

  error: string = '';
  success: boolean = false;

  idUsuarioString: string | null = localStorage.getItem('idUserUpdate')

  idUsuario: number = this. idUsuarioString ? parseInt(this. idUsuarioString): 0;
  user: any = {};

  nombre: string = '';
  apellidos:string = '';
  username: string = '';
  password: string = '';
  direccion: string = '';

  constructor(private authService: AuthService, private router: Router, private userService: UserService) { }

  ngOnInit(): void {
    
    console.log('antes de getUser', this.idUsuario);
    if (this.idUsuario !== null) {
      this.getUser(this.idUsuario);
    }
    console.log('después de getUser',this.idUsuario);
  }

  getUser(idUsuario: number): void {
    if (this.idUsuario !== null) {
    this.userService.getUser(idUsuario).subscribe({
      next: (userData: any) => {
        // Asigna los datos del usuario a las propiedades del componente
        this.nombre = userData.nombre;
        this.apellidos = userData.apellidos;
        this.username = userData.username;
        this.direccion = userData.direccion;
        this.user = userData;
      },
      error: (err) => {
        console.error(err); // Maneja el error
      }

    });
  }
  }

  updateUser2(): void{
    this.userService.updateUser({
      idUsuario: this.idUsuario,
      username: this.username,
      nombre: this.nombre,
      apellidos: this.apellidos,
      direccion: this.direccion,
      
      }).subscribe({
        next: response => {
          this.success = true;
          localStorage.removeItem('idUserUpdate');
        },
        error: (err) => {
          console.error(err); // Maneja el error
        }
      });
    }

}
