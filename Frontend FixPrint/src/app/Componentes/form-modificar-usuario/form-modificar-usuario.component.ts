import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/Services/auth.service';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-form-modificar-usuario',
  templateUrl: './form-modificar-usuario.component.html',
  styleUrls: ['./form-modificar-usuario.component.css']
})
export class FormModificarUsuarioComponent implements OnInit {

  error: string ='';
  success:boolean = false;
  userId: number = Number(localStorage.getItem('userId'));
  user: any = {};
  username: string = '';
  nombre: string = '';
  apellidos:string = '';
  direccion: string = '';
  idUsuario  = Number(localStorage.getItem('userId'));
  
  constructor(private userService: UserService,private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.loadUser();
  }

  loadUser(): void {
    if (this.userId) {
      this.userService.getUser(this.userId).subscribe({
        next: (data) => {
          this.user = data;
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
      direccion: this.direccion}).subscribe({
        next: response => {
          this.success = true;
          this.authService.logout
          this.router.navigate(['usuario'])
        },
        error: (err) => {
          console.error(err); // Maneja el error
        }
      });
    }
}
