import { Component, OnInit } from '@angular/core';
import { ImpresoraService } from 'src/app/Services/impresora.service';
import { IncidenciaService } from 'src/app/Services/incidencia.service';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-form-cli-incidencia',
  templateUrl: './form-cli-incidencia.component.html',
  styleUrls: ['./form-cli-incidencia.component.css']
})
export class FormCliIncidenciaComponent implements OnInit {

  error: string = '';
  success:boolean = false;

  usuarios: any[] = [];
  usuario: number = Number(localStorage.getItem('userId'));

  equipos:  any[] = [];
  serialNumber: number = 0;

  descripcionCliente: string = '';
  listaProductos: any[] = [];

  constructor(public userService: UserService, public impresoraService: ImpresoraService, public incidenciaService: IncidenciaService) { }

  ngOnInit(): void {
    this.equiposByClient(this.usuario);
  }

  equiposByClient(usuario: number){
    this.impresoraService.getEquipos(usuario).subscribe({
      next: (data: any[]) => {
        this.equipos = data;
      },
      error: (err) => {
        this.error = err;
      }
    });
  }

  newIncidenciaAdmin() {
    if (this.usuario !== null) {
      const equipoData = {
        descripcionCliente: this.descripcionCliente,
        serialNumber: Number(this.serialNumber),
        usuario: this.usuario,
        listaProductos: this.listaProductos
      };

      this.incidenciaService.newIncidenciaAdmin(equipoData).subscribe({
        next: response => {
          this.success = true;
        },
        error: (err) => {
          this.error = err;
        }
      });
    } else {
      console.error("No se ha podido recuperar el usuario de la sesion.");
    }
  }

}
