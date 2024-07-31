import { Component, OnInit } from '@angular/core';
import { ImpresoraService } from 'src/app/Services/impresora.service';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-form-admin-impresora',
  templateUrl: './form-admin-impresora.component.html',
  styleUrls: ['./form-admin-impresora.component.css']
})
export class FormAdminImpresoraComponent implements OnInit {

  error: string = '';
  success: boolean = false;

  usuario: number | null = null;
  usuarios: any[] = [];
  serialNumber: number = 0;
  marca: string = '';
  modelo: string = '';

  constructor(public userService: UserService, public impresoraService: ImpresoraService) { }

  ngOnInit(): void {
    this.getClients();
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

  newEquipo() {
    if (this.usuario !== null) {
      const equipoData = {
        marca: this.marca,
        modelo: this.modelo,
        usuario: Number(this.usuario), 
        serialNumber: Number(this.serialNumber)
      };

      this.impresoraService.newEquipo(equipoData).subscribe({
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
