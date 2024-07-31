import { Component, OnInit } from '@angular/core';
import { ProductoService } from '../../Services/producto.service';

@Component({
  selector: 'app-table-tec-almacen',
  templateUrl: './table-tec-almacen.component.html',
  styleUrls: ['./table-tec-almacen.component.css']
})
export class TableTecAlmacenComponent implements OnInit {

  productos: any[] = []; // Para cargar el array con el JSON que devuelva la petición GET
  loading = true; // Para mostrar mensaje de carga de datos en el HTML
  error = ''; // Para manejar los errores en la carga de datos de la petición GET
  idUsuario: number = Number(localStorage.getItem('userId'));

  constructor(public service: ProductoService) { }

  ngOnInit(): void {
    this.getProductosAlmacen(this.idUsuario)
  }

  getProductosAlmacen(idUsuario:number) {
    this.loading= true;
    this.service.getProductosAlmacen(idUsuario).subscribe({
      next: (data: any[]) => {
        this.productos = data;
        this.loading = false; // Finaliza la carga
      },
      error: (err) => {
        this.error = err; // Asigna el mensaje de error
        this.loading = false; // Finaliza la carga
      }
    });
  }

}
