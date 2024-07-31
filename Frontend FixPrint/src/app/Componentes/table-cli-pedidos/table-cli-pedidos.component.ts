import { Component, OnInit, AfterViewChecked, ViewChildren, QueryList, ElementRef } from '@angular/core';
import { PedidosService } from '../../Services/pedido.service';
import { AuthService } from 'src/app/Services/auth.service';

@Component({
  selector: 'app-table-cli-pedidos',
  templateUrl: './table-cli-pedidos.component.html',
  styleUrls: ['./table-cli-pedidos.component.css']
})
export class TableCliPedidosComponent implements OnInit {
  
  pedidos: any[] = []; // Para cargar el array con el JSON que devuelva la petición GET
  loading = true; // Para mostrar mensaje de carga de datos en el HTML
  error = ''; // Para manejar los errores en la carga de datos de la petición GET

  idUsuario = Number(localStorage.getItem('userId'));

  constructor(public service: PedidosService) { }

  ngOnInit(): void {
    this.getPedidos(this.idUsuario)
  }

  getPedidos(idUsuario:number) {
    this.loading= true;
    this.service.getPedidosByUser(idUsuario).subscribe({
      next: (data: any[]) => {
        this.pedidos = data;
        this.loading = false; // Finaliza la carga
      },
      error: (err) => {
        this.error = err; // Asigna el mensaje de error
        this.loading = false; // Finaliza la carga
      }
    });
  }

}