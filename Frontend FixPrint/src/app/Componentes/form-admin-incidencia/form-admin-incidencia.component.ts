import { Component, OnInit } from '@angular/core';
import { ImpresoraService } from 'src/app/Services/impresora.service';
import { IncidenciaService } from 'src/app/Services/incidencia.service';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-form-admin-incidencia',
  templateUrl: './form-admin-incidencia.component.html',
  styleUrls: ['./form-admin-incidencia.component.css']
})
export class FormAdminIncidenciaComponent implements OnInit {

  error: string = '';
  success:boolean = false;

  usuarios: any[] = [];
  usuario: number = 0;

  equipos:  any[] = [];
  serialNumber: number = 0;

  descripcionCliente: string = '';
  listaProductos: any[] = [];

  constructor(public userService: UserService, public impresoraService: ImpresoraService, public incidenciaService: IncidenciaService) { }

  ngOnInit(): void {
    this.getClients();
    this.equiposByClient(this.usuario);
  }

  getClients() {
    this.userService.getClientesAdmin().subscribe({
      next: (data: any[]) => {
        this.usuarios = data;
      },
      error: (err) => {
        this.error = err;
      }
    });
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

  onUsuarioChange() {
    this.equiposByClient(this.usuario);
  }

  newIncidenciaAdmin() {
    if (this.usuario !== null) {
      const equipoData = {
        descripcionCliente: this.descripcionCliente,
        serialNumber: Number(this.serialNumber),
        usuario: Number(this.usuario),
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
      console.error("No se ha seleccionado ningún usuario.");
    }
  }

}
