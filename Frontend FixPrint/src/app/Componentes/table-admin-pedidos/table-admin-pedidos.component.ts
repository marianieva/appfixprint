import { Component, OnInit, AfterViewChecked } from '@angular/core';
import { PedidosService } from '../../Services/pedido.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-table-admin-pedidos',
  templateUrl: './table-admin-pedidos.component.html',
  styleUrls: ['./table-admin-pedidos.component.css'],
  providers: [DatePipe]
})
export class TableAdminPedidosComponent implements OnInit  {

  pedidos: any[] = []; // Para cargar el array con el JSON que devuelva la petición GET
  loading = true; // Para mostrar mensaje de carga de datos en el HTML
  error = ''; // Para manejar los errores en la carga de datos de la petición GET
  currentView: 'all' | 'recambios' | 'consumibles' = 'all';
  finalized: any;
  detalles: any;
  productos: any;

  constructor(public service: PedidosService, private datePipe: DatePipe) { }
   
  

  ngOnInit(): void {
    this.getPedidos();
  }

  getPedidos() {
    this.loading= true;
    this.service.getPedidosAdmin().subscribe({
      next: (data: any[]) => {
        this.pedidos = data.map(pedido => {
          pedido.fechaPedido = this.datePipe.transform(pedido.fechaPedido, 'dd-MM-yyyy');
          return pedido;
        });
        this.loading = false; // Finaliza la carga
      },
      error: (err) => {
        this.error = err; // Asigna el mensaje de error
        this.loading = false; // Finaliza la carga
      }
    });
  }

  getPedidosRecambios(){
    this.loading= true;
    this.service.getPedidosRecambiosAdmin().subscribe({
      next: (data: any[]) => {
        this.pedidos = data.map(pedido => {
          pedido.fechaPedido = this.datePipe.transform(pedido.fechaPedido, 'dd-MM-yyyy');
          return pedido;
        });
        this.loading = false; // Finaliza la carga
      },
      error: (err) => {
        this.error = err; // Asigna el mensaje de error
        this.loading = false; // Finaliza la carga
      }
    });
  }
  getPedidosConsumibles(){
    this.loading= true;
    this.service.getPedidosConsumiblesAdmin().subscribe({
      next: (data: any[]) => {
        this.pedidos = data.map(pedido => {
          pedido.fechaPedido = this.datePipe.transform(pedido.fechaPedido, 'dd-MM-yyyy');
          return pedido;
        });
        this.loading = false; // Finaliza la carga
      },
      error: (err) => {
        this.error = err; // Asigna el mensaje de error
        this.loading = false; // Finaliza la carga
      }
    });
  }

  changeView(view: 'all' | 'recambios' | 'consumibles') {
    this.currentView = view;
    if (view === 'all') {
      this.getPedidos();
    } else if (view === 'recambios') {
      this.getPedidosRecambios();
    } else if (view === 'consumibles') {
      this.getPedidosConsumibles();
    }
  }

  getTypeLabel(tipoPedido: string): string {
    switch (tipoPedido) {
      case 'CLIENTE':
        return 'Consumibles';
      case 'TECNICO':
        return 'Recambios';
      default:
        return tipoPedido;
    }
  }

  confirmarPedido(idPedido: number) {
    this.service.confirmarPedido(idPedido).subscribe({
      next: response => {
      },
      error: (err) => {
        this.error = err;
      }
    });
  }

}
